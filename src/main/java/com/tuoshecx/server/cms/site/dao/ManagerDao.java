package com.tuoshecx.server.cms.site.dao;

import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.cms.site.domain.Manager;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 站点管理员数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class ManagerDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Manager> mapper = (r, i) -> {
        Manager t = new Manager();

        t.setId(r.getString("id"));
        t.setSiteId(r.getString("site_id"));
        t.setUsername(r.getString("username"));
        t.setPassword(r.getString("password"));
        t.setName(r.getString("name"));
        t.setHeadImg(r.getString("head_img"));
        t.setPhone(r.getString("phone"));
        t.setRoles(DaoUtils.toArray(r.getString("roles")));
        t.setManager(r.getBoolean("is_manager"));
        t.setEnable(r.getBoolean("is_enable"));
        t.setUserId(r.getString("user_id"));
        t.setNickname(r.getString("nickname"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ManagerDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Manager t){
        final String sql = "INSERT INTO site_manager " +
                "(id, site_id, username, password, name, head_img, phone, roles, is_manager, is_enable, user_id, " +
                "update_time, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        final Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getSiteId(), t.getUsername(), t.getPassword(),
                StringUtils.defaultString(t.getName()), t.getHeadImg(),
                StringUtils.defaultString(t.getPhone()), DaoUtils.join(t.getRoles()), t.getManager(),
                t.getEnable(), t.getId(), DaoUtils.timestamp(now), DaoUtils.timestamp(now));
    }

    public boolean update(Manager t){
        final String sql = "UPDATE site_manager SET name =?, head_img =?, phone =?, roles =?, update_time =? " +
                "WHERE id = ? AND is_delete =false";
        return jdbcTemplate.update(sql, StringUtils.defaultString(t.getName()),
                t.getHeadImg(), StringUtils.defaultString(t.getPhone()), DaoUtils.join(t.getRoles()),
                DaoUtils.timestamp(new Date()), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "UPDATE site_manager SET username = CONCAT(username,?), user_id = id, is_delete =true " +
                "WHERE id =? AND is_delete =false";
        final String random = "@" + String.valueOf(RandomUtils.nextInt(10000000, 99999999));
        return jdbcTemplate.update(sql, random, id) > 0;
    }

    public boolean updateEnable(String id, Boolean enabled){
        final String sql = "UPDATE site_manager SET is_enable =?, update_time =? WHERE id = ? AND is_delete= false";
        return jdbcTemplate.update(sql, enabled, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public boolean updatePassword(String id, String password){
        final String sql = "UPDATE site_manager SET password = ?, update_time = ? WHERE id = ?";
        return jdbcTemplate.update(sql, password, DaoUtils.date(new Date()), id) > 0;
    }

    public boolean updateUser(String id, String userId, String nickname, String headImg){
        final String sql = "UPDATE site_manager SET user_id = ?, nickname = ?, head_img = ? WHERE id = ? ";
        DaoUtils.setUtf8mb4(jdbcTemplate);
        return jdbcTemplate.update(sql, userId, nickname, headImg, id) > 0;
    }

    public boolean hasUsername(String username){
        final String sql = "SELECT COUNT(id) FROM site_manager WHERE username =?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count != null && count > 0;
    }

    public boolean hasUserId(String userId){
        final String sql = "SELECT COUNT(id) FROM site_manager WHERE user_id= ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{userId}, Integer.class);
        return count != null && count > 0;
    }

    public Manager findOne(String id){
        final String sql = "SELECT * FROM site_manager WHERE id =? AND is_delete =false";
        DaoUtils.setUtf8mb4(jdbcTemplate);
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Manager findOneByUsername(String username){
        final String sql = "SELECT * FROM site_manager WHERE username = ? AND is_delete = false";
        DaoUtils.setUtf8mb4(jdbcTemplate);
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, mapper);
    }

    public Manager findOneByBindUser(String userId, String siteId){
        final  String sql = "SELECT * FROM site_manager WHERE user_id = ? AND site_id = ? AND is_delete = false";
        DaoUtils.setUtf8mb4(jdbcTemplate);
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, siteId}, mapper);
    }

    public Long count(String siteId, String username, String name, String phone){
        final String sql = "SELECT COUNT(id) FROM site_manager " +
                "WHERE site_id =? AND username LIKE ? AND name LIKE ? AND phone LIKE ? AND is_delete =false";
        return jdbcTemplate.queryForObject(sql, buildParameters(siteId, username, name, phone), Long.class);
    }

    private Object[] buildParameters(String siteId, String username, String name, String phone){
        return new Object[]{siteId, DaoUtils.like(username), DaoUtils.like(name), DaoUtils.like(phone)};
    }

    public List<Manager> find(String siteId, String username, String name, String phone, int offset, int limit){
        final String sql = "SELECT * FROM site_manager " +
                "WHERE site_id =? AND username LIKE ? AND name LIKE ? AND phone LIKE ? AND is_delete = false " +
                "ORDER BY create_time DESC LIMIT ? OFFSET ?";

        Object[] params = DaoUtils.appendOffsetLimit(buildParameters(siteId, username, name, phone), offset, limit);
        DaoUtils.setUtf8mb4(jdbcTemplate);
        return jdbcTemplate.query(sql, params, mapper);
    }

    public List<Manager> findBySite(String siteId){
        final String sql = "SELECT * FROM site_manager WHERE site_id = ? AND is_delete = false";
        DaoUtils.setUtf8mb4(jdbcTemplate);
        return jdbcTemplate.query(sql, new Object[]{siteId}, mapper);
    }
}
