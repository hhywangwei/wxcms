package com.tuoshecx.server.cms.api.sys.site.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 重置站点超级管理员密码
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("重置站点超级管理员密码")
public class SiteResetPasswordForm {
    @NotBlank
    @ApiModelProperty(value = "用户编号", required = true)
    private String siteId;
    @NotBlank
    @Size(min = 8, max = 20)
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
