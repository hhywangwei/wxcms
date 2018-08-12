package com.tuoshecx.server.wx.small.client.response;

import java.util.Map;

public class MsgSecCheckResponse extends WxSmallResponse {

    public MsgSecCheckResponse(Map<String, Object> data) {
        super((Integer) data.getOrDefault("errcode", 0),
                (String)data.getOrDefault("errMsg", ""));
    }
}
