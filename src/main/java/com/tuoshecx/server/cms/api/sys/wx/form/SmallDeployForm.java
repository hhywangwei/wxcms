package com.tuoshecx.server.cms.api.sys.wx.form;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 发布小程序提交数据
 *
 * @author <a href="mailto:hhywangwei">WangWei</a>
 */
public class SmallDeployForm {
    @NotBlank
    @ApiModelProperty(value = "站点编号", required = true)
    private String siteId;
    @NotNull
    @ApiModelProperty(value = "发布程序模板编号", required = true)
    private Integer templateId;
    @NotNull
    @ApiModelProperty(value = "是否提交审核")
    private Boolean commit = Boolean.FALSE;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Boolean getCommit() {
        return commit;
    }

    public void setCommit(Boolean commit) {
        this.commit = commit;
    }
}
