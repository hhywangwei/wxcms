package com.tuoshecx.server.cms.sns.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 *  点赞
 *
 * @author <a href="mailto:hhywangwei@mgail.com">WangWei</a>
 */
public class Good {
    private String id;
    private String userId;
    private String nickname;
    private String headImg;
    private String refId;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        Good good = (Good) o;
        return Objects.equals(id, good.id) &&
                Objects.equals(userId, good.userId) &&
                Objects.equals(nickname, good.nickname) &&
                Objects.equals(headImg, good.headImg) &&
                Objects.equals(refId, good.refId) &&
                Objects.equals(createTime, good.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, nickname, headImg, refId, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userId", userId)
                .append("nickname", nickname)
                .append("headImg", headImg)
                .append("refId", refId)
                .append("createTime", createTime)
                .toString();
    }
}
