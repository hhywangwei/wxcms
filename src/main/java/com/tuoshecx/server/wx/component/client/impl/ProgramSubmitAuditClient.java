package com.tuoshecx.server.wx.component.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.component.client.request.ProgramSubmitAuditRequest;
import com.tuoshecx.server.wx.component.client.response.ProgramSubmitAuditResponse;
import org.apache.commons.lang3.StringUtils;

import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 将第三方提交的代码包提交审核（仅供第三方开发者代小程序调用）
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class ProgramSubmitAuditClient extends WxComponentClient<ProgramSubmitAuditRequest, ProgramSubmitAuditResponse> {

    ProgramSubmitAuditClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "submitAuditClient");
    }

    @Override
    protected String buildUri(ProgramSubmitAuditRequest request) {
        return "https://api.weixin.qq.com/wxa/submit_audit?access_token={token}";
    }

    @Override
    protected Object[] uriParams(ProgramSubmitAuditRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected String buildBody(ProgramSubmitAuditRequest request){
        StringBuilder builder = new StringBuilder(200);
        builder.append("{");
        builder.append("\"item_list\":[");
        builder.append(request.getItems().stream().map(this::toItem).collect(Collectors.joining(",")));
        builder.append("]}");
        return builder.toString();
    }

    private String toItem(ProgramSubmitAuditRequest.SubmitAuditItem item){
        StringBuilder builder = new StringBuilder(50);

        builder.append("{\"address\":\"").append(item.getAddress()).append("\",");
        builder.append("\"tag\":\"").append(item.getTag()).append("\",");
        builder.append("\"first_class\":\"").append(item.getFirstClass()).append("\",");
        builder.append("\"first_id\":").append(item.getFirstId()).append(",");
        if(StringUtils.isNotBlank(item.getSecondClass())){
            builder.append("\"second_class\":\"").append(item.getSecondClass()).append("\",");
            builder.append("\"second_id\":").append(item.getSecondId()).append(",");
        }
        if(StringUtils.isNotBlank(item.getThirdClass())){
            builder.append("\"third_class\":\"").append(item.getThirdClass()).append("\",");
            builder.append("\"third_id\":").append(item.getThirdId()).append(",");
        }
        builder.append("\"title\":\"").append(item.getTitle()).append("\"}");

        return builder.toString();
    }

    @Override
    protected ProgramSubmitAuditResponse buildResponse(Map<String, Object> data) {
        return new ProgramSubmitAuditResponse(data);
    }
}
