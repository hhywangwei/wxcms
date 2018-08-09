package com.tuoshecx.server.wx.small.client.response;

import java.util.List;
import java.util.Map;

public class VisitTrendAnalysisResponse extends WxSmallResponse {
    private final String date;
    private final Integer sessionCnt;
    private final Integer visitPv;
    private final Integer visitUv;
    private final Integer visitUvNew;
    private final Integer stayTimeSession;
    private final Float visitDepth;

    @SuppressWarnings("unchecked")
    public VisitTrendAnalysisResponse(Map<String, Object> data) {
        super(data);

        List<Map<String, Object>> list = (List<Map<String, Object>>)data.get("list");
        if(list == null || list.isEmpty()){
            date = "";
            sessionCnt = 0;
            visitPv = 0;
            visitUv = 0;
            visitUvNew = 0;
            stayTimeSession = 0;
            visitDepth = 0F;
        }else{
            Map<String, Object> map = list.get(0);
            date = (String)map.getOrDefault("ref_date", "");
            sessionCnt = (Integer)map.getOrDefault("session_cnt", 0);
            visitPv = (Integer)map.getOrDefault("visit_pv", 0);
            visitUv = (Integer)map.getOrDefault("visit_uv", 0);
            visitUvNew = (Integer)map.getOrDefault("visit_uv_new", 0);
            stayTimeSession = (Integer)map.getOrDefault("stay_time_session", 0);
            visitDepth = (Float)map.getOrDefault("visit_depth", 0F);
        }
    }

    public String getDate() {
        return date;
    }

    public Integer getSessionCnt() {
        return sessionCnt;
    }

    public Integer getVisitPv() {
        return visitPv;
    }

    public Integer getVisitUv() {
        return visitUv;
    }

    public Integer getVisitUvNew() {
        return visitUvNew;
    }

    public Integer getStayTimeSession() {
        return stayTimeSession;
    }

    public Float getVisitDepth() {
        return visitDepth;
    }
}
