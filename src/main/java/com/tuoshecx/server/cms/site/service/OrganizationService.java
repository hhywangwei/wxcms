package com.tuoshecx.server.cms.site.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.site.dao.OrganizationDao;
import com.tuoshecx.server.cms.site.domain.Organization;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 组织机构业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class OrganizationService {
    private static final String ROOT_ID = "root";
    private final OrganizationDao dao;

    @Autowired
    public OrganizationService(OrganizationDao dao){
        this.dao = dao;
    }

    public Organization get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("组织机构不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Organization save(Organization t){
        t.setId(IdGenerators.uuid());
        if(isRoot(t.getParentId())){
            t.setParentId(ROOT_ID);
        }else if (!dao.has(t.getParentId())){
            throw new BaseException("上级组织机构不存在");
        }
        dao.insert(t);
        return get(t.getId());
    }

    private boolean isRoot(String parentId){
        return StringUtils.isBlank(parentId) || StringUtils.endsWithIgnoreCase(parentId, ROOT_ID);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Organization update(Organization t){
        Organization o = get(t.getId());
        if(!StringUtils.equals(t.getSiteId(), o.getSiteId())){
            throw new BaseException("组织机构不存在");
        }
        if(!StringUtils.equals(o.getParentId(), t.getParentId())){
            if(isRoot(t.getParentId())){
                t.setParentId(ROOT_ID);
            }else if (!dao.has(t.getParentId())){
                throw new BaseException("上级组织机构不存在");
            }
        }
        o.setParentId(t.getParentId());
        o.setName(t.getName());
        o.setIcon(t.getIcon());
        o.setShowOrder(t.getShowOrder());
        o.setRemark(t.getRemark());

        if(dao.update(o)){
            return get(o.getId());
        }else{
            throw new BaseException("修改组织机构失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id, String siteId){
        if(dao.hasChildren(id)){
            throw new BaseException("有下级组织机构");
        }
        Organization t = get(id);
        if(!StringUtils.equals(t.getSiteId(), siteId)){
            throw new BaseException("组织机构不存在");
        }
        return dao.delete(id);
    }

    public List<Organization> queryChildren(String parentId){
        String p = isRoot(parentId)? ROOT_ID : parentId;
        return dao.findChildren(p);
    }
}
