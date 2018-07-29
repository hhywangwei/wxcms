package com.tuoshecx.server.cms.site.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.site.dao.ManagerDao;
import com.tuoshecx.server.cms.site.dao.SiteDao;
import com.tuoshecx.server.cms.site.domain.Manager;
import com.tuoshecx.server.cms.site.domain.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 站点管理员业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ManagerService {
    private final ManagerDao dao;
    private final SiteDao shopDao;

    @Autowired
    public ManagerService(ManagerDao dao, SiteDao shopDao){
        this.dao = dao;
        this.shopDao = shopDao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Manager save(Manager t){
        if(dao.hasUsername(t.getUsername())){
            throw new BaseException(20001, "用户名已经存在");
        }

        validateSite(t.getSiteId());
        t.setId(IdGenerators.uuid());
        t.setEnable(true);
        t.setPassword(encodePassword(t.getPassword()));
        dao.insert(t);

        return get(t.getId());
    }

    private void validateSite(String siteId){
        try{
            Site t = shopDao.findOne(siteId);
            if(t.getState() == Site.State.CLOSE){
                throw new BaseException("站点已经关闭");
            }
        }catch (DataAccessException e){
            throw new BaseException("站点不存在");
        }
    }

    private String encodePassword(String password){
        return password;
    }

    public Manager get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("用户不存在");
        }
    }

    public Manager getValidate(String username, String password){
        try{
            Manager t = dao.findOneByUsername(username);
            if(!t.getEnable()){
                throw new BaseException("站点管理员被禁用");
            }
            if(!t.getPassword().equals(encodePassword(password))){
                throw new BaseException("用户名或密码错误");
            }
            return t;
        }catch (DataAccessException e){
            throw new BaseException("用户名或密码错误");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Manager update(Manager t){
        validateSite(t.getSiteId());

        Manager o = get(t.getId());
        if(!o.getSiteId().equals(t.getSiteId())){
            throw new BaseException("无操作权限");
        }
        dao.update(t);

        return get(t.getId());
    }

    public Manager getSiteManager(String siteId){
        List<Manager> managers = dao.findBySite(siteId);
        if(managers.isEmpty()){
            throw new BaseException("站点管理员不存在");
        }
        return managers.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void activeManager(String shopId){
        dao.findBySite(shopId)
                .stream()
                .filter(Manager::getManager)
                .forEach(e -> dao.updateEnable(e.getId(), true));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void inactiveSite(String siteId){
        dao.findBySite(siteId)
                .stream()
                .filter(Manager::getEnable)
                .forEach(e -> dao.updateEnable(e.getId(), false));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Manager active(String id, String shopId){
        Manager o = get(id);
        if(!o.getSiteId().equals(shopId)){
            throw new BaseException("无操作权限");
        }
        dao.updateEnable(id, true);
        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Manager inactive(String id, String shopId){
        Manager o = get(id);
        if(!o.getSiteId().equals(shopId)){
            throw new BaseException("无操作权限");
        }
        dao.updateEnable(id, false);
        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updatePassword(String id, String shopId, String password, String newPassword){
        Manager o = get(id);
        if(!o.getSiteId().equals(shopId)){
            throw new BaseException("无操作权限");
        }
        if(!o.getPassword().equals(encodePassword(password))){
            throw new BaseException(20002, "密码错误");
        }
        return dao.updatePassword(id, newPassword);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean resetPassword(String id, String siteId, String newPassword){
        Manager o = get(id);
        if(!o.getSiteId().equals(siteId)){
            throw new BaseException("无操作权限");
        }
        return dao.updatePassword(id, newPassword);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id, String shopId){
        Manager o = get(id);
        if(!o.getSiteId().equals(shopId)){
            throw new BaseException("无操作权限");
        }
        return dao.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSite(String siteId){
        dao.findBySite(siteId).forEach(e -> dao.delete(e.getId()));
    }

    public boolean hasUsername(String username){
        return dao.hasUsername(username);
    }

    public long count(String siteId, String username, String name, String phone){
        return dao.count(siteId, username, name, phone);
    }

    public List<Manager> query(String siteId, String username, String name, String phone, int offset, int limit){
        return dao.find(siteId, username, name, phone, offset, limit);
    }
}


