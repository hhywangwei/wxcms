package com.tuoshecx.server.wx.component.client.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.cms.common.client.HttpClient;
import com.tuoshecx.server.wx.component.client.request.*;
import com.tuoshecx.server.wx.component.client.response.*;
import com.tuoshecx.server.wx.small.client.request.WxSmallRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;
import org.springframework.web.client.RestTemplate;

/**
 * 微信第三方平台客户端
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ComponentClients {

    private final HttpClient<ObtainAccessTokenRequest, ObtainAccessTokenResponse> obtainAccessTokenClient;
    private final HttpClient<ObtainAuthorizerInfoRequest, ObtainAuthorizerInfoResponse> obtainAuthorizerInfoClient;
    private final HttpClient<ObtainAuthorizerTokenRequest, ObtainAuthorizerTokenResponse> obtainAuthorizerTokenClient;
    private final HttpClient<ObtainPreAuthCodeRequest, ObtainPreAuthCodeResponse> obtainPreAuthCodeClient;
    private final HttpClient<ObtainQueryAuthRequest, ObtainQueryAuthResponse> obtainQueryAuthClient;
    private final HttpClient<GetAuditStatusRequest, GetAuditStatusResponse> getAuditStatusClient;
    private final HttpClient<ComponentRequest, GetCategoryResponse> getCategoryClient;
    private final HttpClient<ProgramCommitRequest, ComponentResponse> programCommitClient;
    private final HttpClient<ComponentRequest, ComponentResponse> programReleaseClient;
    private final HttpClient<SetWebViewDomainRequest, ComponentResponse> setWebViewDomainClient;
    private final HttpClient<SubmitAuditRequest, SubmitAuditResponse> submitAuditClient;
    private final HttpClient<UpdateDomainRequest, ComponentResponse> updateDomainClient;
    private final HttpClient<GetQrcodeRequest, GetQrcodeResponse> getQrcodeClient;
    private final HttpClient<BindTesterRequest, BindTesterResponse> bindTesterClient;
    private final HttpClient<BindTesterRequest, ComponentResponse> unbindTesterClient;

    public ComponentClients(RestTemplate restTemplate){
        ObjectMapper objectMapper = initObjectMapper();
        this.obtainAccessTokenClient = new ObtainAccessTokenClient(restTemplate, objectMapper);
        this.obtainAuthorizerInfoClient = new ObtainAuthorizerInfoClient(restTemplate, objectMapper);
        this.obtainAuthorizerTokenClient = new ObtainAuthorizerTokenClient(restTemplate, objectMapper);
        this.obtainPreAuthCodeClient = new ObtainPreAuthCodeClient(restTemplate, objectMapper);
        this.obtainQueryAuthClient = new ObtainQueryAuthClient(restTemplate, objectMapper);
        this.getAuditStatusClient = new GetAuditStatusClient(restTemplate, objectMapper);
        this.getCategoryClient = new GetCategoryClient(restTemplate, objectMapper);
        this.programCommitClient = new ProgramCommitClient(restTemplate, objectMapper);
        this.programReleaseClient = new ProgramReleaseClient(restTemplate, objectMapper);
        this.setWebViewDomainClient = new SetWebViewDomainClient(restTemplate, objectMapper);
        this.submitAuditClient = new SubmitAuditClient(restTemplate, objectMapper);
        this.updateDomainClient = new UpdateDomainClient(restTemplate, objectMapper);
        this.getQrcodeClient = new GetQrcodeClient(restTemplate, objectMapper);
        this.bindTesterClient = new BindTesterClient(restTemplate, objectMapper);
        this.unbindTesterClient = new UnbindTesterClient(restTemplate, objectMapper);
    }

    private ObjectMapper initObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return mapper;
    }

    public HttpClient<ObtainAccessTokenRequest, ObtainAccessTokenResponse> getObtainAccessTokenClient() {
        return obtainAccessTokenClient;
    }

    public HttpClient<ObtainAuthorizerInfoRequest, ObtainAuthorizerInfoResponse> getObtainAuthorizerInfoClient() {
        return obtainAuthorizerInfoClient;
    }

    public HttpClient<ObtainAuthorizerTokenRequest, ObtainAuthorizerTokenResponse> getObtainAuthorizerTokenClient() {
        return obtainAuthorizerTokenClient;
    }

    public HttpClient<ObtainPreAuthCodeRequest, ObtainPreAuthCodeResponse> getObtainPreAuthCodeClient() {
        return obtainPreAuthCodeClient;
    }

    public HttpClient<ObtainQueryAuthRequest, ObtainQueryAuthResponse> getObtainQueryAuthClient() {
        return obtainQueryAuthClient;
    }

    public HttpClient<GetAuditStatusRequest, GetAuditStatusResponse> getAuditStatusClient() {
        return getAuditStatusClient;
    }

    public HttpClient<ComponentRequest, GetCategoryResponse> getCategoryClient() {
        return getCategoryClient;
    }

    public HttpClient<ProgramCommitRequest, ComponentResponse> programCommitClient() {
        return programCommitClient;
    }

    public HttpClient<ComponentRequest, ComponentResponse> programReleaseClient() {
        return programReleaseClient;
    }

    public HttpClient<SetWebViewDomainRequest, ComponentResponse> setWebViewDomainClient() {
        return setWebViewDomainClient;
    }

    public HttpClient<SubmitAuditRequest, SubmitAuditResponse> submitAuditClient() {
        return submitAuditClient;
    }

    public HttpClient<UpdateDomainRequest, ComponentResponse> updateDomainClient() {
        return updateDomainClient;
    }

    public HttpClient<GetQrcodeRequest, GetQrcodeResponse> getQrcodeClient(){
        return getQrcodeClient;
    }

    public HttpClient<BindTesterRequest, BindTesterResponse> bindTesterClient(){
        return bindTesterClient;
    }

    public HttpClient<BindTesterRequest, ComponentResponse> unbindTesterClient(){
        return unbindTesterClient;
    }
}
