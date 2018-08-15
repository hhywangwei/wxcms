package com.tuoshecx.server.cms.analysis.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 访问趋势
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class VisitTrend {
    private String id;
    private String siteId;
    private String dateStr;
    private String type;
    private Integer sessionCnt;
    private Integer visitPv;
    private Integer visitUv;
    private Integer visitUvNew;
    private Float stayTimeUv;
    private Float stayTimeSession;
    private Float visitDepth;
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

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSessionCnt() {
        return sessionCnt;
    }

    public void setSessionCnt(Integer sessionCnt) {
        this.sessionCnt = sessionCnt;
    }

    public Integer getVisitPv() {
        return visitPv;
    }

    public void setVisitPv(Integer visitPv) {
        this.visitPv = visitPv;
    }

    public Integer getVisitUv() {
        return visitUv;
    }

    public void setVisitUv(Integer visitUv) {
        this.visitUv = visitUv;
    }

    public Integer getVisitUvNew() {
        return visitUvNew;
    }

    public void setVisitUvNew(Integer visitUvNew) {
        this.visitUvNew = visitUvNew;
    }

    public Float getStayTimeUv() {
        return stayTimeUv;
    }

    public void setStayTimeUv(Float stayTimeUv) {
        this.stayTimeUv = stayTimeUv;
    }

    public Float getStayTimeSession() {
        return stayTimeSession;
    }

    public void setStayTimeSession(Float stayTimeSession) {
        this.stayTimeSession = stayTimeSession;
    }

    public Float getVisitDepth() {
        return visitDepth;
    }

    public void setVisitDepth(Float visitDepth) {
        this.visitDepth = visitDepth;
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
        VisitTrend that = (VisitTrend) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(siteId, that.siteId) &&
                Objects.equals(dateStr, that.dateStr) &&
                Objects.equals(type, that.type) &&
                Objects.equals(sessionCnt, that.sessionCnt) &&
                Objects.equals(visitPv, that.visitPv) &&
                Objects.equals(visitUv, that.visitUv) &&
                Objects.equals(visitUvNew, that.visitUvNew) &&
                Objects.equals(stayTimeUv, that.stayTimeUv) &&
                Objects.equals(stayTimeSession, that.stayTimeSession) &&
                Objects.equals(visitDepth, that.visitDepth) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, siteId, dateStr, type, sessionCnt, visitPv, visitUv, visitUvNew, stayTimeUv, stayTimeSession, visitDepth, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("siteId", siteId)
                .append("dateStr", dateStr)
                .append("type", type)
                .append("sessionCnt", sessionCnt)
                .append("visitPv", visitPv)
                .append("visitUv", visitUv)
                .append("visitUvNew", visitUvNew)
                .append("stayTimeUv", stayTimeUv)
                .append("stayTimeSession", stayTimeSession)
                .append("visitDepth", visitDepth)
                .append("createTime", createTime)
                .toString();
    }
}
