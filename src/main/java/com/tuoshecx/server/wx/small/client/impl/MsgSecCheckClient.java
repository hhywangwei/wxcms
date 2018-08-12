package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.MsgSecCheckRequest;
import com.tuoshecx.server.wx.small.client.response.MsgSecCheckResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 检查一段文本是否含有违法违规内容。
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class MsgSecCheckClient extends WxSmallClient<MsgSecCheckRequest, MsgSecCheckResponse> {

    public MsgSecCheckClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "msgSecCheck");
    }

    @Override
    protected String buildBody(MsgSecCheckRequest request) {
        return String.format("{\"content\": \"%s\"}", request.getContent());
    }

    @Override
    protected String buildUri(MsgSecCheckRequest msgSecCheckRequest) {
        return "https://api.weixin.qq.com/wxa/msg_sec_check?access_token={token}";
    }

    @Override
    protected Object[] uriParams(MsgSecCheckRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected MsgSecCheckResponse buildResponse(Map<String, Object> data) {
        return new MsgSecCheckResponse(data);
    }
}
