package com.tuoshecx.server.cms.site.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.common.utils.HtmlUtils;
import com.tuoshecx.server.cms.site.dao.SiteDao;
import com.tuoshecx.server.cms.site.dao.SiteWxTokenDao;
import com.tuoshecx.server.cms.site.domain.Manager;
import com.tuoshecx.server.cms.site.domain.Site;
import com.tuoshecx.server.cms.site.domain.SiteWxToken;
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
 *  站点业务服务
 *
 *  @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class SiteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteService.class);

    private final SiteDao dao;
    private final SiteWxTokenDao wxTokenDao;
    private final ManagerService managerService;

    @Autowired
    public SiteService(SiteDao dao, SiteWxTokenDao wxConfigureDao, ManagerService managerService){
        this.dao = dao;
        this.wxTokenDao = wxConfigureDao;
        this.managerService = managerService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Site save(Site t, String manager, String password){
        t.setId(IdGenerators.uuid());
        t.setState(Site.State.WAIT);
        if(StringUtils.isBlank(t.getSummary())){
            t.setSummary(summary(t.getDetail()));
        }
        dao.insert(t);

        saveManager(t.getId(), manager, password);

        return get(t.getId());
    }

    private String summary(String detail){
        return HtmlUtils.text(detail, 200);
    }

    private void saveManager(String shopId, String manager, String password){
        Manager t = new Manager();

        t.setId(IdGenerators.uuid());
        t.setSiteId(shopId);
        t.setUsername(manager);
        t.setPassword(password);
        t.setName("");
        t.setPhone("");
        t.setEnable(true);
        t.setManager(true);
        t.setRoles(new String[]{"ROLE_SHOP_ADMIN"});
        managerService.save(t);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Site update(Site t){
        Site o = get(t.getId());

        if(StringUtils.isBlank(t.getSummary())){
            t.setSummary(summary(t.getDetail()));
        }

        if(!dao.update(t)){
            throw new BaseException("修改站点失败");
        }
        return get(t.getId());
    }

    public Site get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            LOGGER.warn("Get shop fail, error is {}", e.getMessage());
            throw new BaseException("站点不存在");
        }
    }

    public Site getByAppid(String appid){
        try{
            LOGGER.debug("Appid is {}", appid);
            SiteWxToken c = wxTokenDao.findOneByAppid(appid);
            LOGGER.debug("Site id is {}", c.getSiteId());
            return get(c.getSiteId());
        }catch (DataAccessException e){
            throw new BaseException("站点不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        Site t = get(id);

        if(t.getState() == Site.State.OPEN){
            throw new BaseException("发布站点不能直接删除");
        }

        boolean ok = dao.delete(id);
        if(ok){
            managerService.deleteSite(id);
        }

        return ok;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Site open(String id){
        Site t = get(id);

        boolean ok = dao.updateState(id, Site.State.OPEN);
        if(ok && t.getState() == Site.State.CLOSE){
            managerService.activeManager(id);
        }

        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Site close(String id){
        Site t = get(id);

        if(t.getState() == Site.State.CLOSE){
            throw new BaseException("站点已经关闭");
        }
        if(dao.updateState(id, Site.State.CLOSE)){
            managerService.inactiveSite(id);
        }

        return get(id);
    }

    public long count(String name, String phone){
        return dao.count(name, phone);
    }

    public List<Site> query(String name, String phone, int offset, int limit){
        return dao.find(name, phone, offset, limit);
    }
}
