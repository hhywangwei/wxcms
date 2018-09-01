package com.tuoshecx.server.cms.questionnaire.service;

import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.questionnaire.dao.QueInfoDao;
import com.tuoshecx.server.cms.questionnaire.domain.QueInfo;
import com.tuoshecx.server.cms.questionnaire.domain.QueProject;
import com.tuoshecx.server.cms.site.domain.Manager;
import com.tuoshecx.server.cms.site.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  问卷调查信息服务
 * @author LuJun
 */
@Service
@Transactional(readOnly = true)
public class QueInfoService {

    private final QueInfoDao queInfoDao;
    private final ManagerService managerService;

    @Autowired
    public QueInfoService(QueInfoDao queInfoDao,ManagerService managerService) {
        this.queInfoDao = queInfoDao;
        this.managerService = managerService;

    }

    /**
     * 新增问卷调查信息
     * @param q
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public QueInfo save(QueInfo q,String managerId){
        Manager manager = managerService.get(managerId);
        q.setId(IdGenerators.uuid());
        q.setCreateUser(manager.getUsername());
        queInfoDao.insert(q);
        return queInfoDao.findOne(q.getId());
    }

    /**
     * 修改问卷调查信息
     * @param q
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public QueInfo udpate(QueInfo q,String managerId){
        Manager manager = managerService.get(managerId);
        q.setUpdateUser(manager.getUsername());
        queInfoDao.update(q);
        return queInfoDao.findOne(q.getId());
    }

    /**
     * 删除问卷调查信息
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return queInfoDao.delete(id);
    }

    /**
     * 分页查询问卷调查信息
     * @param organId
     * @param state
     * @param offset
     * @param limit
     * @return
     */
    public List<QueInfo> queryInfo(String organId,String state ,int offset, int limit){
        return queryInfo(organId,state,offset,limit);
    }

    /**
     * 开启状态
     * @param id
     * @return
     */
    public boolean open(String id,String managerId){
        Manager manager = managerService.get(managerId);
        return queInfoDao.updateState(id,QueInfo.State.OPEN,manager.getUsername());
    }

    /**
     * 关闭状态
     * @param id
     * @return
     */
    public boolean close(String id,String managerId){
        Manager manager = managerService.get(managerId);
        return queInfoDao.updateState(id,QueInfo.State.CLOSE,manager.getUsername());
    }

    /**
     * 得到问卷调查项目
     * @param id
     * @return
     */
    public QueInfo get(String id){
        return queInfoDao.findOne(id);
    }

    /**
     * 统计项目条数
     * @param organId
     * @param state
     * @return
     */
    public Long count(String organId,String state){
        return queInfoDao.count(organId,state);
    }
}
