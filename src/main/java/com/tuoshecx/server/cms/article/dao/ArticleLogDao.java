package com.tuoshecx.server.cms.article.dao;

import com.tuoshecx.server.cms.article.domain.ArticleLog;
import com.tuoshecx.server.cms.common.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 文章操作日志数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class ArticleLogDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ArticleLog> mapper = (r, i) -> {
        ArticleLog t = new ArticleLog();

        t.setId(r.getString("id"));
        t.setSiteId(r.getString("site_id"));
        t.setArticleId(r.getString("article_id"));
        t.setTitle(r.getString("title"));
        t.setManagerId(r.getString("manager_id"));
        t.setUsername(r.getString("username"));
        t.setAction(r.getString("action"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ArticleLogDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(ArticleLog t){
        final String sql = "INSERT INTO site_article_log (id, site_id, article_id, title, manager_id, username, action, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getSiteId(), t.getArticleId(), t.getTitle(), t.getManagerId(),
                t.getUsername(), t.getAction(), DaoUtils.timestamp(new Date()));
    }

    public Long count(String articleId, String username){
        final String sql = "SELECT COUNT(id) FROM site_article_log WHERE article_id = ? AND username LIKE ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{articleId, DaoUtils.like(username)}, Long.class);
    }

    public List<ArticleLog> find(String article, String username, int offset, int limit){
        final String sql = "SELECT * FROM site_article_log WHERE article_id = ? AND username LIKE ? " +
                "ORDER BY create_time ASC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{article, DaoUtils.like(username), limit, offset}, mapper);
    }
}
