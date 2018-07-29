package com.tuoshecx.server.cms.channel.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.channel.dao.ChannelDao;
import com.tuoshecx.server.cms.channel.domain.Channel;
import com.tuoshecx.server.cms.common.id.IdGenerators;
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
 * 频道业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ChannelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelService.class);
    private static final String ROOT_ID = "root";

    private final ChannelDao dao;

    @Autowired
    public ChannelService(ChannelDao dao){
        this.dao = dao;
    }

    public Channel get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            LOGGER.error("Get channel id is {}, error is {}", id, e.getMessage());
            throw new BaseException("频道不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Channel save(Channel t){
        if(isRoot(t.getParentId())){
            t.setParentId(ROOT_ID);
        }else{
            if(!dao.has(t.getParentId())){
                throw new BaseException("上级频道不存在");
            }
        }

        t.setId(IdGenerators.uuid());
        dao.insert(t);

        return get(t.getId());
    }

    private boolean isRoot(String id){
        return StringUtils.isBlank(id) ||
                StringUtils.endsWithIgnoreCase(id, ROOT_ID);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Channel update(Channel t){
        Channel o = get(t.getId());

        o.setName(t.getName());
        o.setIcon(t.getIcon());
        o.setShowOrder(t.getShowOrder());

        dao.update(o);

        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id, String siteId) {
        if(dao.hasChildren(id)){
            throw new BaseException("有子频道不能删除");
        }
        Channel o = get(id);
        if(!StringUtils.equals(siteId, o.getSiteId())){
            throw new BaseException("频道不存在");
        }
        return dao.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Channel close(String id, String siteId){
        if(dao.hasChildren(id)){
            throw new BaseException("有子频道不能关闭");
        }

        Channel o = get(id);
        if(!StringUtils.equals(siteId, o.getSiteId())){
            throw new BaseException("频道不存在");
        }
        if(o.getState() == Channel.State.CLOSE){
            throw new BaseException("频道已经关闭");
        }
        if(dao.updateState(id, Channel.State.CLOSE)){
            return get(id);
        }else{
            throw new BaseException("频道关闭失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Channel open(String id, String siteId){
        Channel o = get(id);
        if(!StringUtils.equals(siteId, o.getSiteId())){
            throw new BaseException("频道不存在");
        }
        if(o.getState() == Channel.State.OPEN){
            throw new BaseException("频道已经发布");
        }
        if(dao.updateState(id, Channel.State.OPEN)){
            return get(id);
        }else{
            throw new BaseException("频道发布失败");
        }
    }

    public List<Channel> queryChildren(String parentId, Channel.State state){
        return dao.findChildren(parentId, state);
    }
}
