package com.tuoshecx.server.cms.article.service;

import com.tuoshecx.server.cms.article.dao.ArticleLogDao;
import com.tuoshecx.server.cms.article.domain.ArticleLog;
import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.site.domain.Manager;
import com.tuoshecx.server.cms.site.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文章操作日志业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ArticleLogService {
    private final ArticleLogDao dao;
    private final ManagerService managerService;

    @Autowired
    public ArticleLogService(ArticleLogDao dao, ManagerService managerService) {
        this.dao = dao;
        this.managerService = managerService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(String articleId, String title, String managerId, String action){
        Manager manager = managerService.get(managerId);
        ArticleLog t = new ArticleLog();
        t.setId(IdGenerators.uuid());
        t.setSiteId(manager.getSiteId());
        t.setManagerId(managerId);
        t.setUsername(manager.getUsername());
        t.setArticleId(articleId);
        t.setAction(action);
        t.setTitle(title);

        dao.insert(t);
    }

    public Long count(String articleId, String username){
        return dao.count(articleId, username);
    }

    public List<ArticleLog> query(String articleId, String username, int offset, int limit){
        return dao.find(articleId, username, offset, limit);
    }
}
