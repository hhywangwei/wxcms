package com.tuoshecx.server.cms.analysis.dao;

import com.tuoshecx.server.cms.analysis.domain.SummaryTrend;
import com.tuoshecx.server.cms.common.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 小程序趋势概况数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SummaryTrendDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SummaryTrend> mapper = (r, i) -> {
        SummaryTrend t = new SummaryTrend();

        t.setId(r.getString("id"));
        t.setDate(r.getString("date_str"));
        t.setSiteId(r.getString("site_id"));
        t.setVisitTotal(r.getInt("visit_total"));
        t.setSharePv(r.getInt("share_pv"));
        t.setShareUv(r.getInt("share_uv"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public SummaryTrendDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SummaryTrend t){
        final String sql = "INSERT INTO ana_summary_trend (id, site_id, date_str, visit_total, share_pv, share_uv, create_time) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getSiteId(), t.getDate(), t.getVisitTotal(), t.getSharePv(),
                t.getShareUv(), DaoUtils.timestamp(new Date()));
    }

    public List<SummaryTrend> find(String siteId, String fromDate, String toDate){
        final String sql = "SELECT * FROM ana_summary_trend WHERE site_id = ? AND date >= ? AND date <= ?";
        return jdbcTemplate.query(sql, new Object[]{siteId, fromDate, toDate}, mapper);
    }
}
