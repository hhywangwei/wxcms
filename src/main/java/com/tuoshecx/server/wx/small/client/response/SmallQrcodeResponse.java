package com.tuoshecx.server.wx.small.client.response;

/**
 * 微信二维码输出对象
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SmallQrcodeResponse extends WxSmallResponse {
    private final byte[] image;

    public SmallQrcodeResponse(Integer code, String message, byte[] image) {
        super(code, message);
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }
}
