package com.tuoshecx.server.cms.interaction.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.interaction.dao.InteractionDao;
import com.tuoshecx.server.cms.interaction.domain.Interaction;
import com.tuoshecx.server.cms.site.domain.Organization;
import com.tuoshecx.server.cms.site.service.OrganizationService;
import com.tuoshecx.server.cms.sns.service.CommentService;
import com.tuoshecx.server.cms.user.domain.User;
import com.tuoshecx.server.cms.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 政名互动业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class InteractionService {
    private final InteractionDao dao;
    private final UserService userService;
    private final CommentService commentService;
    private final OrganizationService organService;

    @Autowired
    public InteractionService(InteractionDao dao, UserService userService,
                              CommentService commentService, OrganizationService organService) {
        this.dao = dao;
        this.userService = userService;
        this.commentService = commentService;
        this.organService = organService;
    }

    public Interaction get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("互动不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Interaction save(Interaction t){
        User u = userService.get(t.getUserId());

        t.setId(IdGenerators.uuid());
        t.setNickname(u.getNickname());
        t.setHeadImg(u.getHeadImg());

        if(StringUtils.isNotBlank(t.getOrganId())){
            Organization o = organService.get(t.getOrganId());
            t.setOrganId(o.getId());
            t.setOrganName(o.getName());
        }

        dao.insert(t);

        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Interaction updateOrgan(String id, String organId, String siteId){
        Interaction t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("互动不存在");
        }

        Organization o = organService.get(organId);
        if(dao.updateOrgan(id, o.getId(), o.getName())){
            return get(id);
        }else{
            throw new BaseException("修改组织错误");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Interaction handling(String id, String siteId){
        Interaction t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("互动不存在");
        }

        if(dao.updateState(id, Interaction.State.HANDING)){
            return get(id);
        }else{
            throw new BaseException("修改状态失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Interaction replay(String id, String replay, String siteId, String operator){
        Interaction t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("互动不存在");
        }

        if(dao.updateState(id, Interaction.State.REPLAY)){
            dao.replay(id, replay);
            return get(id);
            //TODO 操作日志
        }else{
            throw new BaseException("修改状态失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Interaction refuse(String id, String siteId, String operator){
        Interaction t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("互动不存在");
        }

        if(dao.updateState(id, Interaction.State.REFUSE)){
            return get(id);
        } else{
            throw new BaseException("修改状态失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Interaction top(String id, boolean isTop, String siteId){
        Interaction t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("互动不存在");
        }
        if(dao.top(id, isTop)){
            return get(id);
        }else {
            throw new BaseException("设置置顶失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Interaction showOrder(String id, Integer showOrder, String siteId){
        Interaction t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("互动不存在");
        }
        if(dao.showOrder(id, showOrder)){
            return get(id);
        }else {
            throw new BaseException("设置显示顺序失败");
        }
    }

}
