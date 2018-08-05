package com.tuoshecx.server.cms.site.schedule;

import com.tuoshecx.server.cms.site.domain.SiteWxToken;
import com.tuoshecx.server.cms.site.service.SiteWxService;
import com.tuoshecx.server.wx.component.client.ComponentClientService;
import com.tuoshecx.server.wx.component.client.response.ObtainAuthorizerTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 店铺定时任务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Component
public class SiteSchedule {
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteSchedule.class);
    private static final int LIMIT = 50;
    private final SiteWxService service;
    private final ComponentClientService clientService;

    @Autowired
    public SiteSchedule(SiteWxService service, ComponentClientService clientService) {
        this.service = service;
        this.clientService = clientService;
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000L, initialDelay = 30 * 1000L)
    public void refreshToken(){
        int row;
        do{
            List<SiteWxToken> list = service.queryExpiresToken(LIMIT);
            list.forEach(this::refreshToken);
            row = list.size();
        }while (row == LIMIT);
    }

    private void refreshToken(SiteWxToken t){
        try{
            ObtainAuthorizerTokenResponse response = clientService.obtainAuthorizerToken(t.getAppid(), t.getRefreshToken());
            if(response.getCode() == 0){
                SiteWxToken o = new SiteWxToken();
                o.setAppid(t.getAppid());
                o.setAccessToken(response.getAuthorizerAccessToken());
                o.setRefreshToken(response.getAuthorizerRefreshToken());
                Date now = new Date();
                long expireTime = System.currentTimeMillis() + (response.getExpiresIn() - 10 * 60)* 1000L;
                o.setExpiresTime(new Date(expireTime));
                o.setUpdateTime(now);

                service.saveToken(o);
            }else{
                LOGGER.error("Refresh {} token fail, code {} message {}", t.getAppid(), response.getCode(), response.getMessage());
            }
        }catch (Exception e){
            LOGGER.error("Refresh token fail, appid {} error {}", t.getAppid(), e.getMessage());
        }
    }
}
