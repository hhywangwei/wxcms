package com.tuoshecx.server.cms.site.dao;

import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.cms.site.domain.SiteWxAuthorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 微信公众号认证数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SiteWxAuthorizedDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SiteWxAuthorized> mapper = (r, i) -> {
        SiteWxAuthorized t = new SiteWxAuthorized();

        t.setId(r.getString("id"));
        t.setSiteId(r.getString("site_id"));
        t.setAppid(r.getString("appid"));
        t.setNickname(r.getString("nickname"));
        t.setHeadImg(r.getString("head_img"));
        t.setServiceTypeInfo(r.getInt("service_type_info"));
        t.setVerifyTypeInfo(r.getInt("verify_type_info"));
        t.setUsername(r.getString("username"));
        t.setName(r.getString("name"));
        t.setBusinessInfo(r.getString("business_info"));
        t.setQrcodeUrl(r.getString("qrcode_url"));
        t.setAuthorizationInfo(r.getString("authorization_info"));
        t.setAuthorization(r.getBoolean("authorization"));
        t.setMiniProgramInfo(r.getString("mini_program_info"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return  t;
    };

    @Autowired
    public SiteWxAuthorizedDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SiteWxAuthorized t){
        final String sql = "INSERT INTO site_wx_authorized (id, site_id, appid, nickname, head_img, service_type_info," +
                "verify_type_info, username, name, business_info, mini_program_info, qrcode_url, authorization_info, authorization, update_time, create_time) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getSiteId(), t.getAppid(), t.getNickname(), t.getHeadImg(), t.getServiceTypeInfo(),
                t.getVerifyTypeInfo(), t.getUsername(), t.getName(), t.getBusinessInfo(), t.getMiniProgramInfo(), t.getQrcodeUrl(),
                t.getAuthorizationInfo(), t.getAuthorization(), DaoUtils.timestamp(now), DaoUtils.timestamp(now));
    }

    public boolean update(SiteWxAuthorized t) {
        final String sql = "UPDATE site_wx_authorized SET nickname = ?, head_img = ?, service_type_info = ?," +
                "verify_type_info = ?, username = ?, name = ?, business_info = ?, mini_program_info = ?, qrcode_url = ?," +
                "authorization_info = ?, authorization = ?, update_time = ? WHERE appid = ? ";

        return jdbcTemplate.update(sql, t.getNickname(), t.getHeadImg(), t.getServiceTypeInfo(), t.getVerifyTypeInfo(),
                t.getUsername(), t.getName(), t.getBusinessInfo(), t.getMiniProgramInfo(), t.getQrcodeUrl(), t.getAuthorizationInfo(),
                t.getAuthorization(), DaoUtils.timestamp(new Date()), t.getAppid()) > 0;
    }

    public boolean delete(String appid){
        final String sql = "DELETE FROM site_wx_authorized WHERE appid = ?";
        return jdbcTemplate.update(sql, appid) > 0;
    }

    public boolean hasAppid(String appid){
        return jdbcTemplate.queryForObject("SELECT COUNT(id) FROM site_wx_authorized WHERE appid = ?",
                new Object[]{appid}, Integer.class) > 0;
    }

    public SiteWxAuthorized findOne(String id){
        return jdbcTemplate.queryForObject("SELECT * FROM site_wx_authorized WHERE id = ?", new Object[]{id}, mapper);
    }

    public SiteWxAuthorized findOneByAppid(String appid){
        return jdbcTemplate.queryForObject("SELECT * FROM site_wx_authorized WHERE appid = ?", new Object[]{appid}, mapper);
    }

    public List<SiteWxAuthorized> findBySiteId(String siteId){
        return jdbcTemplate.query("SELECT * FROM site_wx_authorized WHERE site_id = ?", new Object[]{siteId}, mapper);
    }
}
