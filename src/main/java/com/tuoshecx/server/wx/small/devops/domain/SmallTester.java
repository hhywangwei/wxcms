package com.tuoshecx.server.wx.small.devops.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 微信小程序测试号
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SmallTester {
    private String id;
    private String siteId;
    private String appid;
    private String wechatid;
    private String userstr;
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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getWechatid() {
        return wechatid;
    }

    public void setWechatid(String wechatid) {
        this.wechatid = wechatid;
    }

    public String getUserstr() {
        return userstr;
    }

    public void setUserstr(String userstr) {
        this.userstr = userstr;
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
        SmallTester that = (SmallTester) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(siteId, that.siteId) &&
                Objects.equals(appid, that.appid) &&
                Objects.equals(wechatid, that.wechatid) &&
                Objects.equals(userstr, that.userstr) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, siteId, appid, wechatid, userstr, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("siteId", siteId)
                .append("appid", appid)
                .append("wechatid", wechatid)
                .append("userstr", userstr)
                .append("createTime", createTime)
                .toString();
    }
}
