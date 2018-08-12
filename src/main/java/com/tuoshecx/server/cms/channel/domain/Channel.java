package com.tuoshecx.server.cms.channel.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 站点频道
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("频道")
public class Channel {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("上级频道编号")
    private String parentId;
    @ApiModelProperty("站点编号")
    private String siteId;
    @ApiModelProperty("频道名称")
    private String name;
    @ApiModelProperty(value = "频道图标")
    private String icon;
    @ApiModelProperty(value = "频道模板")
    private String template;
    @ApiModelProperty(value = "频道路径")
    private String path;
    @ApiModelProperty(value = "频道全路径")
    private String pathFull;
    @ApiModelProperty(value = "频道状态")
    private State state;
    @ApiModelProperty(value = "显示顺序，按照正序显示")
    private Integer showOrder;
    @ApiModelProperty(value = "频道是否隐藏")
    private Boolean hide;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public enum State {
        WAIT, OPEN, CLOSE
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathFull() {
        return pathFull;
    }

    public void setPathFull(String pathFull) {
        this.pathFull = pathFull;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
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
        Channel channel = (Channel) o;
        return Objects.equals(id, channel.id) &&
                Objects.equals(parentId, channel.parentId) &&
                Objects.equals(siteId, channel.siteId) &&
                Objects.equals(name, channel.name) &&
                Objects.equals(icon, channel.icon) &&
                Objects.equals(template, channel.template) &&
                Objects.equals(path, channel.path) &&
                Objects.equals(pathFull, channel.pathFull) &&
                state == channel.state &&
                Objects.equals(showOrder, channel.showOrder) &&
                Objects.equals(hide, channel.hide) &&
                Objects.equals(updateTime, channel.updateTime) &&
                Objects.equals(createTime, channel.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, siteId, name, icon, template, path, pathFull, state, showOrder, hide, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("parentId", parentId)
                .append("siteId", siteId)
                .append("name", name)
                .append("icon", icon)
                .append("template", template)
                .append("path", path)
                .append("pathFull", pathFull)
                .append("state", state)
                .append("showOrder", showOrder)
                .append("hide", hide)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
