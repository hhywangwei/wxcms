package com.tuoshecx.server.cms.sns.dao;

import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.cms.sns.domain.Read;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;

/**
 * 读记录
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class ReadDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReadDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Read t){
        final String sql = "INSERT INTO sns_read (id, openid, ref_id, create_time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getOpenid(), t.getRefId(), DaoUtils.timestamp(new Date()));
    }

    public boolean has(String openid, String refId){
        final String sql = "SELECT COUNT(id) FROM sns_read WHERE openid = ? AND ref_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{openid, refId}, Integer.class);
        return count != null && count > 0;
    }
}
