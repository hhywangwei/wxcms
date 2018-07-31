package com.tuoshecx.server.cms.sns.dao;

import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.cms.sns.domain.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;

/**
 * 点赞数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class GoodDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GoodDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Good t){
        final String sql = "INSERT INTO sns_good (id, user_id, nickname, head_img, ref_id, create_time) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getUserId(), t.getNickname(), t.getHeadImg(), t.getRefId(), DaoUtils.timestamp(new Date()));
    }

    public boolean delete(String userId, String refId){
        final String sql = "DELETE FROM sns_good WHERE user_id = ? AND  ref_id = ?";
        return jdbcTemplate.update(sql, userId, refId) > 0;
    }

    public boolean has(String userId, String refId){
        final String sql = "SELECT COUNT(id) FROM sns_good WHERE user_id = ? AND ref_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{userId, refId}, Integer.class);
        return count != null && count > 0;
    }
}
