package com.tuoshecx.server.cms.api.manage.wx.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 绑定小程序测试
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class BindTesterForm {
    @NotBlank
    @ApiModelProperty("微信编号")
    private String wechatid;

    public String getWechatid() {
        return wechatid;
    }

    public void setWechatid(String wechatid) {
        this.wechatid = wechatid;
    }
}
