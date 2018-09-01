package com.tuoshecx.server.cms.questionnaire.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 问卷调查项目实体类
 * @author LuJun
 */
@ApiModel("问卷调查项目")
public class QueProject {

    @ApiModelProperty(value = "问卷项目编号", required = true)
    private String id;
    @ApiModelProperty(value = "问卷项目标题")
    private String title;
    @ApiModelProperty(value = "问卷项目类容")
    private String content;
    @ApiModelProperty(value = "问卷调查表编号", required = true)
    private String queInfoId;
    @ApiModelProperty(value = "项目类型", required = true)
    private String type;
    @ApiModelProperty(value = "创建用户", required = true)
    private String createUser;
    @ApiModelProperty(value = "修改用户", required = true)
    private String updateUser;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
    @ApiModelProperty(value = "修改时间", required = true)
    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQueInfoId() {
        return queInfoId;
    }

    public void setQueInfoId(String queInfoId) {
        this.queInfoId = queInfoId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueProject)) return false;
        QueProject that = (QueProject) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                Objects.equals(queInfoId, that.queInfoId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(updateUser, that.updateUser) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, queInfoId, type, createUser, updateUser, createTime, updateTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("content", content)
                .append("queInfoId", queInfoId)
                .append("type", type)
                .append("createUser", createUser)
                .append("updateUser", updateUser)
                .append("createTime", createTime)
                .append("updateTime", updateTime)
                .toString();
    }
}