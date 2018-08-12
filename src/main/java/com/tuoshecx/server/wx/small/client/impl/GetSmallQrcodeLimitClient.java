package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.GetSmallQrcodeLimitRequest;
import com.tuoshecx.server.wx.small.client.response.SmallQrcodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 生成微信小程序场景二维码
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class GetSmallQrcodeLimitClient extends WxSmallClient<GetSmallQrcodeLimitRequest, SmallQrcodeResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetSmallQrcodeLimitClient.class);

    public GetSmallQrcodeLimitClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "getSmallQrcodeLimit");
    }

    @Override
    protected String buildBody(GetSmallQrcodeLimitRequest request) {
        return String.format("{\"path\":\"%s\", \"scene\": \"%s\", \"width\": %d, \"is_hyaline\": %b}",
                request.getPath(), request.getScene(), request.getWidth(), request.isHyaline());
    }

    @Override
    protected String buildUri(GetSmallQrcodeLimitRequest request) {
        return "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token={token}";
    }

    @Override
    protected Object[] uriParams(GetSmallQrcodeLimitRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected SmallQrcodeResponse parseResponse(ResponseEntity<byte[]> response) {
        MediaType contentType = response.getHeaders().getContentType();
        boolean error = contentType == null || response.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON);
        if(error){
            byte[] bytes= response.getBody();
            String body = bytes == null?  "": new String(bytes, UTF_8_CHARSET);
            LOGGER.error("Get wx small qrcode fail, error is {}", body);
            return new SmallQrcodeResponse(205,"得到微信二维码错误", null);
        }else{
            return new SmallQrcodeResponse(0, "", response.getBody());
        }
    }

    @Override
    protected SmallQrcodeResponse buildResponse(Map<String, Object> data) {
        //none instance
        return null;
    }
}
