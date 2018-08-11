package com.tuoshecx.server.wx.component.client.response;

import java.util.Map;

public class SubmitAuditResponse extends ComponentResponse {
    private final Integer auditId;

    public SubmitAuditResponse(Map<String, Object> data) {
        super(data);
        this.auditId = (Integer)data.getOrDefault("auditid", -1);
    }

    public Integer getAuditId() {
        return auditId;
    }
}
