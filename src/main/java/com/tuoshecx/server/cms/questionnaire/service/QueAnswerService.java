package com.tuoshecx.server.cms.questionnaire.service;

import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.questionnaire.dao.QueAnswerDao;
import com.tuoshecx.server.cms.questionnaire.dao.QueInfoDao;
import com.tuoshecx.server.cms.questionnaire.domain.QueAnswer;
import com.tuoshecx.server.cms.site.domain.Manager;
import com.tuoshecx.server.cms.site.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  问卷答题服务
 * @author LuJun
 */
@Service
@Transactional(readOnly = true)
public class QueAnswerService {

    private final QueAnswerDao queAnswerDao;

    @Autowired
    public QueAnswerService(QueAnswerDao queAnswerDao, ManagerService managerService) {
        this.queAnswerDao = queAnswerDao;

    }

    /**
     * 新增问卷答题
     * @param q
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public QueAnswer save(QueAnswer q){
        q.setId(IdGenerators.uuid());
        queAnswerDao.insert(q);
        return queAnswerDao.findOne(q.getId());
    }

    /**
     * 删除问卷答题
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return queAnswerDao.delete(id);
    }

    /**
     * 得到问卷答题
     * @param id
     * @return
     */
    public QueAnswer get(String id){
        return queAnswerDao.findOne(id);
    }


    /**
     * 通过userId和问卷信息id分页查询问卷答题
     * @param userId
     * @param queInfoId
     * @param offset
     * @param limit
     * @return
     */
    public List<QueAnswer> queryAnswer(String userId,String queInfoId,int offset, int limit){
        return queAnswerDao.findList(userId, queInfoId, offset, limit);
    }

    /**
     * 通过userId和问卷信息id统计答题条数
     * @param userId
     * @param queInfoId
     * @return
     */
    public Long count(String userId,String queInfoId){
        return queAnswerDao.count(userId, queInfoId);
    }

}
