package com.tuoshecx.server.cms.questionnaire.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 问卷调查信息实体类
 * @author LuJun
 */
@ApiModel("问卷调查信息")
public class QueInfo {

    @ApiModelProperty(value = "问卷调查信息编号", required = true)
    private String id;
    @ApiModelProperty(value = "问卷调查信息标题")
    private String title;
    @ApiModelProperty(value = "问卷调查信息内容")
    private String content;
    @ApiModelProperty(value = "问卷调查信息状态")
    private State state;
    @ApiModelProperty(value = "组织机构编号", required = true)
    private String organId;
    @ApiModelProperty(value = "true:删除,false:未删除", required = true)
    private Boolean isDelete;
    @ApiModelProperty(value = "创建用户", required = true)
    private String createUser;
    @ApiModelProperty(value = "修改用户", required = true)
    private String updateUser;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
    @ApiModelProperty(value = "修改时间", required = true)
    private Date updateTime;

    public enum State {
        WAIT, OPEN, CLOSE
    }

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
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
        if (!(o instanceof QueInfo)) return false;
        QueInfo queInfo = (QueInfo) o;
        return Objects.equals(id, queInfo.id) &&
                Objects.equals(title, queInfo.title) &&
                Objects.equals(content, queInfo.content) &&
                state == queInfo.state &&
                Objects.equals(organId, queInfo.organId) &&
                Objects.equals(isDelete, queInfo.isDelete) &&
                Objects.equals(createUser, queInfo.createUser) &&
                Objects.equals(updateUser, queInfo.updateUser) &&
                Objects.equals(createTime, queInfo.createTime) &&
                Objects.equals(updateTime, queInfo.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, state, organId, isDelete, createUser, updateUser, createTime, updateTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("content", content)
                .append("state", state)
                .append("organId", organId)
                .append("isDelete", isDelete)
                .append("createUser", createUser)
                .append("updateUser", updateUser)
                .append("createTime", createTime)
                .append("updateTime", updateTime)
                .toString();
    }
}
