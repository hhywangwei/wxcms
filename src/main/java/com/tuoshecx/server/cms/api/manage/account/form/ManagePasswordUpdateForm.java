package com.tuoshecx.server.cms.api.manage.account.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 站点管理员修改密码
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ManagePasswordUpdateForm {
    @NotBlank
    private String password;
    @NotBlank
    @Size(min = 8, max = 20)
    private String newPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
