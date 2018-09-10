package com.tuoshecx.server.cms.questionnaire.service;

import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.common.id.UUIDGenerator;
import com.tuoshecx.server.cms.questionnaire.dao.QueProjectDao;
import com.tuoshecx.server.cms.questionnaire.domain.QueProject;
import com.tuoshecx.server.cms.site.domain.Manager;
import com.tuoshecx.server.cms.site.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 问卷调查项目服务
 * @author LuJun
 */
@Service
@Transactional(readOnly = true)
public class QueProjectService {

    private final QueProjectDao queProjectDao;
    private final ManagerService managerService;

    @Autowired
    public QueProjectService(QueProjectDao queProjectDao,ManagerService managerService) {
        this.queProjectDao = queProjectDao;
        this.managerService = managerService;

    }

    /**
     * 新增问卷调查项目
     * @param q
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public QueProject save(QueProject q,String managerId){
        Manager manager = managerService.get(managerId);
        q.setCreateUser(manager.getUsername());
        q.setUpdateUser(manager.getUsername());
        q.setId(IdGenerators.uuid());
        queProjectDao.insert(q);
        return queProjectDao.findOne(q.getId());
    }


    /**
     * 修改问卷调查项目
     * @param q
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public QueProject udpate(QueProject q,String managerId){
        Manager manager = managerService.get(managerId);
        q.setUpdateUser(manager.getUsername());
        queProjectDao.update(q);
        return queProjectDao.findOne(q.getId());
    }

    /**
     * 得到问卷调查项目
     * @param id
     * @return
     */
    public QueProject get(String id){
        return queProjectDao.findOne(id);
    }

    /**
     * 分页查询问卷项目
     * @param queInfoId
     * @param type
     * @param offset
     * @param limit
     * @return
     */
    public List<QueProject> queryProject(String queInfoId,String title,String type,int offset, int limit){
        return queProjectDao.findListByInfoIdAndType(queInfoId,title,type,offset,limit);
    }

    /**
     * 统计项目条数
     * @param queInfoId
     * @param type
     * @return
     */
    public Long count(String queInfoId,String title,String type){
        return queProjectDao.count(queInfoId,title,type);
    }

}
