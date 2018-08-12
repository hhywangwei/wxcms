package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.cms.common.client.HttpClient;
import com.tuoshecx.server.wx.small.client.request.*;
import com.tuoshecx.server.wx.small.client.response.*;
import org.springframework.web.client.RestTemplate;

/**
 * 微信小程序API接口请求处理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class WxSmallClients {
    private final HttpClient<LoginRequest, LoginResponse> loginClient;
    private final HttpClient<SendTemplateMsgRequest, WxSmallResponse> sendTmpMsgClient;
    private final HttpClient<SendCustomMsgRequest, WxSmallResponse> sendCustomMsgClient;
    private final HttpClient<MessageTemplateAddRequest, MessageTemplateAddResponse> messageTemplateAddClient;
    private final HttpClient<MessageTemplateDelRequest, WxSmallResponse> messageTemplateDelClient;
    private final HttpClient<MessageTemplateQueryRequest, MessageTemplateQueryResponse> messageTemplateQueryClient;
    private final HttpClient<AnalysisRequest, VisitTrendAnalysisResponse> dailyVisitTrendAnalysisClient;
    private final HttpClient<AnalysisRequest, VisitTrendAnalysisResponse> weekVisitTrendAnalysisClient;
    private final HttpClient<AnalysisRequest, VisitTrendAnalysisResponse> monthVisitTrendAnalysisClient;
    private final HttpClient<AnalysisRequest, SummaryTrendAnalysisResponse> summaryTrendAnalysisClient;
    private final HttpClient<AnalysisRequest, VisitPageResponse> visitPageClient;
    private final HttpClient<GetSmallQrcodeRequest, SmallQrcodeResponse> getSmallQrcodeClient;
    private final HttpClient<GetSmallQrcodeLimitRequest, SmallQrcodeResponse> getSmallQrcodeLimitClient;


    public WxSmallClients(RestTemplate restTemplate) {
        ObjectMapper objectMapper = initObjectMapper();
        this.loginClient = new LoginClient(restTemplate, objectMapper);
        this.sendTmpMsgClient = new SendTemplateMsgClient(restTemplate, objectMapper);
        this.sendCustomMsgClient = new SendCustomMsgClient(restTemplate, objectMapper);
        this.messageTemplateAddClient = new MessageTemplateAddClient(restTemplate, objectMapper);
        this.messageTemplateDelClient = new MessageTemplateDelClient(restTemplate, objectMapper);
        this.messageTemplateQueryClient = new MessageTemplateQueryClient(restTemplate, objectMapper);
        this.dailyVisitTrendAnalysisClient = new DailyVisitTrendAnalysisClient(restTemplate, objectMapper);
        this.weekVisitTrendAnalysisClient = new WeekVisitTrendAnalysisClient(restTemplate, objectMapper);
        this.monthVisitTrendAnalysisClient = new MonthVisitTrendAnalysisClient(restTemplate, objectMapper);
        this.summaryTrendAnalysisClient = new SummaryTrendAnalysisClient(restTemplate, objectMapper);
        this.visitPageClient = new VisitPageAnalysisClient(restTemplate, objectMapper);
        this.getSmallQrcodeClient = new GetSmallQrcodeClient(restTemplate, objectMapper);
        this.getSmallQrcodeLimitClient = new GetSmallQrcodeLimitClient(restTemplate, objectMapper);
    }

    private ObjectMapper initObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return mapper;
    }

    public HttpClient<LoginRequest, LoginResponse> loginClient(){
        return loginClient;
    }

    public HttpClient<SendTemplateMsgRequest, WxSmallResponse> sendTmpMsgClient(){
        return sendTmpMsgClient;
    }

    public HttpClient<SendCustomMsgRequest, WxSmallResponse> sendCustomMsgClient() {
        return sendCustomMsgClient;
    }

    public HttpClient<MessageTemplateAddRequest, MessageTemplateAddResponse> messageTemplateAddClient(){
        return messageTemplateAddClient;
    }

    public HttpClient<MessageTemplateDelRequest, WxSmallResponse> messageTemplateDelClient(){
        return messageTemplateDelClient;
    }

    public HttpClient<MessageTemplateQueryRequest, MessageTemplateQueryResponse> messageTemplateQueryClient(){
        return messageTemplateQueryClient;
    }

    public HttpClient<AnalysisRequest, VisitTrendAnalysisResponse> dailyVisitTrendAnalysisClient(){
        return dailyVisitTrendAnalysisClient;
    }

    public HttpClient<AnalysisRequest, VisitTrendAnalysisResponse> weekVisitTrendAnalysisClient(){
        return weekVisitTrendAnalysisClient;
    }

    public HttpClient<AnalysisRequest, VisitTrendAnalysisResponse> monthVisitTrendAnalysisClient(){
        return monthVisitTrendAnalysisClient;
    }

    public HttpClient<AnalysisRequest, SummaryTrendAnalysisResponse> summaryTrendAnalysisHttpClient(){
        return summaryTrendAnalysisClient;
    }

    public HttpClient<AnalysisRequest, VisitPageResponse> visitPageClient(){
        return visitPageClient;
    }

    public HttpClient<GetSmallQrcodeRequest, SmallQrcodeResponse> getSmallQrcodeClient(){
        return getSmallQrcodeClient;
    }

    public HttpClient<GetSmallQrcodeLimitRequest, SmallQrcodeResponse> getSmallQrcodeLimitClient(){
        return getSmallQrcodeLimitClient;
    }
}
