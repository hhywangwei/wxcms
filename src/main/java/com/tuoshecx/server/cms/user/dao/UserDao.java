package com.tuoshecx.server.cms.user.dao;

import com.tuoshecx.server.cms.user.domain.User;
import com.tuoshecx.server.cms.common.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.defaultString;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> mapper = (r, i) -> {
        User t = new User();

        t.setId(r.getString("id"));
        t.setSiteId(r.getString("site_id"));
        t.setAppid(r.getString("appid"));
        t.setOpenid(r.getString("openid"));
        t.setUnionid(r.getString("unionid"));
        t.setUsername(r.getString("username"));
        t.setPassword(r.getString("password"));
        t.setName(r.getString("name"));
        t.setNickname(r.getString("nickname"));
        t.setHeadImg(r.getString("head_img"));
        t.setPhone(r.getString("phone"));
        t.setSex(r.getString("sex"));
        t.setProvince(r.getString("province"));
        t.setCity(r.getString("city"));
        t.setCountry(r.getString("country"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public UserDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(User t){
        final String sql = "INSERT INTO site_user (id, site_id, appid, openid, unionid, username, password, name, " +
                "nickname, phone, sex, head_img, province, city, country, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        final Date date = new Date();
        DaoUtils.setUtf8mb4(jdbcTemplate);
        jdbcTemplate.update(sql, t.getId(), t.getSiteId(), t.getAppid(), t.getOpenid(), t.getUnionid(), t.getUsername(),
                t.getPassword(), defaultString(t.getName()), defaultString(t.getNickname()), defaultString(t.getPhone()),
                defaultString(t.getSex()), t.getHeadImg(), defaultString(t.getProvince()), defaultString(t.getCity()),
                defaultString(t.getCountry()), DaoUtils.timestamp(date), DaoUtils.timestamp(date));
    }

    public boolean update(User t){
        final String sql = "UPDATE site_user SET name = ?, nickname = ?, phone = ?, sex = ?, head_img = ?," +
                "province = ?, city = ?, country = ?, update_time = ? WHERE id = ?";
        DaoUtils.setUtf8mb4(jdbcTemplate);
        return jdbcTemplate.update(sql, defaultString(t.getName()), defaultString(t.getNickname()),
                defaultString(t.getPhone()), defaultString(t.getSex()), t.getHeadImg(), defaultString(t.getProvince()),
                defaultString(t.getCity()), defaultString(t.getCountry()), DaoUtils.timestamp(new Date()), t.getId()) > 0;
    }

    public User findOne(String id){
        final String sql = "SELECT * FROM site_user WHERE id = ?";
        DaoUtils.setUtf8mb4(jdbcTemplate);
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public User findOneByOpenid(String openid){
        final String sql = "SELECT * FROM site_user where openid = ?";
        DaoUtils.setUtf8mb4(jdbcTemplate);
        return jdbcTemplate.queryForObject(sql, new Object[]{openid}, mapper);
    }

    public User findOneByUsername(String username){
        final String sql = "SELECT * FROM site_user WHERE username = ?";
        DaoUtils.setUtf8mb4(jdbcTemplate);
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, mapper);
    }

    public boolean hasOpenid(String openid){
        final String sql = "SELECT COUNT(id) FROM site_user WHERE openid = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{openid}, Integer.class);
        return count != null && count > 0;
    }

    public boolean hasUsername(String username){
        final String sql = "SELECT COUNT(id) FROM site_user WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count != null && count > 0;
    }

    public boolean updatePassword(String id, String password){
        final String sql = "UPDATE site_user SET password = ? WHERE id = ?";
        return jdbcTemplate.update(sql, password, id) > 0;
    }

    public Long count(String siteId, String name, String nickname, String phone){
        final String sql = "SELECT COUNT(id) FROM site_user WHERE site_id = ? AND name LIKE ? " +
                "AND nickname LIKE ? AND phone LIKE ?";

        final String nameLike = DaoUtils.like(name);
        final String nicknameLike = DaoUtils.like(nickname);
        final String phoneLike = DaoUtils.like(phone);

        return jdbcTemplate.queryForObject(sql, new Object[]{siteId, nameLike, nicknameLike, phoneLike}, Long.class);
    }

    public List<User> find(String siteId, String name, String nickname, String phone, int offset, int limit){
        final String sql = "SELECT * FROM site_user " +
                "WHERE site_id = ? AND name LIKE ? AND nickname LIKE ? AND phone LIKE ? " +
                "ORDER BY create_time DESC LIMIT ? OFFSET ?";

        final String nameLike = DaoUtils.like(name);
        final String nicknameLike = DaoUtils.like(nickname);
        final String phoneLike = DaoUtils.like(phone);

        DaoUtils.setUtf8mb4(jdbcTemplate);
        return jdbcTemplate.query(sql, new Object[]{siteId, nameLike, nicknameLike, phoneLike, limit, offset}, mapper);
    }
}
