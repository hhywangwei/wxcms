package com.tuoshecx.server.cms.sns.service;

import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.sns.dao.GoodDao;
import com.tuoshecx.server.cms.sns.domain.Good;
import com.tuoshecx.server.cms.user.domain.User;
import com.tuoshecx.server.cms.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GoodService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodService.class);

    private final GoodDao dao;
    private final UserService userService;
    private final CounterService counterService;

    @Autowired
    public GoodService(GoodDao dao, UserService userService, CounterService counterService) {
        this.dao = dao;
        this.userService = userService;
        this.counterService = counterService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean good(String userId, String refId){
        if(has(userId, refId)){
            return false;
        }

        User u = userService.get(userId);
        try{
            Good t = new Good();
            t.setId(IdGenerators.uuid());
            t.setUserId(u.getId());
            t.setNickname(u.getNickname());
            t.setHeadImg(u.getHeadImg());
            t.setRefId(refId);
            dao.insert(t);

            counterService.incGood(refId, 1);

            return true;
        }catch (DataAccessException e){
            LOGGER.error("Good fail, error is {}", e.getMessage());
            return false;
        }
    }

    public boolean has(String openid, String refId){
        return dao.has(openid, refId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean unGood(String userId, String refId){
        boolean ok = dao.delete(userId, refId);
        if(ok){
            counterService.incGood(refId, -1);
        }
        return ok;
    }
}
