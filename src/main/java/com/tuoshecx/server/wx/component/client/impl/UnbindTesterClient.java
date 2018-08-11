package com.tuoshecx.server.wx.component.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.component.client.response.ComponentResponse;
import com.tuoshecx.server.wx.component.client.request.BindTesterRequest;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 解除测试者请求
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UnbindTesterClient extends WxComponentClient<BindTesterRequest, ComponentResponse> {

    public UnbindTesterClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "unbindTester");
    }

    @Override
    protected String buildBody(BindTesterRequest request) {
        return String.format("{\"wechatid\":\"%s\"}", request.getWechatid());
    }

    @Override
    protected String buildUri(BindTesterRequest request) {
        return "https://api.weixin.qq.com/wxa/unbind_tester?access_token={token}";
    }

    @Override
    protected Object[] uriParams(BindTesterRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected ComponentResponse buildResponse(Map<String, Object> data) {
        return new ComponentResponse(data);
    }
}
