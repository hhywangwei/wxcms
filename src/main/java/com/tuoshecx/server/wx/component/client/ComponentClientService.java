package com.tuoshecx.server.wx.component.client;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.site.domain.SiteWxToken;
import com.tuoshecx.server.cms.site.service.SiteWxService;
import com.tuoshecx.server.wx.component.client.impl.ComponentClients;
import com.tuoshecx.server.wx.component.client.request.*;
import com.tuoshecx.server.wx.component.client.response.*;
import com.tuoshecx.server.wx.component.token.ComponentTokenService;
import com.tuoshecx.server.wx.component.token.ComponentVerifyTicketService;
import com.tuoshecx.server.wx.configure.properties.WxComponentProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * 微信第三方平台客户端业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class ComponentClientService {
    private final WxComponentProperties properties;
    private final ComponentClients clients;
    private final ComponentTokenService tokenService;
    private final ComponentVerifyTicketService verifyTicketService;
    private final SiteWxService wxService;

    @Autowired
    public ComponentClientService(WxComponentProperties properties, RestTemplate restTemplate, ComponentTokenService tokenService,
                                  ComponentVerifyTicketService verifyTicketService, SiteWxService wxService){

        this.properties = properties;
        this.tokenService = tokenService;
        this.verifyTicketService = verifyTicketService;
        this.clients = new ComponentClients(restTemplate);
        this.wxService = wxService;
    }

    public ObtainAccessTokenResponse obtainAccessToken(){
        ObtainAccessTokenRequest request = new ObtainAccessTokenRequest(properties.getAppid(),
                properties.getSecret(), verifyTicketService.get(properties.getAppid()));
        return clients.getObtainAccessTokenClient().request(request);
    }

    public ObtainAuthorizerInfoResponse obtainAuthorizerInfo(String authorizerAppid){
        ObtainAuthorizerInfoRequest request = new ObtainAuthorizerInfoRequest(getComponentToken(properties.getAppid()),
                properties.getAppid(), authorizerAppid);
        return clients.getObtainAuthorizerInfoClient().request(request);
    }

    public ObtainAuthorizerTokenResponse obtainAuthorizerToken(String authorizerAppid, String authorizerRefreshToken){
        ObtainAuthorizerTokenRequest request = new ObtainAuthorizerTokenRequest(getComponentToken(properties.getAppid()),
                properties.getAppid(), authorizerAppid, authorizerRefreshToken);
        return clients.getObtainAuthorizerTokenClient().request(request);
    }

    public ObtainPreAuthCodeResponse obtainPreAuthCode(){
        ObtainPreAuthCodeRequest request = new ObtainPreAuthCodeRequest(
                getComponentToken(properties.getAppid()), properties.getAppid());
        return clients.getObtainPreAuthCodeClient().request(request);
    }

    public ObtainQueryAuthResponse obtainQueryAuth(String authorizationCode){
        ObtainQueryAuthRequest request = new ObtainQueryAuthRequest(
                getComponentToken(properties.getAppid()), properties.getAppid(), authorizationCode);
        return clients.getObtainQueryAuthClient().request(request);
    }

    /**
     * 得到小程序审核状态
     *
     * @param appid   小程序appid
     * @param auditId 审核编号
     * @return 小程序认证输出
     */
    public GetAuditStatusResponse getAuditStatus(String appid, Integer auditId){
        String token = getAccessToken(appid);
        GetAuditStatusRequest request = new GetAuditStatusRequest(token, auditId);

        return clients.getAuditStatusClient().request(request);
    }

    /**
     * 得到小程序目录分类
     *
     * @param appid 小程序appid
     * @return 小程序目录输出
     */
    public GetCategoryResponse getCategory(String appid){
        String token = getAccessToken(appid);
        ComponentRequest request = new ComponentRequest(token);

        return clients.getCategoryClient().request(request);
    }

    /**
     * 上传小程序代码
     *
     * @param appid       微信appid
     * @param templateId  模板编号
     * @param userVersion 用户编号
     * @param userDesc    用户描述
     * @param extJson     模板配置
     * @return {@link ComponentResponse}
     */
    public ComponentResponse programCommit(String appid, Integer templateId,
                                         String userVersion, String userDesc, String extJson){
        String token = getAccessToken(appid);
        ProgramCommitRequest request  = new ProgramCommitRequest(token, templateId, userVersion, userDesc, extJson);

        return clients.programCommitClient().request(request);
    }

    /**
     * 发布微信小程序
     *
     * @param appid 微信appid
     * @return {@link ComponentResponse}
     */
    public ComponentResponse promgramRelease(String appid){
        String token = getAccessToken(appid);
        ComponentRequest request = new ComponentRequest(token);

        return clients.programReleaseClient().request(request);
    }

    /**
     * 设置小程序业务域
     *
     * @param appid  微信appid
     * @param webViewDomain 小程序业务域
     * @return {@link ComponentResponse}
     */
    public ComponentResponse setWebViewDomain(String appid, String[] webViewDomain){
        String token = getAccessToken(appid);
        SetWebViewDomainRequest request = new SetWebViewDomainRequest(token, "set", webViewDomain);

        return clients.setWebViewDomainClient().request(request);
    }

    /**
     *  提交小程序审核
     *
     * @param appid 微信appid
     * @param func  构建请求方法
     * @return {@link SubmitAuditResponse}
     */
    public SubmitAuditResponse  submitAudit(String appid, Consumer<SubmitAuditRequest> consumer){
        String token = getAccessToken(appid);
        SubmitAuditRequest request = new SubmitAuditRequest(token);
        consumer.accept(request);
        return clients.submitAuditClient().request(request);
    }

    /**
     * 更新小程序域名
     *
     * @param appid            微信appid
     * @param requestDomain    http请求域
     * @param wsRequestDomain  webSocket请求域
     * @param uploadDomain     上传域
     * @param downlandDomain   下载域
     * @return {@link ComponentResponse}
     */
    public ComponentResponse updateDomain(String appid, String[] requestDomain, String[] wsRequestDomain,
                                        String[] uploadDomain, String[] downlandDomain){

        String token = getAccessToken(appid);
        UpdateDomainRequest request = new UpdateDomainRequest(token, "add",
                requestDomain, wsRequestDomain, uploadDomain, downlandDomain);

        return clients.updateDomainClient().request(request);
    }

    /**
     * 得到小程序体验二维码
     *
     * @param appid appid
     * @param path  小程序访问页面路径
     * @return {@link GetQrcodeResponse}
     */
    public GetQrcodeResponse getQrocde(String appid, String path){
        GetQrcodeRequest request = new GetQrcodeRequest(getAccessToken(appid), path);
        return clients.getQrcodeClient().request(request);
    }

    /**
     * 绑定小程序测试员
     *
     * @param appid     appid
     * @param wechatid 微信编号
     * @return {@link BindTesterResponse}
     */
    public BindTesterResponse bindTester(String appid, String wechatid){
        BindTesterRequest request = new BindTesterRequest(getAccessToken(appid), wechatid);
        return clients.bindTesterClient().request(request);
    }

    /**
     * 解绑小程序测试员
     *
     * @param appid     appid
     * @param wechatid  微信号
     * @return {@link ComponentResponse}
     */
    public ComponentResponse unbindTester(String appid, String wechatid){
        BindTesterRequest request = new BindTesterRequest(getAccessToken(appid), wechatid);
        return clients.unbindTesterClient().request(request);
    }


    private String getComponentToken(String componentAppid){
        return tokenService.get(componentAppid).orElseThrow(() -> new BaseException(201, "Token不存在"));
    }

    private String getAccessToken(String appid){
        Optional<SiteWxToken> optional = wxService.getToken(appid);
        return optional.orElseThrow(() -> new BaseException(5001, "微信公众号Token失效")).getAccessToken();
    }
}
