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
    private final HttpClient<GetAuditStatusRequest, GetAuditStatusResponse> getAuditStatusClient;
    private final HttpClient<WxSmallRequest, GetCategoryResponse> getCategoryClient;
    private final HttpClient<ProgramCommitRequest, WxSmallResponse> programCommitClient;
    private final HttpClient<WxSmallRequest, WxSmallResponse> programReleaseClient;
    private final HttpClient<SetWebViewDomainRequest, WxSmallResponse> setWebViewDomainClient;
    private final HttpClient<SubmitAuditRequest, SubmitAuditResponse> submitAuditClient;
    private final HttpClient<UpdateDomainRequest, WxSmallResponse> updateDomainClient;
    private final HttpClient<MessageTemplateAddRequest, MessageTemplateAddResponse> messageTemplateAddClient;
    private final HttpClient<MessageTemplateDelRequest, WxSmallResponse> messageTemplateDelClient;
    private final HttpClient<MessageTemplateQueryRequest, MessageTemplateQueryResponse> messageTemplateQueryClient;
    private final HttpClient<GetQrcodeRequest, GetQrcodeResponse> getQrcodeClient;
    private final HttpClient<BindTesterRequest, BindTesterResponse> bindTesterClient;
    private final HttpClient<BindTesterRequest, WxSmallResponse> unbindTesterClient;
    private final HttpClient<AnalysisRequest, VisitTrendAnalysisResponse> dailyVisitTrendAnalysisClient;
    private final HttpClient<AnalysisRequest, VisitTrendAnalysisResponse> weekVisitTrendAnalysisClient;
    private final HttpClient<AnalysisRequest, VisitTrendAnalysisResponse> monthVisitTrendAnalysisClient;
    private final HttpClient<AnalysisRequest, SummaryTrendAnalysisResponse> summaryTrendAnalysisClient;
    private final HttpClient<AnalysisRequest, VisitPageResponse> visitPageClient;


    public WxSmallClients(RestTemplate restTemplate) {
        ObjectMapper objectMapper = initObjectMapper();
        this.loginClient = new LoginClient(restTemplate, objectMapper);
        this.sendTmpMsgClient = new SendTemplateMsgClient(restTemplate, objectMapper);
        this.sendCustomMsgClient = new SendCustomMsgClient(restTemplate, objectMapper);
        this.getAuditStatusClient = new GetAuditStatusClient(restTemplate, objectMapper);
        this.getCategoryClient = new GetCategoryClient(restTemplate, objectMapper);
        this.programCommitClient = new ProgramCommitClient(restTemplate, objectMapper);
        this.programReleaseClient = new ProgramReleaseClient(restTemplate, objectMapper);
        this.setWebViewDomainClient = new SetWebViewDomainClient(restTemplate, objectMapper);
        this.submitAuditClient = new SubmitAuditClient(restTemplate, objectMapper);
        this.updateDomainClient = new UpdateDomainClient(restTemplate, objectMapper);
        this.messageTemplateAddClient = new MessageTemplateAddClient(restTemplate, objectMapper);
        this.messageTemplateDelClient = new MessageTemplateDelClient(restTemplate, objectMapper);
        this.messageTemplateQueryClient = new MessageTemplateQueryClient(restTemplate, objectMapper);
        this.getQrcodeClient = new GetQrcodeClient(restTemplate, objectMapper);
        this.bindTesterClient = new BindTesterClient(restTemplate, objectMapper);
        this.unbindTesterClient = new UnbindTesterClient(restTemplate, objectMapper);
        this.dailyVisitTrendAnalysisClient = new DailyVisitTrendAnalysisClient(restTemplate, objectMapper);
        this.weekVisitTrendAnalysisClient = new WeekVisitTrendAnalysisClient(restTemplate, objectMapper);
        this.monthVisitTrendAnalysisClient = new MonthVisitTrendAnalysisClient(restTemplate, objectMapper);
        this.summaryTrendAnalysisClient = new SummaryTrendAnalysisClient(restTemplate, objectMapper);
        this.visitPageClient = new VisitPageAnalysisClient(restTemplate, objectMapper);
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

    public HttpClient<GetAuditStatusRequest, GetAuditStatusResponse> getAuditStatusClient() {
        return getAuditStatusClient;
    }

    public HttpClient<WxSmallRequest, GetCategoryResponse> getCategoryClient() {
        return getCategoryClient;
    }

    public HttpClient<ProgramCommitRequest, WxSmallResponse> programCommitClient() {
        return programCommitClient;
    }

    public HttpClient<WxSmallRequest, WxSmallResponse> programReleaseClient() {
        return programReleaseClient;
    }

    public HttpClient<SetWebViewDomainRequest, WxSmallResponse> setWebViewDomainClient() {
        return setWebViewDomainClient;
    }

    public HttpClient<SubmitAuditRequest, SubmitAuditResponse> submitAuditClient() {
        return submitAuditClient;
    }

    public HttpClient<UpdateDomainRequest, WxSmallResponse> updateDomainClient() {
        return updateDomainClient;
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

    public HttpClient<GetQrcodeRequest, GetQrcodeResponse> getQrcodeClient(){
        return getQrcodeClient;
    }

    public HttpClient<BindTesterRequest, BindTesterResponse> bindTesterClient(){
        return bindTesterClient;
    }

    public HttpClient<BindTesterRequest, WxSmallResponse> unbindTesterClient(){
        return unbindTesterClient;
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
}
