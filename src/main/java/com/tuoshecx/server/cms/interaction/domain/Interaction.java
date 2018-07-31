package com.tuoshecx.server.cms.interaction.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 政名互动
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class Interaction {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("站点编号")
    private String siteId;
    @ApiModelProperty("用户编号")
    private String userId;
    @ApiModelProperty("用户昵称")
    private String nickname;
    @ApiModelProperty("用户头像")
    private String headImg;
    @ApiModelProperty("互动机构编号")
    private String organId;
    @ApiModelProperty("互动机构名称")
    private String organName;
    @ApiModelProperty("互动标题")
    private String title;
    @ApiModelProperty("互动类型")
    private Action action;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("互动图片")
    private String[] images;
    @ApiModelProperty("回复")
    private String replay;
    @ApiModelProperty("是否公开互动")
    private Boolean open;
    @ApiModelProperty("阅读人数")
    private Integer readCount;
    @ApiModelProperty("点赞人数")
    private Integer goodCount;
    @ApiModelProperty("评论人数")
    private Integer commentCount;
    @ApiModelProperty("是否置顶")
    private Boolean top;
    @ApiModelProperty("显示顺序")
    private Integer showOrder;
    @ApiModelProperty("回复时间")
    private Date replayTime;
    @ApiModelProperty("创建时间")
    private Date createTime;

    public enum  State {
        WAIT, HANDING, REPLAY, REFUSE
    }

    public enum Action {
        CONSULT, COMPLAINT, SUGGEST
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public Date getReplayTime() {
        return replayTime;
    }

    public void setReplayTime(Date replayTime) {
        this.replayTime = replayTime;
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
        Interaction that = (Interaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(siteId, that.siteId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(headImg, that.headImg) &&
                Objects.equals(organId, that.organId) &&
                Objects.equals(organName, that.organName) &&
                Objects.equals(title, that.title) &&
                action == that.action &&
                Objects.equals(content, that.content) &&
                Arrays.equals(images, that.images) &&
                Objects.equals(replay, that.replay) &&
                Objects.equals(open, that.open) &&
                Objects.equals(readCount, that.readCount) &&
                Objects.equals(goodCount, that.goodCount) &&
                Objects.equals(commentCount, that.commentCount) &&
                Objects.equals(top, that.top) &&
                Objects.equals(showOrder, that.showOrder) &&
                Objects.equals(replayTime, that.replayTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, siteId, userId, nickname, headImg, organId, organName, title, action, content, replay, open, readCount, goodCount, commentCount, top, showOrder, replayTime, createTime);
        result = 31 * result + Arrays.hashCode(images);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("siteId", siteId)
                .append("userId", userId)
                .append("nickname", nickname)
                .append("headImg", headImg)
                .append("organId", organId)
                .append("organName", organName)
                .append("title", title)
                .append("action", action)
                .append("content", content)
                .append("images", images)
                .append("replay", replay)
                .append("open", open)
                .append("readCount", readCount)
                .append("goodCount", goodCount)
                .append("commentCount", commentCount)
                .append("top", top)
                .append("showOrder", showOrder)
                .append("replayTime", replayTime)
                .append("createTime", createTime)
                .toString();
    }
}
