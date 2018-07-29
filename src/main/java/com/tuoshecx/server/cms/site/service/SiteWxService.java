package com.tuoshecx.server.cms.site.service;

import com.tuoshecx.server.cms.common.id.IdGenerators;
import com.tuoshecx.server.cms.site.dao.SiteWxAuthorizedDao;
import com.tuoshecx.server.cms.site.dao.SiteWxTokenDao;
import com.tuoshecx.server.cms.site.dao.SiteWxUnauthorizedDao;
import com.tuoshecx.server.cms.site.domain.SiteWxAuthorized;
import com.tuoshecx.server.cms.site.domain.SiteWxToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 站点微信小程序业务服务
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class SiteWxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteWxService.class);

    private final SiteWxAuthorizedDao authorizedDao;
    private final SiteWxTokenDao tokenDao;
    private final SiteWxUnauthorizedDao unauthorizedDao;

    @Autowired
    public SiteWxService(SiteWxAuthorizedDao authorizedDao, SiteWxTokenDao tokenDao, SiteWxUnauthorizedDao unauthorizedDao) {
        this.authorizedDao = authorizedDao;
        this.tokenDao = tokenDao;
        this.unauthorizedDao = unauthorizedDao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveToken(SiteWxToken t){
        if(tokenDao.hasAppid(t.getAppid())){
            tokenDao.updateToken(t.getAppid(), t.getAccessToken(),
                    t.getRefreshToken(), t.getExpiresTime(), t.getUpdateTime());
        }else{
            t.setId(IdGenerators.uuid());
            tokenDao.insert(t);
        }
    }

    public Optional<SiteWxToken> getToken(String appid){
        try{
            return Optional.of(tokenDao.findOneByAppid(appid));
        }catch (DataAccessException e){
            LOGGER.error("Get wx token {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveAuthorized(SiteWxAuthorized t){
        if(authorizedDao.hasAppid(t.getAppid())){
            authorizedDao.update(t);
        }else{
            t.setId(IdGenerators.uuid());
            authorizedDao.insert(t);
        }
    }

    public Optional<SiteWxAuthorized> getAuthorized(String appid){
        try{
            return Optional.of(authorizedDao.findOneByAppid(appid));
        }catch (DataAccessException e){
            LOGGER.error("Get wx configure {}", e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<String> getAppid(String siteId){
        return queryAuthorized(siteId).stream().map(SiteWxAuthorized::getAppid).findFirst();
    }

    public List<SiteWxAuthorized> queryAuthorized(String siteId){
        return authorizedDao.findBySiteId(siteId);
    }

    public List<SiteWxToken> queryExpiresToken(int limit){
       return tokenDao.findExpires(new Date(), limit);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void unauthorized(String appid){
        if(!authorizedDao.hasAppid(appid)){
            LOGGER.debug("unauthorized appid {} not exist", appid);
            return ;
        }

        SiteWxAuthorized configure = authorizedDao.findOneByAppid(appid);
        unauthorizedDao.insert(configure);
        LOGGER.debug("Save {} unauthorized success", appid);
        authorizedDao.delete(appid);
        tokenDao.delete(appid);
    }
}
