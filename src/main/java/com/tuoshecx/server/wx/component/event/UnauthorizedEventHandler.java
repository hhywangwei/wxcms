package com.tuoshecx.server.wx.component.event;

import com.tuoshecx.server.cms.site.service.SiteWxService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.Map;

class UnauthorizedEventHandler extends BaseEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnauthorizedEventHandler.class);

    private final SiteWxService service;

    UnauthorizedEventHandler(SiteWxService service){
        this.service = service;
    }

    @Override
    protected boolean isHandler(String infoType) {
        return StringUtils.equals(infoType, "unauthorized");
    }

    @Override
    protected ResponseEntity<String> doHandler(Map<String, String> data) {
        String appid = data.get("AuthorizerAppid");
        LOGGER.debug("Unauthorized appid {}", appid);
        service.unauthorized(appid);
        return ResponseEntity.ok("success");
    }
}
