package com.tuoshecx.server.cms.site.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 站点托管微信小程序公众号Token信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("站点微信托管Token信息")
public class SiteWxToken {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "站点编号")
    private String siteId;
    @ApiModelProperty(value = "微信appid")
    private String appid;
    @ApiModelProperty(value = "微信访问AccessToken")
    private String accessToken;
    @ApiModelProperty(value = "刷新Token令牌")
    private String refreshToken;
    @ApiModelProperty(value = "token过期时间")
    private Date expiresTime;
    @ApiModelProperty(value = "更新Token时间")
    private Date updateTime;

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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(Date expiresTime) {
        this.expiresTime = expiresTime;
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
        if (o == null || getClass() != o.getClass()) return false;
        SiteWxToken that = (SiteWxToken) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(siteId, that.siteId) &&
                Objects.equals(appid, that.appid) &&
                Objects.equals(accessToken, that.accessToken) &&
                Objects.equals(refreshToken, that.refreshToken) &&
                Objects.equals(expiresTime, that.expiresTime) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, siteId, appid, accessToken, refreshToken, expiresTime, updateTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("siteId", siteId)
                .append("appid", appid)
                .append("accessToken", accessToken)
                .append("refreshToken", refreshToken)
                .append("expiresTime", expiresTime)
                .append("updateTime", updateTime)
                .toString();
    }
}
