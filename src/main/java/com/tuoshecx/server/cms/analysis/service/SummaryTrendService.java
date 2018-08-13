package com.tuoshecx.server.cms.analysis.service;

import com.tuoshecx.server.cms.analysis.dao.SummaryTrendDao;
import com.tuoshecx.server.cms.analysis.domain.SummaryTrend;
import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.site.service.SiteWxService;
import com.tuoshecx.server.wx.small.client.WxSmallClientService;
import com.tuoshecx.server.wx.small.client.response.SummaryTrendAnalysisResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SummaryTrendService {
    private final SummaryTrendDao dao;
    private final WxSmallClientService clientService;
    private final SiteWxService wxService;

    @Autowired
    public SummaryTrendService(SummaryTrendDao dao, WxSmallClientService clientService,
                               SiteWxService wxService) {
        this.dao = dao;
        this.clientService = clientService;
        this.wxService = wxService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void download(String siteId, Date date){
        Optional<String> optional = wxService.getAppid(siteId);
        optional.ifPresent(e -> {
            SummaryTrendAnalysisResponse response = clientService.summaryTrendAnalysis(e, date);
            if(response.isOk()){
                SummaryTrend t = new SummaryTrend();

                t.setId(IdGenerators.uuid());
                t.setSiteId(siteId);
                t.setDate(response.getDate());
                t.setVisitTotal(response.getVisitTotal());
                t.setSharePv(response.getSharePv());
                t.setShareUv(response.getShareUv());

                dao.insert(t);
            }
        });
    }

    public List<SummaryTrend> find(String siteId, String fromDate, String toDate){
        return dao.find(siteId, fromDate, toDate);
    }
}
