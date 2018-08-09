package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.AnalysisRequest;
import org.springframework.web.client.RestTemplate;

/**
 * 微信小程序月趋势请求
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class MonthVisitTrendAnalysisClient extends DailyVisitTrendAnalysisClient {

    MonthVisitTrendAnalysisClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "monthVisitTrendAnalysis");
    }

    @Override
    protected String buildUri(AnalysisRequest request) {
        return "https://api.weixin.qq.com/datacube/getweanalysisappidmonthlyvisittrend?access_token={token}";
    }
}
