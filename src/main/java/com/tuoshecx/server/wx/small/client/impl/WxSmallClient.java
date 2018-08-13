package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeBase;
import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.common.client.HttpClient;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现微信小程序请求
 *
 * @param <I> 输出参数类型
 * @param <O> 输出数据类型
 */
abstract class WxSmallClient<I, O extends WxSmallResponse> implements HttpClient<I, O> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxSmallClient.class);
    static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final TypeBase mapType;
    private final String clientName;

    /**
     * 构造{@link WxSmallClient}
     *
     * @param restTemplate {@link RestTemplate}
     * @param objectMapper {@link ObjectMapper}
     * @param clientName 调用API名称
     */
    public WxSmallClient(RestTemplate restTemplate, ObjectMapper objectMapper, String clientName){
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.clientName = clientName;
        this.mapType = objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
    }

    @Override
    public O request(I i) {
        HttpEntity<byte[]> requestEntity = buildRequestEntity(i);
        return doResponse(restTemplate.postForEntity(buildUri(i),requestEntity, byte[].class, uriParams(i)));
    }

    /**
     * 构建请求对象
     *
     * @param i 请求参数对象
     * @return {@link HttpEntity}
     */
    private HttpEntity<byte[]> buildRequestEntity(I i){
        String body = buildBody(i);
        LOGGER.debug("Wx small client name {} request body {}", clientName, body);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new HttpEntity<>(body.getBytes(UTF_8_CHARSET), headers);
    }

    protected abstract String buildBody(I i);

    /**
     * 构建请求URI
     *
     * @param i 请求对象
     * @return 访问URI
     */
    protected abstract String buildUri(I i);

    /**
     * 构建uri参数
     *
     * @param i 请求对象
     * @return uri查询
     */
    protected abstract Object[] uriParams(I i);


    /**
     * API接口输出信息日志
     *
     * @param response 输出对象
     * @return 输出对象
     */
    protected O doResponse(ResponseEntity<byte[]> response){

        if(!response.getStatusCode().is2xxSuccessful()){
            LOGGER.error("Wx small {} http request fail, status code {}", clientName, response.getStatusCodeValue());
            Map<String, Object> map = new HashMap<>();
            map.put("errcode", -1000);
            map.put("errmsg", "Http state code is " + response.getStatusCodeValue());
            return buildResponse(map);
        }
        return parseResponse(response);
    }

    /**
     *  解析body为输出对象
     *
     * @param response 输出对象
     * @return 输出对象
     */
    protected O parseResponse(ResponseEntity<byte[]> response){
        try{
            byte[] bytes = response.getBody();
            String content =bytes == null? "{}": new String(bytes, UTF_8_CHARSET);
            LOGGER.debug("{} Response is {}", clientName, content);
            return buildResponse(objectMapper.readValue(content, mapType));
        }catch (Exception e){
            throw new BaseException("解析Json错误");
        }
    }

    /**
     * 构造输出对象
     *
     * @param data 输出数据
     * @return 输出对象
     */
    protected abstract O buildResponse(Map<String, Object> data);
}
