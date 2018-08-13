package com.tuoshecx.server.cms.analysis.schedule;

import com.tuoshecx.server.cms.analysis.service.SummaryTrendService;
import com.tuoshecx.server.cms.common.utils.DateUtils;
import com.tuoshecx.server.cms.site.domain.Site;
import com.tuoshecx.server.cms.site.service.SiteService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class AnalysisSchedule {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisSchedule.class);

    private final SiteService service;
    private final SummaryTrendService summaryTrendService;

    @Autowired
    public AnalysisSchedule(SiteService service, SummaryTrendService summaryTrendService) {
        this.service = service;
        this.summaryTrendService = summaryTrendService;
    }

    @Scheduled(cron = "5 2 * * * ?")
    public void downloadAnalysis(){
        List<Site> sites = service.query(StringUtils.EMPTY, StringUtils.EMPTY, 0, 200);
        Date date = DateUtils.plusDays(new Date(), -1);
        for(Site site: sites){
            try{
                summaryTrendService.download(site.getId(), date);
                LOGGER.debug("Download {} wx small analysis success ...");
            }catch (Exception e){
                LOGGER.error("Download wx small analysis fail, site {} error is {}", site.getId(), e.getMessage());
            }
        }
    }
}
