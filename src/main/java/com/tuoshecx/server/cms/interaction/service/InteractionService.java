package com.tuoshecx.server.cms.interaction.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.interaction.dao.InteractionDao;
import com.tuoshecx.server.cms.interaction.domain.Interaction;
import com.tuoshecx.server.cms.site.domain.Organization;
import com.tuoshecx.server.cms.site.service.OrganizationService;
import com.tuoshecx.server.cms.sns.domain.Comment;
import com.tuoshecx.server.cms.sns.service.CommentService;
import com.tuoshecx.server.cms.sns.service.CounterService;
import com.tuoshecx.server.cms.sns.service.GoodService;
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
 * 政名互动业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class InteractionService {
    private final InteractionDao dao;
    private final UserService userService;
    private final OrganizationService organService;
    private final CommentService commentService;
    private final GoodService goodService;
    private final CounterService counterService;


    @Autowired
    public InteractionService(InteractionDao dao, UserService userService, OrganizationService organService,
                              CommentService commentService, GoodService goodService, CounterService counterService) {
        this.dao = dao;
        this.userService = userService;
        this.organService = organService;
        this.commentService = commentService;
        this.goodService = goodService;
        this.counterService = counterService;
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
        t.setSiteId(u.getSiteId());
        t.setNickname(u.getNickname());
        t.setHeadImg(u.getHeadImg());
        t.setTop(false);

        if(StringUtils.isNotBlank(t.getOrganId())){
            Organization o = organService.get(t.getOrganId());
            t.setOrganId(o.getId());
            t.setOrganName(o.getName());
        }

        dao.insert(t);
        counterService.newEmpty(t.getId(), refDetail(t.getTitle()));
        return get(t.getId());
    }

    private String refDetail(String title){
        return "[政民互动] " + title;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Interaction update(Interaction t){
        Interaction o = get(t.getId());
        if(!StringUtils.equals(t.getSiteId(), o.getSiteId())){
            throw new BaseException("互动不存在");
        }
        if(!StringUtils.equals(t.getUserId(), o.getUserId())){
            throw new BaseException("互动不存在");
        }

        if(o.getState() != Interaction.State.WAIT){
            throw new BaseException("正在处理不能修改");
        }

        Organization organ= organService.get(t.getOrganId());
        o.setOrganId(organ.getId());
        o.setOrganName(organ.getName());
        o.setAction(t.getAction());
        o.setTitle(t.getTitle());
        o.setContent(t.getContent());
        o.setImages(t.getImages());
        o.setOpen(t.getOpen());

        if(dao.update(o)){
            counterService.updateDetail(o.getId(), refDetail(o.getTitle()));
            return get(t.getId());
        }else{
            throw new BaseException("修改政民错误");
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
            dao.reply(id, replay);
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

    @Transactional(propagation = Propagation.REQUIRED)
    public Comment comment(String id, String userId, String[] images, String content){
        Interaction t = get(id);

        User u= userService.get(userId);
        if(!StringUtils.equals(t.getSiteId(), u.getSiteId())){
            throw new BaseException("不能评论");
        }

        String detail = "[政民互动] " + t.getTitle();
        return commentService.save(userId, t.getId(), detail, content, images);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean good(String id, String userId){
        return goodService.good(userId, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Interaction> query(String siteId, String title, int offset, int limit){
        return dao.find(siteId, title, offset, limit);
    }
}
