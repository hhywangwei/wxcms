package com.tuoshecx.server.wx.component.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.component.client.request.BindTesterRequest;
import com.tuoshecx.server.wx.component.client.response.BindTesterResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 绑定微信测试者请求
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class BindTesterClient extends WxComponentClient<BindTesterRequest, BindTesterResponse> {

    BindTesterClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "bindTester");
    }

    @Override
    protected String buildBody(BindTesterRequest request) {
        return String.format("{\"wechatid\":\"%s\"}", request.getWechatid());
    }

    @Override
    protected String buildUri(BindTesterRequest bindTesterRequest) {
        return "https://api.weixin.qq.com/wxa/bind_tester?access_token={token}";
    }

    @Override
    protected Object[] uriParams(BindTesterRequest bindTesterRequest) {
        return new Object[]{bindTesterRequest.getToken()};
    }

    @Override
    protected BindTesterResponse buildResponse(Map<String, Object> data) {
        return new BindTesterResponse(data);
    }
}
