package com.tuoshecx.server.wx.component.client.response;

import java.util.Map;

public class GetAuditStatusResponse extends ComponentResponse {
    private final Integer status;
    private final String reason;

    public GetAuditStatusResponse(Map<String, Object> data) {
        super(data);
        this.status = (Integer) data.getOrDefault("status", 0);
        this.reason = (String)data.getOrDefault("reason", "");
    }

    public Integer getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }
}
