package com.tuoshecx.server.wx.component.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.component.client.request.ComponentRequest;
import com.tuoshecx.server.wx.component.client.response.ComponentResponse;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 发布已通过审核的小程序（仅供第三方代小程序调用）
 *
 * author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class ProgramReleaseClient extends WxComponentClient<ComponentRequest, ComponentResponse> {

    ProgramReleaseClient(RestTemplate restTemplate, ObjectMapper objectMapper){
        super(restTemplate, objectMapper, "programRelease");
    }

    @Override
    protected String buildBody(ComponentRequest request) {
        return "{}";
    }

    @Override
    protected String buildUri(ComponentRequest request) {
        return "https://api.weixin.qq.com/wxa/release?access_token={token}";
    }

    @Override
    protected Object[] uriParams(ComponentRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected ComponentResponse buildResponse(Map<String, Object> data) {
        return new ComponentResponse(data);
    }
}
