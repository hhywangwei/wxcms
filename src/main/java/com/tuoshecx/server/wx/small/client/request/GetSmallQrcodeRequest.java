package com.tuoshecx.server.wx.small.client.request;

/**
 * 得到二维码请求
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class GetSmallQrcodeRequest extends WxSmallRequest {
    private final String path;
    private final int width;
    private final boolean hyaline;

    public GetSmallQrcodeRequest(String token, String path, int width, boolean hyaline) {
        super(token);
        this.path = path;
        this.width = width;
        this.hyaline = hyaline;
    }

    public String getPath() {
        return path;
    }

    public int getWidth() {
        return width;
    }

    public boolean isHyaline() {
        return hyaline;
    }
}
