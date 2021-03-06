package com.tuoshecx.server.wx.component.client.request;

/**
 * 得到体验二维码请求
 *
 * @author WangWei
 */
public class GetQrcodeRequest extends ComponentRequest {
    private final String path;

    public GetQrcodeRequest(String token){
        this(token, "");
    }

    public GetQrcodeRequest(String token, String path) {
        super(token);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
