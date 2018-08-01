package com.tuoshecx.server.cms.sns.service;

import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.sns.dao.ReadDao;
import com.tuoshecx.server.cms.sns.domain.Read;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadService.class);
    private final ReadDao dao;
    private final CounterService counterService;

    @Autowired
    public ReadService(ReadDao dao, CounterService counterService) {
        this.dao = dao;
        this.counterService = counterService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean read(String openid, String refId){
        if(has(openid, refId)){
            return false;
        }

        try{
            Read t = new Read();
            t.setId(IdGenerators.uuid());
            t.setOpenid(openid);
            t.setRefId(refId);
            dao.insert(t);

            counterService.incRead(refId, 1);

            return true;
        }catch (DataAccessException e){
            LOGGER.error("Save read fail, error is {}", e.getMessage());
            return false;
        }
    }

    public boolean has(String openid, String refId){
        return dao.has(openid, refId);
    }
}
