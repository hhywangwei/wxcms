package com.tuoshecx.server.wx.small.devops.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.site.service.SiteWxService;
import com.tuoshecx.server.wx.small.client.WxSmallClientService;
import com.tuoshecx.server.wx.small.client.response.BindTesterResponse;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;
import com.tuoshecx.server.wx.small.devops.dao.SmallTesterDao;
import com.tuoshecx.server.wx.small.devops.domain.SmallTester;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 绑定小程序测试业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class SmallTesterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmallTesterService.class);

    private final SmallTesterDao dao;
    private final WxSmallClientService clientService;
    private final SiteWxService wxService;

    @Autowired
    public SmallTesterService(SmallTesterDao dao, WxSmallClientService clientService, SiteWxService wxService) {
        this.dao = dao;
        this.clientService = clientService;
        this.wxService = wxService;
    }

    public SmallTester get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("小程序测试员不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SmallTester bindTest(String siteId, String wechatid){
        if (dao.has(siteId, wechatid)) {
            throw new BaseException("已经绑定了测试");
        }

        Optional<String> optional = wxService.getAppid(siteId);
        if(!optional.isPresent()){
            throw new BaseException("还未绑定小程序公众号");
        }

        String appid = optional.get();
        BindTesterResponse response = clientService.bindTester(appid, wechatid);
        if(!response.isOk()){
            LOGGER.error("Bind small tester fail, error is {}-{}", response.getCode(), response.getMessage());
            throw new BaseException("绑定测试失败");
        }

        SmallTester t = new SmallTester();

        t.setId(IdGenerators.uuid());
        t.setWechatid(wechatid);
        t.setSiteId(siteId);
        t.setAppid(appid);
        t.setUserstr(response.getUserstr());

        dao.insert(t);
        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean unbindTester(String id, String siteId){
        try{
            SmallTester t = get(id);
            if(!StringUtils.equals(t.getSiteId(), siteId)){
                throw new BaseException("解绑小程序测试员不存在");
            }
            WxSmallResponse response = clientService.unbindTester(t.getAppid(), t.getWechatid());
            if(response.isOk()){
                dao.delete(t.getId());
            }
            return response.isOk();
        }catch (DataAccessException e){
            LOGGER.error("Unbind wechatid fail, error is {}", e.getMessage());
            return false;
        }
    }

    public List<SmallTester> query(String siteId){
        return dao.find(siteId);
    }
}
