package com.tuoshecx.server.cms.api.manage.organization.form;

import com.tuoshecx.server.cms.site.domain.Organization;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OrganizationSaveForm {
    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(value = "组织机构名称", required = true)
    private String name;
    @Size(max = 200)
    @ApiModelProperty(value = "组织机构图标")
    private String icon;
    @NotNull
    @ApiModelProperty(value = "显示排名")
    private Integer showOrder = 9999;
    @Size(max = 200)
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("上级组织机构编号")
    private String parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Organization toDomain(String siteId){
        Organization t = new Organization();

        t.setSiteId(siteId);
        t.setName(name);
        t.setIcon(icon);
        t.setShowOrder(showOrder);
        t.setRemark(remark);
        t.setParentId(parentId);

        return t;
    }
}
