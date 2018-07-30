package com.tuoshecx.server.cms.site.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 组织机构
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("组织机构")
public class Organization {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("站点编号")
    private String siteId;
    @ApiModelProperty("组织机构名称")
    private String name;
    @ApiModelProperty("组织机构图标")
    private String icon;
    @ApiModelProperty("显示排名")
    private Integer showOrder;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("上级组织机构编号")
    private String parentId;
    @ApiModelProperty("创建时间")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(siteId, that.siteId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(icon, that.icon) &&
                Objects.equals(showOrder, that.showOrder) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(parentId, that.parentId) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, siteId, name, icon, showOrder, remark, parentId, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("siteId", siteId)
                .append("name", name)
                .append("icon", icon)
                .append("showOrder", showOrder)
                .append("remark", remark)
                .append("parentId", parentId)
                .append("createTime", createTime)
                .toString();
    }
}
