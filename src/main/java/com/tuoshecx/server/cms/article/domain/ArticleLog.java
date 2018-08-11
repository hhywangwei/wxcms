package com.tuoshecx.server.cms.article.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 文章操作日志
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ArticleLog {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "站点编号")
    private String siteId;
    @ApiModelProperty(value = "文章编号")
    private String articleId;
    @ApiModelProperty(value = "文章标题")
    private String title;
    @ApiModelProperty(value = "操作管理员编号")
    private String managerId;
    @ApiModelProperty(value = "操作管理员用户名")
    private String username;
    @ApiModelProperty(value = "动作")
    private String action;
    @ApiModelProperty(value = "创建时间")
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

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
        ArticleLog that = (ArticleLog) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(siteId, that.siteId) &&
                Objects.equals(articleId, that.articleId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(managerId, that.managerId) &&
                Objects.equals(username, that.username) &&
                Objects.equals(action, that.action) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, siteId, articleId, title, managerId, username, action, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("siteId", siteId)
                .append("articleId", articleId)
                .append("title", title)
                .append("managerId", managerId)
                .append("username", username)
                .append("action", action)
                .append("createTime", createTime)
                .toString();
    }
}
