package com.tuoshecx.server.cms.analysis.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 小程序概况趋势
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SummaryTrend {
    private String id;
    private String siteId;
    private String date;
    private Integer visitTotal;
    private Integer sharePv;
    private Integer shareUv;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getVisitTotal() {
        return visitTotal;
    }

    public void setVisitTotal(Integer visitTotal) {
        this.visitTotal = visitTotal;
    }

    public Integer getSharePv() {
        return sharePv;
    }

    public void setSharePv(Integer sharePv) {
        this.sharePv = sharePv;
    }

    public Integer getShareUv() {
        return shareUv;
    }

    public void setShareUv(Integer shareUv) {
        this.shareUv = shareUv;
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
        SummaryTrend that = (SummaryTrend) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(siteId, that.siteId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(visitTotal, that.visitTotal) &&
                Objects.equals(sharePv, that.sharePv) &&
                Objects.equals(shareUv, that.shareUv) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, siteId, date, visitTotal, sharePv, shareUv, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("siteId", siteId)
                .append("date", date)
                .append("visitTotal", visitTotal)
                .append("sharePv", sharePv)
                .append("shareUv", shareUv)
                .append("createTime", createTime)
                .toString();
    }
}
