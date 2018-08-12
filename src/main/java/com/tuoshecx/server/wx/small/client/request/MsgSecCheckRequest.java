package com.tuoshecx.server.wx.small.client.request;

public class MsgSecCheckRequest extends WxSmallRequest {
    private final String content;

    public MsgSecCheckRequest(String token, String content) {
        super(token);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
