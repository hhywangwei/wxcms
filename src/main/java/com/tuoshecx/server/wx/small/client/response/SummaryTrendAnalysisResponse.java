package com.tuoshecx.server.wx.small.client.response;

import java.util.List;
import java.util.Map;

/**
 * 微信小程序概况趋势输出
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SummaryTrendAnalysisResponse extends WxSmallResponse {
    private final String date;
    private final Integer visitTotal;
    private final Integer sharePv;
    private final Integer shareUv;

    @SuppressWarnings("unchecked")
    public SummaryTrendAnalysisResponse(Map<String, Object> data) {
        super(data);

        List<Map<String, Object>> list = (List<Map<String, Object>>)data.get("list");
        if(list == null || list.isEmpty()){
            date = "";
            visitTotal = 0;
            sharePv = 0;
            shareUv = 0;
        }else{
            Map<String, Object> map = list.get(0);
            date = (String)map.getOrDefault("ref_date", "");
            visitTotal = (Integer)map.getOrDefault("visit_total", 0);
            sharePv = (Integer)map.getOrDefault("share_pv", 0);
            shareUv = (Integer)map.getOrDefault("share_uv", 0);
        }
    }

    public String getDate() {
        return date;
    }

    public Integer getVisitTotal() {
        return visitTotal;
    }

    public Integer getSharePv() {
        return sharePv;
    }

    public Integer getShareUv() {
        return shareUv;
    }
}
