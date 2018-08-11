package com.tuoshecx.server.wx.component.client.request;

public class GetAuditStatusRequest extends ComponentRequest {
    private final Integer auditId;

    public GetAuditStatusRequest(String token, Integer auditId) {
        super(token);
        this.auditId = auditId;
    }

    public Integer getAuditId() {
        return auditId;
    }
}
