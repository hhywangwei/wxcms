package com.tuoshecx.server.cms.questionnaire.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.questionnaire.dao.QueAnswerDao;
import com.tuoshecx.server.cms.questionnaire.dao.QueInfoDao;
import com.tuoshecx.server.cms.questionnaire.domain.QueAnswer;
import com.tuoshecx.server.cms.site.domain.Manager;
import com.tuoshecx.server.cms.site.service.ManagerService;
import com.tuoshecx.server.cms.user.domain.User;
import com.tuoshecx.server.cms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    private final UserService userService;

    @Autowired
    public QueAnswerService(QueAnswerDao queAnswerDao, UserService userService) {
        this.queAnswerDao = queAnswerDao;
        this.userService = userService;
    }

    /**
     * 新增问卷答题
     * @param q
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public QueAnswer save(QueAnswer q,String userId){

        try {
            if (isAnswer(userId,q.getQueInfoId())){
                throw new BaseException("答案不能重复提交");
            }else{
                User user = userService.get(userId);
                q.setId(IdGenerators.uuid());
                q.setUserId(userId);
                q.setHeadImg(user.getHeadImg());
                q.setNickName(user.getNickname());
                queAnswerDao.insert(q);
                return queAnswerDao.findOne(q.getId());
            }
        }catch (DataAccessException e){
            throw new BaseException("答案提交失败"+e.getMessage());
        }
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
     * @param organId
     * @param offset
     * @param limit
     * @return
     */
    public List<QueAnswer> queryAnswer(String userId,String organId,String queInfoId,int offset, int limit){
        return queAnswerDao.findList(userId, organId,queInfoId ,offset, limit);
    }

    /**
     * 是否已经答应
     * @param userId
     * @param queInfoId
     * @return
     */
    public boolean isAnswer(String userId,String queInfoId){
        return queAnswerDao.isAnswer(userId, queInfoId);
    }

    /**
     * 通过userId和问卷信息id统计答题条数
     * @param userId
     * @param organId
     * @return
     */
    public Long count(String userId,String organId,String queInfoId){
        return queAnswerDao.count(userId, organId,queInfoId);
    }

}
