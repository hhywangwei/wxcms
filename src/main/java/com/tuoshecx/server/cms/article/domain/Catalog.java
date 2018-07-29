package com.tuoshecx.server.cms.article.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 文章分类
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("文章分类")
public class Catalog {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "站点编号", required = true)
    private String siteId;
    @ApiModelProperty(value = "分类编号", required = true)
    private String name;
    @ApiModelProperty(value = "分类图标")
    private String icon;
    @ApiModelProperty(value = "显示排序")
    private Integer showOrder;
    @ApiModelProperty(value = "修改时间", required = true)
    private Date updateTime;
    @ApiModelProperty(value = "创建时间", required = true)
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        Catalog goodsCtg = (Catalog) o;
        return Objects.equals(id, goodsCtg.id) &&
                Objects.equals(siteId, goodsCtg.siteId) &&
                Objects.equals(name, goodsCtg.name) &&
                Objects.equals(icon, goodsCtg.icon) &&
                Objects.equals(showOrder, goodsCtg.showOrder) &&
                Objects.equals(updateTime, goodsCtg.updateTime) &&
                Objects.equals(createTime, goodsCtg.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, siteId, name, icon, showOrder, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("siteId", siteId)
                .append("name", name)
                .append("icon", icon)
                .append("showOrder", showOrder)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
