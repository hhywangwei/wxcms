package com.tuoshecx.server.cms.api.client.account.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 绑定管理员
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class BindManagerForm {
    @NotBlank
    @ApiModelProperty(value = "管理员编号", required = true)
    private String managerId;

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
}
