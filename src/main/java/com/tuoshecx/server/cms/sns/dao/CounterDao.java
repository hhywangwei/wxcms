package com.tuoshecx.server.cms.sns.dao;

import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.cms.sns.domain.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;

/**
 * 文章等计数数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class CounterDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Counter> mapper = (r, i) -> {
        Counter t = new Counter();
        t.setId(r.getString("id"));
        t.setRefId(r.getString("ref_id"));
        t.setRefDetail(r.getString("ref_detail"));
        t.setReadCount(r.getInt("read_count"));
        t.setGoodCount(r.getInt("good_count"));
        t.setCommentCount(r.getInt("comment_count"));
        t.setShareCount(r.getInt("share_count"));
        t.setCreateTime(r.getTimestamp("create_time"));
        t.setUpdateTime(r.getTimestamp("update_time"));

        return t;
    };

    @Autowired
    public CounterDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Counter t){
        Date now = new Date();
        final String sql = "INSERT INTO sns_counter (id, ref_id, ref_detail, read_count, good_count, comment_count, " +
                "share_count, update_time, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getRefId(), t.getRefDetail(), t.getReadCount(), t.getGoodCount(),
                t.getCommentCount(), t.getShareCount(), DaoUtils.timestamp(now), DaoUtils.timestamp(now));
    }

    public boolean has(String refId){
        final String sql = "SELECT COUNT(id) FROM sns_counter WHERE ref_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{refId}, Integer.class);
        return count != null && count > 0;
    }

    public Counter findOne(String refId){
        final String sql = "SELECT * FROM sns_counter WHERE ref_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{refId}, mapper);
    }

    public void incRead(String refId, int count){
        final String sql = "UPDATE sns_counter SET read_count = read_count + 1, update_time = now() WHERE ref_id = ?";
        jdbcTemplate.update(sql, count, refId);
    }

    public void incGood(String refId, int count){
        final String sql = "UPDATE sns_counter SET good_count = good_count + 1, update_time = now() WHERE ref_id = ?";
        jdbcTemplate.update(sql, count, refId);
    }

    public void incComment(String refId, int count){
        final String sql = "UPDATE sns_counter SET comment_count = comment_count + 1, update_time = now() WHERE ref_id =?";
        jdbcTemplate.update(sql, count, refId);
    }

    public void incShare(String refId, int count){
        final String sql = "UPDATE sns_counter SET share_count = share_count + 1, update_time = now() WHERE ref_id =?";
        jdbcTemplate.update(sql, count, refId);
    }

    public void updateDetail(String refId, String detail){
        final String sql = "UPDATE sns_counter SET ref_detail = ? WHERE ref_id = ?";
        jdbcTemplate.update(sql, detail, refId);
    }
}
