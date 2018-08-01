package com.tuoshecx.server.cms.sns.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.sns.dao.CommentDao;
import com.tuoshecx.server.cms.sns.domain.Comment;
import com.tuoshecx.server.cms.user.domain.User;
import com.tuoshecx.server.cms.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评论业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class CommentService {
    private final CommentDao dao;
    private final UserService userService;
    private final CounterService counterService;

    @Autowired
    public CommentService(CommentDao dao, UserService userService, CounterService counterService) {
        this.dao = dao;
        this.userService = userService;
        this.counterService = counterService;
    }

    public Comment get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("评论不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Comment save(String userId, String refId, String refDetail,
                        String content, String[] images){

        User u = userService.get(userId);

        Comment t = new Comment();
        t.setId(IdGenerators.uuid());
        t.setSiteId(u.getSiteId());
        t.setUserId(u.getId());
        t.setNickname(u.getNickname());
        t.setHeadImg(u.getHeadImg());
        t.setRefId(refId);
        t.setRefDetail(refDetail);
        t.setContent(content);
        t.setImages(images);

        dao.insert(t);
        counterService.incComment(t.getRefId(), 1);

        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Comment pass(String id, String siteId){
        Comment t = get(id);

        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("评论不存在");
        }
        if(t.getState() == Comment.State.PASS){
            throw new BaseException("已经审核通过");
        }

        if(dao.updateState(id, Comment.State.PASS)){
            return get(id);
        }else{
            throw new BaseException("审核通过失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Comment refuse(String id, String siteId){
        Comment t = get(id);

        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("评论不存在");
        }
        if(t.getState() == Comment.State.REFUSE){
            throw new BaseException("已经审核通过");
        }

        if(dao.updateState(id, Comment.State.REFUSE)){
            return get(id);
        }else{
            throw new BaseException("审核拒绝失败");
        }
    }

    public List<Comment> query(String refId, int offset, int limit){
        return dao.find(refId, offset, limit);
    }

    public Long count(String nickname, String refDetail, Comment.State state){
        return dao.count(nickname, refDetail, state);
    }

    public List<Comment> query(String nickname, String refDetail, Comment.State state, int offset, int limit){
        return dao.find(nickname, refDetail, state, offset, limit);
    }
}
