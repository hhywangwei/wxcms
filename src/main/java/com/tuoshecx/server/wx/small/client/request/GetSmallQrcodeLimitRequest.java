package com.tuoshecx.server.wx.small.client.request;

/**
 * 生成场景小程序二维码
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class GetSmallQrcodeLimitRequest extends GetSmallQrcodeRequest {
    private final String scene;

    public GetSmallQrcodeLimitRequest(String token, String path, String scene, int width, boolean hyaline) {
        super(token, path, width, hyaline);
        this.scene = scene;
    }

    public String getScene() {
        return scene;
    }
}
