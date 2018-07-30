package com.tuoshecx.server.cms.interaction.domain;

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
    private Integer id;
    private String userId;
    private String nickname;
    private String headImage;
    private String mobile;
    private String title;
    private Integer type;
    private String content;
    private String[] images;
    private String replay;
    private Boolean open;
    private Integer readCount;
    private Integer goodCount;
    private Integer speakCount;
    private Boolean top;
    private Integer showOrder;
    private Date replayTime;
    private Date createTime;

    public enum  State {
        WAIT, HANDING, REPLAY, REFUSE
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getSpeakCount() {
        return speakCount;
    }

    public void setSpeakCount(Integer speakCount) {
        this.speakCount = speakCount;
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
                Objects.equals(userId, that.userId) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(headImage, that.headImage) &&
                Objects.equals(mobile, that.mobile) &&
                Objects.equals(title, that.title) &&
                Objects.equals(type, that.type) &&
                Objects.equals(content, that.content) &&
                Arrays.equals(images, that.images) &&
                Objects.equals(replay, that.replay) &&
                Objects.equals(open, that.open) &&
                Objects.equals(readCount, that.readCount) &&
                Objects.equals(goodCount, that.goodCount) &&
                Objects.equals(speakCount, that.speakCount) &&
                Objects.equals(top, that.top) &&
                Objects.equals(showOrder, that.showOrder) &&
                Objects.equals(replayTime, that.replayTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, userId, nickname, headImage, mobile, title, type, content, replay, open, readCount, goodCount, speakCount, top, showOrder, replayTime, createTime);
        result = 31 * result + Arrays.hashCode(images);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userId", userId)
                .append("nickname", nickname)
                .append("headImage", headImage)
                .append("mobile", mobile)
                .append("title", title)
                .append("type", type)
                .append("content", content)
                .append("images", images)
                .append("replay", replay)
                .append("open", open)
                .append("readCount", readCount)
                .append("goodCount", goodCount)
                .append("speakCount", speakCount)
                .append("top", top)
                .append("showOrder", showOrder)
                .append("replayTime", replayTime)
                .append("createTime", createTime)
                .toString();
    }
}
