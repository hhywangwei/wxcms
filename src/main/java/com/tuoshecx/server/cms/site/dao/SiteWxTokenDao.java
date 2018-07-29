package com.tuoshecx.server.cms.site.dao;

import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.cms.site.domain.SiteWxToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 站点微信配置数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SiteWxTokenDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SiteWxToken> mapper = (r, i) -> {
        SiteWxToken t = new SiteWxToken();

        t.setId(r.getString("id"));
        t.setSiteId(r.getString("site_id"));
        t.setAppid(r.getString("appid"));
        t.setAccessToken(r.getString("access_token"));
        t.setRefreshToken(r.getString("refresh_token"));
        t.setExpiresTime(r.getTimestamp("expires_time"));
        t.setUpdateTime(r.getDate("update_time"));

        return t;
    };

    @Autowired
    public SiteWxTokenDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SiteWxToken t){
        final String sql = "INSERT INTO site_wx_token (id, site_id, appid, access_token, refresh_token, expires_time, update_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getSiteId(), t.getAppid(), t.getAccessToken(), t.getRefreshToken(),
                DaoUtils.timestamp(t.getExpiresTime()), DaoUtils.timestamp(t.getUpdateTime()));
    }

    public boolean updateToken(String appid, String accessToken, String refreshToken, Date expiresTime, Date updateTime){
        final String sql = "UPDATE site_wx_token SET access_token = ?, refresh_token = ?, expires_time = ?, update_time = ? WHERE appid = ?";
        return jdbcTemplate.update(sql, accessToken, refreshToken, DaoUtils.timestamp(expiresTime), DaoUtils.timestamp(updateTime), appid) > 0;
    }

    public boolean delete(String appid){
        final String sql = "DELETE FROM site_wx_token WHERE appid = ?";
        return jdbcTemplate.update(sql, appid) > 0;
    }

    public SiteWxToken findOne(String id){
        final String sql = "SELECT * FROM site_wx_token WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean hasAppid(String appid){
        final String sql = "SELECT COUNT(id) FROM site_wx_token WHERE appid = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{appid}, Integer.class) > 0;
    }

    public SiteWxToken findOneByAppid(String appid){
        final String sql = "SELECT * FROM site_wx_token WHERE appid = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{appid}, mapper);
    }

    public List<SiteWxToken> findExpires(Date expires, int limit){
        final String sql = "SELECT * FROM site_wx_token WHERE expires_time < ? LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{expires, limit}, mapper);
    }
}
