package com.tuoshecx.server.cms.analysis.service;

import com.tuoshecx.server.cms.analysis.dao.VisitTrendDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class VisitTrendService {
    private final VisitTrendDao dao;

    @Autowired
    public VisitTrendService(VisitTrendDao dao) {
        this.dao = dao;
    }

    public void downloadDaily(String siteId, Date date){
        
    }
}
