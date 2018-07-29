package com.tuoshecx.server.cms.site.dao;

import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.cms.site.domain.SiteWxAuthorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;

/**
 * 取消认证公众号信息数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SiteWxUnauthorizedDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SiteWxUnauthorizedDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SiteWxAuthorized t){
        final String sql = "INSERT INTO site_wx_unauthorized (id, site_id, appid, nickname, head_img, service_type_info," +
                "verify_type_info, username, name, business_info, mini_program_info, qrcode_url, authorization_info, create_time) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getSiteId(), t.getAppid(), t.getNickname(), t.getHeadImg(), t.getServiceTypeInfo(),
                t.getVerifyTypeInfo(), t.getUsername(), t.getName(), t.getBusinessInfo(), t.getMiniProgramInfo(), t.getQrcodeUrl(),
                t.getAuthorizationInfo(), DaoUtils.timestamp(now));
    }
}
