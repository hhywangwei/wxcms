package com.tuoshecx.server.cms.tencent.map.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeBase;
import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.common.client.HttpClient;
import com.tuoshecx.server.cms.tencent.map.client.request.QqMapRequest;
import com.tuoshecx.server.cms.tencent.map.client.response.QqMapResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现腾讯地图调用
 *
 * @param <I> 请求对象类型
 * @param <O> 输出对象类型
 */
 abstract class TcMapClient<I extends QqMapRequest, O extends QqMapResponse> implements HttpClient<I, O> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TcMapClient.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final TypeBase mapType;
    private final String clientName;

    TcMapClient(RestTemplate restTemplate, ObjectMapper objectMapper, String clientName) {
        this.restTemplate = restTemplate;
        this.clientName = clientName;
        this.objectMapper = objectMapper;
        this.mapType = objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
    }

     @Override
     public O request(I i) {
         return doResponse(restTemplate.getForEntity(buildUri(i), byte[].class, uriParams(i)));
     }

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
     private O doResponse(ResponseEntity<byte[]> response){

         if(!response.getStatusCode().is2xxSuccessful()){
             LOGGER.error("Wx small {} http request fail, status code {}", clientName, response.getStatusCodeValue());
             Map<String, Object> map = new HashMap<>();
             map.put("errcode", -1000);
             map.put("errmsg", "Http state code is " + response.getStatusCodeValue());
             return buildResponse(map);
         }

         try{
             String content = new String(response.getBody(), UTF_8_CHARSET);
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
