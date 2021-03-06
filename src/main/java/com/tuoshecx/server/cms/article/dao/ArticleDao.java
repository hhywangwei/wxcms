package com.tuoshecx.server.cms.article.dao;

import com.tuoshecx.server.cms.article.domain.Article;
import com.tuoshecx.server.cms.common.utils.DaoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文章数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class ArticleDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Article> mapper = (r, i) -> {
        Article t = new Article();

        t.setId(r.getString("id"));
        t.setSiteId(r.getString("site_id"));
        t.setChannelId(r.getString("channel_id"));
        t.setChannelPath(r.getString("channel_path"));
        t.setTitle(r.getString("title"));
        t.setShortTitle(r.getString("short_title"));
        t.setSubTitle(r.getString("sub_title"));
        t.setImage(r.getString("image"));
        t.setAuthor(r.getString("author"));
        t.setOrigin(r.getString("origin"));
        t.setKeywords(DaoUtils.toArray(r.getString("keywords")));
        t.setCatalogs(DaoUtils.toArray(r.getString("catalogs")));
        t.setTag(r.getString("tag"));
        t.setSummary(r.getString("summary"));
        t.setContent(r.getString("content"));
        t.setTemplate(r.getString("template"));
        t.setState(Article.State.valueOf(r.getString("state")));
        t.setComment(r.getBoolean("is_comment"));
        t.setShowOrder(r.getInt("show_order"));
        t.setTop(r.getBoolean("is_top"));
        t.setFreeze(r.getBoolean("is_freeze"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    private final RowMapper<Article> notContentMapper = (r, i) -> {
        Article t = new Article();

        t.setId(r.getString("id"));
        t.setSiteId(r.getString("site_id"));
        t.setChannelId(r.getString("channel_id"));
        t.setChannelPath(r.getString("channel_path"));
        t.setTitle(r.getString("title"));
        t.setShortTitle(r.getString("short_title"));
        t.setSubTitle(r.getString("sub_title"));
        t.setImage(r.getString("image"));
        t.setTag(r.getString("tag"));
        t.setSummary(r.getString("summary"));
        t.setTemplate(r.getString("template"));
        t.setState(Article.State.valueOf(r.getString("state")));
        t.setTop(r.getBoolean("is_top"));
        t.setAuthor(r.getString("author"));
        t.setShowOrder(r.getInt("show_order"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ArticleDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Article t){
        Date now = new Date();
        final String sql = "INSERT INTO site_article (id, site_id, channel_id, channel_path, title, short_title, sub_title, " +
                "author, origin, keywords, catalogs, tag, summary, content, image, is_comment, template, state, show_order, " +
                "is_top, is_freeze, update_time, create_time) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getSiteId(), t.getChannelId(), t.getChannelPath(), t.getTitle(), t.getShortTitle(),
                t.getSubTitle(), t.getAuthor(), t.getOrigin(), DaoUtils.join(t.getKeywords()), DaoUtils.join(t.getCatalogs()),
                t.getTag(), t.getSummary(), t.getContent(), t.getImage(), t.getComment(), t.getTemplate(), t.getState().name(),
                t.getShowOrder(), t.getTop(), t.getFreeze(), now, now);
    }

    public boolean update(Article t){
        final String sql = "UPDATE site_article SET title = ?, short_title = ?, sub_title = ?, author = ?, origin = ?, keywords = ?, catalogs = ?, " +
                "tag = ?, summary = ?, content = ?, image = ?, is_comment = ?, template = ?, show_order = ?, is_top = ?, update_time = ? " +
                "WHERE id = ? AND is_delete = false";

        return jdbcTemplate.update(sql, t.getTitle(), t.getShortTitle(), t.getSubTitle(), t.getAuthor(), t.getOrigin(), DaoUtils.join(t.getKeywords()),
                DaoUtils.join(t.getCatalogs()), t.getTag(), t.getSummary(), t.getContent(), t.getImage(), t.getComment(), t.getTemplate(),
                t.getShowOrder(), t.getTop(), DaoUtils.timestamp(new Date()), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "UPDATE site_article SET is_delete = true, update_time = ? WHERE id = ?";
        return jdbcTemplate.update(sql, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public boolean updateState(String id, Article.State state){
        final String sql = "UPDATE site_article SET state = ?, update_time = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, state.name(), DaoUtils.timestamp(new Date()), id) > 0;
    }

    public boolean showOrder(String id, Integer showOrder){
        final String sql = "UPDATE site_article SET show_order = ?, update_time = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, showOrder, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public boolean top(String id, boolean isTop){
        final String sql = "UPDATE site_article SET is_top = ?, update_time = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, isTop, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public void updateFreeze(String channelId, boolean freeze){
        final String sql = "UPDATE site_article SET is_freeze = ? WHERE channel_id = ? AND is_delete = false";
        jdbcTemplate.update(sql, freeze, channelId);
    }

    public Article findOne(String id){
        final String sql = "SELECT * FROM site_article WHERE id = ? AND is_delete = false";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean hasOfChannel(String channelId){
        final String sql = "SELECT COUNT(id) FROM site_article WHERE channel_id = ? AND is_delete = false";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{channelId}, Integer.class);
        return count != null && count > 0;
    }

    public Long count(String siteId, String channelId, String path, Article.State state, String title){
        StringBuilder sql = new StringBuilder(100);
        sql.append("SELECT COUNT(id) FROM site_article ");
        buildWhere(sql, siteId, channelId, path, state, title);
        Object[] params = params(siteId, channelId, path, state, title);
        return jdbcTemplate.queryForObject(sql.toString(), params, Long.class);
    }

    private void buildWhere(StringBuilder sql, String siteId, String channelId, String path, Article.State state, String title){
        sql.append(" WHERE is_delete = false ");
        if(StringUtils.isNotBlank(siteId)){
            sql.append(" AND site_id = ? ");
        }
        if (StringUtils.isNotBlank(channelId)) {
            sql.append(" AND channel_id = ? ");
        }
        if(StringUtils.isNotBlank(path)){
            sql.append(" AND channel_path LIKE ? ");
        }
        if(state != null){
            sql.append(" AND state = ? ");
        }
        if(StringUtils.isNotBlank(title)){
            sql.append(" AND title LIKE ? ");
        }
    }

    private Object[] params(String siteId, String channelId, String path, Article.State state, String title){
        List<Object>  params = new ArrayList<>(4);
        if(StringUtils.isNotBlank(siteId)){
            params.add(siteId);
        }
        if(StringUtils.isNotBlank(channelId)){
            params.add(channelId);
        }
        if(StringUtils.isNotBlank(path)){
            params.add(path+"%");
        }
        if(state != null){
            params.add(state.name());
        }
        if(StringUtils.isNotBlank(title)){
            params.add(DaoUtils.like(title));
        }
        return params.toArray();
    }

    public List<Article> find(String siteId, String channelId, String path,  Article.State state, String title, int offset, int limit){
        StringBuilder sql= new StringBuilder(100);
        sql.append("SELECT id, site_id, channel_id, channel_path, title, short_title, sub_title, tag, summary, image, " +
                "author, template, state, is_top, show_order, update_time, create_time FROM site_article ");
        buildWhere(sql, siteId, channelId, path, state, title);
        sql.append(" ORDER BY is_top DESC, show_order ASC, update_time DESC LIMIT ? OFFSET ?");
        Object[] params = DaoUtils.appendOffsetLimit(params(siteId, channelId, path, state, title), offset, limit);
        return jdbcTemplate.query(sql.toString(), params, notContentMapper);
    }

    public List<Article> findWhole(String siteId, String channelId, String channelPath, Article.State state, int limit){
        final String sql = "SELECT * FROM site_article " +
                " WHERE is_delete = false AND site_id = ? AND channel_id LIKE ? AND channel_path LIKE ? AND state = ? " +
                " ORDER BY is_top DESC, show_order ASC, update_time DESC LIMIT ?";

        return jdbcTemplate.query(sql, new Object[]{siteId, DaoUtils.blankLike(channelId),
                DaoUtils.blankLike(channelPath), state.name(), limit}, mapper);
    }

    public List<Article> findRelease(String siteId, String channelId, String path, String title, int offset, int limit){
        final String sql = "SELECT id, site_id, channel_id, channel_path, title, short_title, sub_title, " +
                "tag, summary, image, author, template, state, is_top, show_order, update_time, create_time " +
                "FROM site_article " +
                "WHERE site_id = ? AND channel_id LIKE ? AND channel_path LIKE ?  AND title LIKE ? AND state = ?" +
                "AND is_delete = false AND is_freeze = false " +
                "ORDER BY is_top DESC, show_order ASC, update_time DESC LIMIT ? OFFSET ?";

        String pathLike = StringUtils.isBlank(path)? "%" : path + "%";
        Object[] params = new Object[]{siteId, DaoUtils.blankLike(channelId),
                pathLike, DaoUtils.like(title), Article.State.RELEASE.name(), limit, offset};
        return jdbcTemplate.query(sql, params, notContentMapper);
    }
}
