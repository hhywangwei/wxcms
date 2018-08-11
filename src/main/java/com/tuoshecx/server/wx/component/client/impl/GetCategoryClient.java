package com.tuoshecx.server.wx.component.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.component.client.request.ComponentRequest;
import com.tuoshecx.server.wx.component.client.response.GetCategoryResponse;

import org.springframework.web.client.RestTemplate;

import java.util.Map;


class GetCategoryClient extends WxComponentClient<ComponentRequest, GetCategoryResponse> {
    private final RestTemplate restTemplate;

    GetCategoryClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "getCategory");
        this.restTemplate = restTemplate;
    }

    @Override
    public GetCategoryResponse request(ComponentRequest request) {
        return doResponse(restTemplate.getForEntity(buildUri(request), byte[].class, uriParams(request)));
    }

    @Override
    protected String buildUri(ComponentRequest wxSmallRequest) {
        return "https://api.weixin.qq.com/wxa/get_category?access_token={token}";
    }

    @Override
    protected Object[] uriParams(ComponentRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected String buildBody(ComponentRequest wxSmallRequest) {
        //not instance
        return null;
    }

    @Override
    protected GetCategoryResponse buildResponse(Map<String, Object> data) {
        return new GetCategoryResponse(data);
    }
}
