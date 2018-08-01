package com.tuoshecx.server.cms.sns.service;

import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.sns.dao.CounterDao;
import com.tuoshecx.server.cms.sns.domain.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 文章等计数业务操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class CounterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CounterService.class);

    private final CounterDao dao;

    @Autowired
    public CounterService(CounterDao dao){
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void newEmpty(String refId, String refDetail){
        Counter t = new Counter();
        if(dao.has(refId)){
            return ;
        }

        t.setId(IdGenerators.uuid());
        t.setRefId(refId);
        t.setRefDetail(refDetail);
        t.setReadCount(0);
        t.setGoodCount(0);
        t.setCommentCount(0);
        t.setShareCount(0);

        dao.insert(t);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDetail(String refId, String refDetail){
        dao.updateDetail(refId, refDetail);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void incRead(String refId, int count){
        dao.incRead(refId, count);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void incGood(String refId, int count){
        dao.incGood(refId, count);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void incComment(String refId, int count){
        dao.incComment(refId, count);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void incShare(String refId, int count){
        dao.incShare(refId, count);
    }

    public Optional<Counter> getOptional(String id){
        try{
            return Optional.of(dao.findOne(id));
        }catch (DataAccessException e){
            LOGGER.error("Get {} counter fail, error is {}", id, e.getMessage());
            return Optional.empty();
        }
    }
}
