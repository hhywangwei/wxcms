package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.AnalysisRequest;
import com.tuoshecx.server.wx.small.client.response.VisitTrendAnalysisResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 微信小程序日趋势请求
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class DailyVisitTrendAnalysisClient extends WxSmallClient<AnalysisRequest, VisitTrendAnalysisResponse>{

    DailyVisitTrendAnalysisClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this(restTemplate, objectMapper, "dailyVisitTrendAnalysis");
    }

    protected DailyVisitTrendAnalysisClient(RestTemplate restTemplate, ObjectMapper objectMapper, String clientName) {
        super(restTemplate, objectMapper, clientName);
    }

    @Override
    protected String buildBody(AnalysisRequest request) {
        return String.format("{\"begin_date\": \"%s\", \"end_date\": \"%s\"}",
                request.getBeginDate(), request.getEndDate());
    }

    @Override
    protected String buildUri(AnalysisRequest request) {
        return "https://api.weixin.qq.com/datacube/getweanalysisappiddailyvisittrend?access_token={token}";
    }

    @Override
    protected Object[] uriParams(AnalysisRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected VisitTrendAnalysisResponse buildResponse(Map<String, Object> data) {
        return new VisitTrendAnalysisResponse(data);
    }
}
