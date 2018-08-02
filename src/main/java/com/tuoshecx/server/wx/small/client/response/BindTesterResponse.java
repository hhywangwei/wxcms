package com.tuoshecx.server.wx.small.client.response;

import java.util.Map;

/**
 * 绑定微信小程序测试员
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class BindTesterResponse extends WxSmallResponse {
    private final String userstr;

    public BindTesterResponse(Map<String, Object> data) {
        super(data);
        this.userstr = (String)data.getOrDefault("userstr", "");
    }

    public String getUserstr() {
        return userstr;
    }
}
