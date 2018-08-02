package com.tuoshecx.server.wx.small.client.request;

/**
 * 绑定微信测试者请求
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class BindTesterRequest extends WxSmallRequest {
    private final String wechatid;

    public BindTesterRequest(String token, String wechatid) {
        super(token);
        this.wechatid = wechatid;
    }

    public String getWechatid() {
        return wechatid;
    }
}
