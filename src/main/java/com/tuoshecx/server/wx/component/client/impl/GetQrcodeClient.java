package com.tuoshecx.server.wx.component.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.component.client.request.GetQrcodeRequest;
import com.tuoshecx.server.wx.component.client.response.GetQrcodeResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class GetQrcodeClient extends WxComponentClient<GetQrcodeRequest, GetQrcodeResponse> {
    private final RestTemplate restTemplate;

    public GetQrcodeClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "getQrcode");
        this.restTemplate = restTemplate;
    }

    @Override
    public GetQrcodeResponse request(GetQrcodeRequest i) {
        return doResponse(restTemplate.getForEntity(buildUri(i), byte[].class, uriParams(i)));
    }

    @Override
    protected String buildUri(GetQrcodeRequest request) {
        return StringUtils.isNotBlank(request.getPath())?
                "https://api.weixin.qq.com/wxa/get_qrcode?access_token={token}&path={path}":
                "https://api.weixin.qq.com/wxa/get_qrcode?access_token={token}";
    }

    @Override
    protected Object[] uriParams(GetQrcodeRequest request) {
        return StringUtils.isNotBlank(request.getPath())?
                new Object[]{request.getToken(), request.getPath()}: new Object[]{request.getToken()};
    }

    @Override
    protected GetQrcodeResponse doResponse(ResponseEntity<byte[]> response) {
        Boolean success = response.getHeaders().getContentType().includes(MediaType.IMAGE_JPEG);
        if(success){
            return new GetQrcodeResponse(response.getBody());
        }else{
            return super.doResponse(response);
        }
    }

    @Override
    protected GetQrcodeResponse buildResponse(Map<String, Object> data) {
        return new GetQrcodeResponse(data);
    }

    @Override
    protected String buildBody(GetQrcodeRequest request) {
        //not instance
        return null;
    }
}
