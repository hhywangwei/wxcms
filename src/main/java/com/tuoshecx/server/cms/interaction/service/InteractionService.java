package com.tuoshecx.server.cms.interaction.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.check.SecCheckProducer;
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
    private final InteractionMessageService messageService;
    private final SecCheckProducer producer;


    @Autowired
    public InteractionService(InteractionDao dao, UserService userService, OrganizationService organService,
                              CommentService commentService, GoodService goodService, CounterService counterService,
                              InteractionMessageService messageService, SecCheckProducer producer) {
        this.dao = dao;
        this.userService = userService;
        this.organService = organService;
        this.commentService = commentService;
        this.goodService = goodService;
        this.counterService = counterService;
        this.messageService = messageService;
        this.producer = producer;
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
        t.setSecCheck(Interaction.SecCheck.UNKNOWN);
        t.setShowOrder(9999);

        if(StringUtils.isNotBlank(t.getOrganId())){
            Organization o = organService.get(t.getOrganId());
            t.setOrganId(o.getId());
            t.setOrganName(o.getName());
        }

        dao.insert(t);

        counterService.newEmpty(t.getId(), refDetail(t.getTitle()));
        producer.product(t.getId(), "interaction");

        return get(t.getId());
    }

    private String refDetail(String title){
        return "[政民互动] " + title;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Interaction update(Interaction t){
        Interaction o = get(t.getId());
        if(!StringUtils.equals(t.getUserId(), o.getUserId())){
            throw new BaseException("互动不存在");
        }

        if(o.getState() != Interaction.State.WAIT){
            throw new BaseException("正在处理不能修改");
        }

        Organization organ= organService.get(t.getOrganId());
        o.setOrganId(organ.getId());
        o.setOrganName(organ.getName());
        o.setType(t.getType());
        o.setTitle(t.getTitle());
        o.setContent(t.getContent());
        o.setImages(t.getImages());
        if(t.getOpen() != null){
            o.setOpen(t.getOpen());
        }
        if(t.getTop() != null){
            o.setTop(t.getTop());
        }

        if(dao.update(o)){
            counterService.updateDetail(o.getId(), refDetail(o.getTitle()));
            if(!StringUtils.equals(o.getContent(), t.getContent())){
                producer.product(t.getId(), "interaction");
            }
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
        if(t.getState() == Interaction.State.HANDING){
            throw new BaseException("已经在处理");
        }
        if(dao.updateState(id, Interaction.State.HANDING)){
            return get(id);
        }else{
            throw new BaseException("修改状态失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Interaction reply(String id, String replay, String siteId, String operator){
        Interaction t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("互动不存在");
        }

        if(dao.updateState(id, Interaction.State.REPLY)){
            dao.reply(id, replay);
            Interaction o = get(id);
            messageService.send(o);
            return o;
        }else{
            throw new BaseException("修改状态失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Interaction refuse(String id, String reason, String siteId, String operator){
        Interaction t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("互动不存在");
        }

        if(dao.updateState(id, Interaction.State.REFUSE)){
            dao.reply(id, reason);
            Interaction o = get(id);
            messageService.send(o);
            return o;
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
    public void updateSecCheck(String id, boolean ok){
        dao.updateSecCheck(id, ok? Interaction.SecCheck.OK: Interaction.SecCheck.RISKY);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean good(String id, String userId){
        return goodService.good(userId, id);
    }

    public List<Interaction> query(String siteId, String title, int offset, int limit){
        return dao.find(siteId, title, offset, limit);
    }

    public List<Interaction> queryByUserId(String userId, boolean handling, int offset, int limit){
        return dao.findByUserId(userId, handling, offset, limit);
    }

    public Long count(String siteId, String title, String nickname, String mobile, Interaction.State state){
        return dao.count(siteId, title, nickname, mobile, state);
    }

    public List<Interaction> query(String siteId, String title, String nickname, String mobile, Interaction.State state, int offset, int limit){
        return dao.find(siteId, title, nickname, mobile, state, offset, limit);
    }
}
