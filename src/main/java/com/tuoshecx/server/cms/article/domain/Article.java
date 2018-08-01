package com.tuoshecx.server.cms.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 文章
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class Article {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("站点编号")
    private String siteId;
    @ApiModelProperty("频道编号")
    private String channelId;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("短标题")
    private String shortTitle;
    @ApiModelProperty("子标题 ")
    private String subTitle;
    @ApiModelProperty("作者")
    private String author;
    @ApiModelProperty("新闻来源")
    private String origin;
    @ApiModelProperty("关键字")
    private String[] keywords;
    @ApiModelProperty("文章分类")
    private String[] catalogs;
    @ApiModelProperty("文章标签")
    private String tag;
    @ApiModelProperty("摘要")
    private String summary;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("图片")
    private String image;
    @ApiModelProperty("显示模板")
    private String template;
    @ApiModelProperty("文章状态")
    private State state;
    @ApiModelProperty("文章排序")
    private Integer showOrder;
    @ApiModelProperty(value = "是否可以评论", hidden = true)
    @JsonIgnore
    private Boolean comment;
    @ApiModelProperty("是否顶置")
    private Boolean top;
    @ApiModelProperty("修改时间")
    private Date updateTime;
    @ApiModelProperty("创建时间")
    private Date createTime;

    public enum State {
        REEDIT ,RELEASE, CLOSE
    }

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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public String[] getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(String[] catalogs) {
        this.catalogs = catalogs;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getComment() {
        return comment;
    }

    public void setComment(Boolean comment) {
        this.comment = comment;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
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

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
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
        Article article = (Article) o;
        return Objects.equals(id, article.id) &&
                Objects.equals(siteId, article.siteId) &&
                Objects.equals(channelId, article.channelId) &&
                Objects.equals(title, article.title) &&
                Objects.equals(shortTitle, article.shortTitle) &&
                Objects.equals(subTitle, article.subTitle) &&
                Objects.equals(author, article.author) &&
                Objects.equals(origin, article.origin) &&
                Arrays.equals(keywords, article.keywords) &&
                Arrays.equals(catalogs, article.catalogs) &&
                Objects.equals(tag, article.tag) &&
                Objects.equals(summary, article.summary) &&
                Objects.equals(content, article.content) &&
                Objects.equals(image, article.image) &&
                Objects.equals(template, article.template) &&
                state == article.state &&
                Objects.equals(showOrder, article.showOrder) &&
                Objects.equals(comment, article.comment) &&
                Objects.equals(top, article.top) &&
                Objects.equals(updateTime, article.updateTime) &&
                Objects.equals(createTime, article.createTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, siteId, channelId, title, shortTitle, subTitle, author, origin, tag, summary,
                content, image, template, state, showOrder, comment, top, updateTime, createTime);
        result = 31 * result + Arrays.hashCode(keywords);
        result = 31 * result + Arrays.hashCode(catalogs);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("siteId", siteId)
                .append("channelId", channelId)
                .append("title", title)
                .append("shortTitle", shortTitle)
                .append("subTitle", subTitle)
                .append("author", author)
                .append("origin", origin)
                .append("keywords", keywords)
                .append("catalogs", catalogs)
                .append("tag", tag)
                .append("summary", summary)
                .append("content", content)
                .append("image", image)
                .append("template", template)
                .append("state", state)
                .append("showOrder", showOrder)
                .append("comment", comment)
                .append("top", top)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
