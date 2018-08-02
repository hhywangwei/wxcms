package com.tuoshecx.server.wx.small.devops.dao;

import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.wx.small.devops.domain.SmallTester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 小程序测试号数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SmallTesterDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SmallTester> mapper = (r, i) -> {
        SmallTester t = new SmallTester();

        t.setId(r.getString("id"));
        t.setSiteId(r.getString("site_id"));
        t.setAppid(r.getString("appid"));
        t.setWechatid(r.getString("wechatid"));
        t.setUserstr(r.getString("user_str"));
        t.setCreateTime(r.getTimestamp("create_time"));
        return t;
    };

    @Autowired
    public SmallTesterDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SmallTester t){
        final String sql = "INSERT INTO wx_small_tester (id, site_id, appid, wechatid, user_str, create_time) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getSiteId(), t.getWechatid(), t.getUserstr(), DaoUtils.timestamp(new Date()));
    }

    public SmallTester findOne(String id){
        final String sql = "SELECT * FROM wx_small_tester WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public SmallTester findOne(String siteId, String wechatid){
        final String sql = "SELECT * FROM wx_small_tester WHERE site_id = ? AND wechatid = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{siteId, wechatid}, mapper);
    }

    public boolean has(String siteId, String wechatid){
        final String sql = "SELECT COUNT(id) FROM wx_small_tester WHERE site_id =  ? AND wechatid = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{siteId, wechatid}, Integer.class);
        return count != null && count > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM wx_small_tester WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public List<SmallTester> find(String siteId){
        final String sql = "SELECT * FROM wx_small_tester WHERE site_id = ?";
        return jdbcTemplate.query(sql, new Object[]{siteId}, mapper);
    }
}
