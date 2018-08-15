package com.tuoshecx.server.cms.analysis.dao;

import com.tuoshecx.server.cms.analysis.domain.VisitTrend;
import com.tuoshecx.server.cms.common.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 访问趋势数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class VisitTrendDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<VisitTrend> mapper = (r, i) -> {
        VisitTrend t = new VisitTrend();

        t.setId(r.getString("id"));
        t.setSiteId(r.getString("site_id"));
        t.setDateStr(r.getString("date_str"));
        t.setSessionCnt(r.getInt("session_cnt"));
        t.setVisitPv(r.getInt("visit_pv"));
        t.setVisitUv(r.getInt("visit_uv"));
        t.setVisitUvNew(r.getInt("visit_uv_new"));
        t.setStayTimeUv(r.getFloat("stay_time_uv"));
        t.setStayTimeSession(r.getFloat("stay_time_session"));
        t.setVisitDepth(r.getFloat("visit_depth"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public VisitTrendDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(VisitTrend t){
        final String sql = "INSERT INTO ana_visit_trend (id, site_id, date_str, type, session_cnt, visit_pv, visit_uv, " +
                "visit_uv_new, stay_time_uv, stay_time_session, visit_depth, create_time) VALUES (?, ?, ?, ?, ?，?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getSiteId(), t.getDateStr(), t.getType(), t.getSessionCnt(), t.getVisitPv(), t.getVisitUv(),
                t.getVisitUvNew(), t.getStayTimeUv(), t.getStayTimeSession(), t.getVisitDepth(), DaoUtils.timestamp(new Date()));
    }

    public boolean has(String siteId, String dateStr){
        final String sql = "SELECT COUNT(id) FROM ana_visit_trend WHERE site_id = ? AND date_str = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{siteId, dateStr}, Integer.class);
        return count != null && count > 0;
    }

    public List<VisitTrend> find(String siteId, String type, String from, String to){
        final String sql = "SELECT * FROM ana_visit_trend WHERE site_id = ? AND date_str = ? AND type = ? AND date_str >= ? AND date_str <= ?";
        return jdbcTemplate.query(sql, new Object[]{siteId, type, from, to}, mapper);
    }

}
