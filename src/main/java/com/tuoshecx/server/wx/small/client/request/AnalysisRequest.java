package com.tuoshecx.server.wx.small.client.request;

/**
 * 微信小程序概况趋势请求
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class AnalysisRequest extends WxSmallRequest {
    private final String beginDate;
    private final String endDate;

    public AnalysisRequest(String token, String beginDate, String endDate) {
        super(token);
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
