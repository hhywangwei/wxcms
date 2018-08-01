package com.tuoshecx.server.cms.sns.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 文章等计数，如阅读数等
 *
 * @author <a href="mailto:hhywangwei@gmail.com>WangWei</a>
 */
public class Counter {
    private String id;
    private String refId;
    private String refDetail;
    private Integer readCount;
    private Integer goodCount;
    private Integer commentCount;
    private Integer shareCount;
    private Date updateTime;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
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
        Counter counter = (Counter) o;
        return Objects.equals(id, counter.id) &&
                Objects.equals(refId, counter.refId) &&
                Objects.equals(refDetail, counter.refDetail) &&
                Objects.equals(readCount, counter.readCount) &&
                Objects.equals(goodCount, counter.goodCount) &&
                Objects.equals(commentCount, counter.commentCount) &&
                Objects.equals(shareCount, counter.shareCount) &&
                Objects.equals(updateTime, counter.updateTime) &&
                Objects.equals(createTime, counter.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, refId, refDetail, readCount, goodCount, commentCount, shareCount, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("refId", refId)
                .append("refDetail", refDetail)
                .append("readCount", readCount)
                .append("goodCount", goodCount)
                .append("commentCount", commentCount)
                .append("shareCount", shareCount)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
