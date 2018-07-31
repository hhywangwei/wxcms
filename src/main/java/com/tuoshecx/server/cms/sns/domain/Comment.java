package com.tuoshecx.server.cms.sns.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 评论
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class Comment {
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
    @ApiModelProperty("评论记录编号")
    private String refId;
    @ApiModelProperty("评论记录描述")
    private String refDetail;
    @ApiModelProperty("发表评论内容")
    private String content;
    @ApiModelProperty("评论状态")
    private State state;
    @ApiModelProperty("上传图片")
    private String[] images;
    @ApiModelProperty("创建时间")
    private Date createTime;

    public enum State {
        WAIT, PASS, REFUSE
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

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getRefDetail() {
        return refDetail;
    }

    public void setRefDetail(String refDetail) {
        this.refDetail = refDetail;
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

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
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
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(siteId, comment.siteId) &&
                Objects.equals(userId, comment.userId) &&
                Objects.equals(nickname, comment.nickname) &&
                Objects.equals(headImg, comment.headImg) &&
                Objects.equals(refId, comment.refId) &&
                Objects.equals(refDetail, comment.refDetail) &&
                Objects.equals(content, comment.content) &&
                state == comment.state &&
                Arrays.equals(images, comment.images) &&
                Objects.equals(createTime, comment.createTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, siteId, userId, nickname, headImg, refId, refDetail, content, state, createTime);
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
                .append("refId", refId)
                .append("refDetail", refDetail)
                .append("content", content)
                .append("state", state)
                .append("images", images)
                .append("createTime", createTime)
                .toString();
    }
}
