package com.tuoshecx.server.cms.article.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.article.dao.ArticleDao;
import com.tuoshecx.server.cms.article.domain.Article;
import com.tuoshecx.server.cms.channel.domain.Channel;
import com.tuoshecx.server.cms.channel.service.ChannelService;
import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.common.utils.HtmlUtils;
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

    @Autowired
    public ArticleService(ArticleDao dao, ChannelService channelService) {
        this.dao = dao;
        this.channelService = channelService;
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
    public Article save(Article t){
        t.setId(IdGenerators.uuid());
        t.setState(Article.State.REEDIT);
        t.setReadCount(0);
        t.setGoodCount(0);
        if(t.getTop() == null){
            t.setTop(Boolean.FALSE);
        }
        if(t.getShowOrder() == null){
            t.setShowOrder(99999);
        }
        if(StringUtils.isBlank(t.getSummary())){
            t.setSummary(HtmlUtils.text(t.getContent(), 150));
        }
        dao.insert(t);
        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Article update(Article t){
        Article o = get(t.getId());
        if(!StringUtils.equals(t.getSiteId(), o.getSiteId())){
            throw new BaseException("文章不存在");
        }
        if(StringUtils.isBlank(t.getSummary())){
            t.setSummary(HtmlUtils.text(t.getContent(), 150));
        }
        dao.update(t);
        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Article copy(String id, String toChannelId, String siteId){
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
        return save(t);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Article move(String id, String toChannelId, String siteId){
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
        dao.insert(t);
        dao.delete(id);
        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Article release(String id, String siteId){
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
        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Article close(String id, String siteId){
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
        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id, String siteId){
        Article t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("文章不存在");
        }
        return dao.delete(id);
    }

    public Long count(String siteId, Article.State state, String title, Boolean isTop){
        return dao.count(siteId, state, title, isTop);
    }

    public List<Article> query(String siteId, Article.State state, String title, Boolean isTop, int offset, int limit){
        return dao.find(siteId, state, title, isTop, offset, limit);
    }
}
