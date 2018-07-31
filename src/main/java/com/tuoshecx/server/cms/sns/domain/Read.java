package com.tuoshecx.server.cms.sns.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

public class Read {
    private String id;
    private String openid;
    private String refId;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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
        Read read = (Read) o;
        return Objects.equals(id, read.id) &&
                Objects.equals(openid, read.openid) &&
                Objects.equals(refId, read.refId) &&
                Objects.equals(createTime, read.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openid, refId, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("openid", openid)
                .append("refId", refId)
                .append("createTime", createTime)
                .toString();
    }
}
