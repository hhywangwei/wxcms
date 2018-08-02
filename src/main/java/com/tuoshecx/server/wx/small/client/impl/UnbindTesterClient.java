package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.BindTesterRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 解除测试者请求
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UnbindTesterClient extends WxSmallClient<BindTesterRequest, WxSmallResponse> {

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
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return new WxSmallResponse(data);
    }
}
