package com.tuoshecx.server.wx.component.devops.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.site.service.SiteWxService;
import com.tuoshecx.server.wx.component.client.ComponentClientService;
import com.tuoshecx.server.wx.component.client.response.*;
import com.tuoshecx.server.wx.component.client.request.ProgramSubmitAuditRequest;
import com.tuoshecx.server.wx.component.devops.domain.DomainConfigure;
import com.tuoshecx.server.wx.component.devops.domain.SmallAuditConfigure;
import com.tuoshecx.server.wx.component.devops.domain.SmallDeploy;
import com.tuoshecx.server.wx.component.devops.domain.SmallExtConfigure;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 发布小程序服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class DeployService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployService.class);

    private final SiteWxService siteWxService;
    private final SmallDeployService smallDeployService;
    private final SmallDeployLogService smallDeployLogService;
    private final DomainConfigureService domainConfigureService;
    private final SmallExtConfigureService smallExtConfigureService;
    private final ComponentClientService componentClientService;
    private final SmallAuditConfigureService auditConfigureService;

    @Autowired
    public DeployService(SiteWxService siteWxService, SmallDeployService smallDeployService,
                         SmallDeployLogService smallDeployLogService, DomainConfigureService domainConfigureService,
                         SmallExtConfigureService smallExtConfigureService, ComponentClientService componentClientService,
                         SmallAuditConfigureService auditConfigureService) {

        this.siteWxService = siteWxService;
        this.smallDeployService = smallDeployService;
        this.smallDeployLogService = smallDeployLogService;
        this.domainConfigureService = domainConfigureService;
        this.smallExtConfigureService = smallExtConfigureService;
        this.componentClientService = componentClientService;
        this.auditConfigureService = auditConfigureService;
    }

    public SmallDeploy deploy(String siteId, Integer templateId){
        String appid = getAppid(siteId);
        Optional<SmallDeploy> optional = smallDeployService.get(appid, templateId);

        SmallDeploy deploy = optional.orElseGet(()-> {
            SmallDeploy t = new SmallDeploy();
            t.setSiteId(siteId);
            t.setAppid(appid);
            t.setTemplateId(templateId);
            t.setState("WAIT");
            t.setSetDomain(false);
            return smallDeployService.save(t);
        });

        if(isRefuse(deploy.getState())){
            smallDeployService.reset(deploy.getId());
            return smallDeployService.get(deploy.getId());
        }

        return deploy;
    }

    private boolean isRefuse(String state){
        return StringUtils.equals(state, "REFUSE");
    }

    private String getAppid(String siteId){
        Optional<String> optional = siteWxService.getAppid(siteId);
        if(!optional.isPresent()){
            throw new BaseException("站点还未托管小程序公众号");
        }
        return optional.get();
    }

    public SmallDeploy setDomain(String id){
        SmallDeploy t = smallDeployService.get(id);
        Optional<DomainConfigure> optional = domainConfigureService.getOptional();
        if(!optional.isPresent()){
            saveDeployLog(id, "SET_DOMAIN", "Domain配置不存在");
            throw new BaseException("Domain配置不存在");
        }
        if(t.getSetDomain()){
            return t;
        }

        DomainConfigure configure = optional.get();
        ComponentResponse viewResponse = componentClientService.setWebViewDomain(t.getAppid(), configure.getWebViewDomain());
        if(!viewResponse.isOk()){
            LOGGER.error("Set view domain fail, error {}-{}", viewResponse.getCode(), viewResponse.getMessage());
            saveDeployLog(id, "SET_DOMAIN", String.format("%d-%s", viewResponse.getCode(), viewResponse.getMessage()));
            throw new BaseException("设置业务域失败");
        }

        ComponentResponse domainResponse = componentClientService.updateDomain(t.getAppid(), configure.getRequestDomain(),
                configure.getWsrequestDomain(), configure.getUploadDomain(), configure.getDownloadDomain());
        if(!domainResponse.isOk() && domainResponse.getCode() != 85017){
            saveDeployLog(id, "SET_DOMAIN", String.format("%d-%s", domainResponse.getCode(), domainResponse.getMessage()));
            throw new BaseException("设置服务器域名失败");
        }

        smallDeployService.setDomain(id);
        saveDeployLog(id, "SET_DOMAIN", "成功设置Domain");
        return smallDeployService.get(id);
    }

    private void saveDeployLog(String id, String action, String message){
        smallDeployLogService.save(id, action, message);
    }

    public SmallDeploy programCommit(String id){
        SmallDeploy t = smallDeployService.get(id);
        String extJson = buildExtJson(t.getAppid(), t.getTemplateId());
        ComponentResponse  response = componentClientService.programCommit(t.getAppid(), t.getTemplateId(),
                "V1.0", "tuoshecx.com", extJson);

        if(response.isOk()){
            saveDeployLog(id, "COMMIT", "提交小程序成功");
            smallDeployService.commit(id);
        }else{
            saveDeployLog(id, "COMMIT", String.format("%d-%s", response.getCode(), response.getMessage()));
        }

        return smallDeployService.get(id);
    }

    private String buildExtJson(String appid, Integer templateId){
        Optional<SmallExtConfigure> optional = smallExtConfigureService.getTemplateId(templateId);
        String m = optional.map(configure -> {
            StringBuilder builder = new StringBuilder(200);
            builder.append("{\"extAppid\":\"").append(appid).append("\"");
            appendNotBlank(builder, "\"ext\"", StringUtils.replace(configure.getExt(), "$appid", appid));
            appendNotBlank(builder, "\"extPages\"", configure.getExtPages());
            appendNotBlank(builder, "\"pages\"", configure.getPages());
            appendNotBlank(builder, "\"window\"", configure.getWindow());
            appendNotBlank(builder, "\"tabBar\"", configure.getTabBar());
            builder.append("}");
            return builder.toString();
        }).orElseGet(() ->  String.format("{\"extAppid\":\"%s\",\"ext\":{\"appId\":\"%s\"}}",  appid, appid));
        m = StringUtils.remove(m, "\r");
        m = StringUtils.remove(m, "\n");
        return StringUtils.replace(m, "\"", "\\\"");
    }

    private void appendNotBlank(StringBuilder builder, String key, String value){
        if(StringUtils.isNotBlank(value)){
            builder.append(String.format(",%s:%s", key, value));
        }
    }

    public SmallDeploy submitAudit(String id){
        SmallDeploy t = smallDeployService.get(id);
        List<SmallAuditConfigure> configures = auditConfigureService.queryAppid(t.getAppid());
        if(configures.isEmpty()){
            throw new BaseException("小程序审核信息不存在");
        }

        ProgramSubmitAuditResponse response = componentClientService.submitAudit(t.getAppid(), e -> {
            for(SmallAuditConfigure c: configures){
                e.addItem(new ProgramSubmitAuditRequest.SubmitAuditItem(c.getTitle(), c.getAddress(), c.getTag(),
                        c.getFirstClass(), c.getSecondClass(), c.getThirdClass(), c.getFirstId(), c.getSecondId(),
                        c.getThirdId()));
            }
        });

        if(response.isOk()){
            smallDeployService.apply(id, response.getAuditId());
            saveDeployLog(id, "AUDIT", "提交小程序审核成功");
        }else{
            saveDeployLog(id, "AUDIT", String.format("%d-%s", response.getCode(), response.getMessage()));
        }
        return smallDeployService.get(id);
    }

    public SmallDeploy getAuditStatus(String id){
        SmallDeploy t = smallDeployService.get(id);

        if(!isAudit(t.getState())){
            LOGGER.error("shop {} appid {} state {}", t.getSiteId(), t.getAppid(), t.getState());
            throw new BaseException("不是提交审核状态");
        }

        GetAuditStatusResponse response = componentClientService.getAuditStatus(t.getAppid(), t.getAuditId());
        if(response.isOk()){
            if(response.getStatus() == 0){
                smallDeployService.auditPass(id);
                saveDeployLog(id, "AUDIT-PASS", "小程序审核通过");
            }else{
                smallDeployService.auditRefuse(id, response.getReason());
                saveDeployLog(id, "AUDIT-REFUSE", response.getReason());
            }
        }else{
            saveDeployLog(id, "GET-AUDIT", String.format("%d-%s", response.getCode(), response.getMessage()));
        }

        return smallDeployService.get(id);
    }

    private boolean isAudit(String state){
        return StringUtils.equals(state, "AUDIT");
    }

    public void getAuditStatus(){
        List<String> ids = smallDeployService.queryAudit();
        for(String id: ids){
            SmallDeploy t = smallDeployService.get(id);
            GetAuditStatusResponse response = componentClientService.getAuditStatus(t.getAppid(), t.getAuditId());

            if(!response.isOk()){
                LOGGER.error("Get {} audit status fail, error {}-{}",
                        t.getAuditId(), response.getCode(), response.getMessage());
                continue;
            }

            if(response.getStatus() == 2){
                LOGGER.info("Auditing...");
                continue;
            }

            if(response.getStatus() == 0){
                smallDeployService.auditPass(id);
                release(id);
            }else{
                smallDeployService.auditRefuse(id, response.getReason());
            }
        }
    }

    private void release(String id){
        SmallDeploy t = smallDeployService.get(id);
        ComponentResponse response = componentClientService.promgramRelease(t.getAppid());
        if(response.isOk()){
            smallDeployService.release(id);
            saveDeployLog(id, "RELEASE", "发布成功");
        }else{
            saveDeployLog(id, "RELEASE", String.format("%d-%s", response.getCode(), response.getMessage()));
        }
    }

    public GetCategoryResponse getCategory(String siteId){
        String appid = getAppid(siteId);
        return componentClientService.getCategory(appid);
    }

    public GetQrcodeResponse getQrcode(String siteId, String path){
       String appid = getAppid(siteId);
       return componentClientService.getQrocde(appid, path);
    }
}
