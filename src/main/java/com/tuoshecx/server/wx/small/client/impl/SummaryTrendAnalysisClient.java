package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.AnalysisRequest;
import com.tuoshecx.server.wx.small.client.response.SummaryTrendAnalysisResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 微信小程序概况趋势
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class SummaryTrendAnalysisClient extends WxSmallClient<AnalysisRequest, SummaryTrendAnalysisResponse> {

    SummaryTrendAnalysisClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "summaryTrend");
    }

    @Override
    protected String buildBody(AnalysisRequest request) {
        return String.format("{\"begin_date\": \"%s\", \"end_date\": \"%s\"}",
                request.getBeginDate(), request.getEndDate());
    }

    @Override
    protected String buildUri(AnalysisRequest request) {
        return "https://api.weixin.qq.com/datacube/getweanalysisappiddailysummarytrend?access_token={token}";
    }

    @Override
    protected Object[] uriParams(AnalysisRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected SummaryTrendAnalysisResponse buildResponse(Map<String, Object> data) {
        return new SummaryTrendAnalysisResponse(data);
    }
}
