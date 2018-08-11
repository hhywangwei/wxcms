package com.tuoshecx.server.cms.article.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.article.dao.ArticleDao;
import com.tuoshecx.server.cms.article.domain.Article;
import com.tuoshecx.server.cms.channel.domain.Channel;
import com.tuoshecx.server.cms.channel.service.ChannelService;
import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.common.utils.HtmlUtils;
import com.tuoshecx.server.cms.sns.service.CounterService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文章业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleService.class);

    private final ArticleDao dao;
    private final ChannelService channelService;
    private final CounterService counterService;
    private final ArticleLogService logService;

    @Autowired
    public ArticleService(ArticleDao dao, ChannelService channelService,
                          CounterService counterService, ArticleLogService logService) {
        this.dao = dao;
        this.channelService = channelService;
        this.counterService = counterService;
        this.logService = logService;
    }

    public Article get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            LOGGER.error("Get {} article fail, error is {}", e.getMessage());
            throw new BaseException("文章不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Article save(Article t, String managerId){
        t.setId(IdGenerators.uuid());
        t.setState(Article.State.REEDIT);
        if(t.getTop() == null){
            t.setTop(Boolean.FALSE);
        }
        if(t.getShowOrder() == null){
            t.setShowOrder(99999);
        }
        if(StringUtils.isBlank(t.getSummary())){
            t.setSummary(HtmlUtils.text(t.getContent(), 150));
        }

        Channel channel = channelService.get(t.getChannelId());
        t.setChannelPath(channel.getPathFull());

        dao.insert(t);

        logService.save(t.getId(), t.getTitle(), managerId, "创建文章");
        counterService.newEmpty(t.getId(), refDetail(t.getTitle()));

        return dao.findOne(t.getId());
    }

    private String refDetail(String title){
        return "[文章] " + title;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Article update(Article t, String managerId){
        Article o = get(t.getId());
        if(!StringUtils.equals(t.getSiteId(), o.getSiteId())){
            throw new BaseException("文章不存在");
        }
        if(StringUtils.isBlank(t.getSummary())){
            t.setSummary(HtmlUtils.text(t.getContent(), 150));
        }

        dao.update(t);

        logService.save(t.getId(), t.getTitle(), managerId, "修改文章");
        counterService.updateDetail(t.getId(), refDetail(t.getTitle()));

        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Article top(String id, Boolean isTop, String siteId){
        Article o = get(id);
        if(!StringUtils.equals(siteId, o.getSiteId())){
            throw new BaseException("文章不存在");
        }
        if(dao.top(id, isTop)){
            return get(id);
        }else{
            throw new BaseException("设置文章置顶失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Article showOrder(String id, Integer showOrder, String siteId){
        Article o = get(id);
        if(!StringUtils.equals(siteId, o.getSiteId())){
            throw new BaseException("文章不存在");
        }
        if(dao.showOrder(id, showOrder)){
            return get(id);
        }else{
            throw new BaseException("设置文章显示顺序失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Article copy(String id, String toChannelId, String siteId, String managerId){
        Article t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("文章不存在");
        }
        Channel channel = channelService.get(toChannelId);
        if(!StringUtils.equals(channel.getSiteId(), siteId)){
            throw new BaseException("频道不存在");
        }
        if(StringUtils.equals(t.getSiteId(), toChannelId)){
            throw new BaseException("不能拷贝到文章所在频道");
        }
        return save(t, managerId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Article move(String id, String toChannelId, String siteId, String manageId){
        Article t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("文章不存在");
        }
        Channel channel = channelService.get(toChannelId);
        if(!StringUtils.equals(channel.getSiteId(), siteId)){
            throw new BaseException("频道不存在");
        }
        if(StringUtils.equals(t.getSiteId(), toChannelId)){
            throw new BaseException("不能移动到文章所在频道");
        }
        t.setId(IdGenerators.uuid());
        t.setChannelPath(channel.getPathFull());
        dao.insert(t);
        dao.delete(id);
        logService.save(t.getId(), t.getTitle(), manageId, "改变频道");
        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Article release(String id, String siteId, String managerId){
        Article t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("文章不存在");
        }
        if(t.getState() == Article.State.RELEASE){
            throw new BaseException("文章已经发布");
        }
        if(!dao.updateState(id, Article.State.RELEASE)){
            throw new BaseException("发布文章失败");
        }

        logService.save(t.getId(), t.getTitle(), managerId, "发布文章");
        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Article close(String id, String siteId, String managerId){
        Article t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("文章不存在");
        }
        if(t.getState() == Article.State.CLOSE){
            throw new BaseException("文章已经关闭");
        }
        if(!dao.updateState(id, Article.State.CLOSE)){
            throw new BaseException("文章关闭失败");
        }

        logService.save(t.getId(), t.getTitle(), managerId, "关闭文章");

        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id, String siteId, String managerId){
        Article t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("文章不存在");
        }

        logService.save(t.getId(), t.getTitle(), managerId, "删除文章");
        return dao.delete(id);
    }

    public Long count(String siteId, String channelId, String path, Article.State state, String title){
        return dao.count(siteId, channelId, path, state, title);
    }

    public List<Article> query(String siteId,  String channelId, String path, Article.State state, String title, int offset, int limit){
        return dao.find(siteId, channelId, path, state, title, offset, limit);
    }

    public List<Article> querydWhole(String siteId, String channelId, Article.State state, int limit){
        return dao.findWhole(siteId, channelId, state, limit);
    }
}

