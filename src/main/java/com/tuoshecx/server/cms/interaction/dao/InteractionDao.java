package com.tuoshecx.server.cms.interaction.dao;

import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.cms.interaction.domain.Interaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 政民互动记录数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWeiss</a>
 */
@Repository
public class InteractionDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Interaction> mapper = (r, i) -> {
        Interaction t = new Interaction();

        t.setId(r.getString("id"));
        t.setSiteId(r.getString("site_id"));
        t.setUserId(r.getString("user_id"));
        t.setNickname(r.getString("nickname"));
        t.setHeadImg(r.getString("head_img"));
        t.setOrganId(r.getString("organ_id"));
        t.setOrganName(r.getString("organ_name"));
        t.setTitle(r.getString("title"));
        t.setAction(Interaction.Action.valueOf(r.getString("action")));
        t.setContent(r.getString("content"));
        t.setImages(DaoUtils.toArray(r.getString("images")));
        t.setReadCount(r.getInt("read_count"));
        t.setGoodCount(r.getInt("good_count"));
        t.setCommentCount(r.getInt("comment_count"));
        t.setOpen(r.getBoolean("is_open"));
        t.setTop(r.getBoolean("is_top"));
        t.setShowOrder(r.getInt("show_order"));
        t.setReplay(r.getString("replay"));
        t.setReplayTime(r.getTimestamp("reply_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public InteractionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Interaction t){
        final String sql = "INSERT INTO site_interaction (id, site_id, user_id, nickname, head_img, organ_id, organ_name," +
                " title, action, content, images, is_open, is_top, show_order, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getSiteId(), t.getUserId(), t.getNickname(), t.getHeadImg(),
                t.getOrganId(), t.getOrganName(), t.getTitle(), t.getAction().name(), t.getContent(), DaoUtils.join(t.getImages()),
                t.getOpen(), t.getTop(), t.getShowOrder(), DaoUtils.timestamp(new Date()));
    };

    public boolean updateOrgan(String id, String organId, String organName){
        final String sql = "UPDATE site_interaction WHERE SET organ_id = ? AND organ_name = ? WHERE id = ?";
        return jdbcTemplate.update(sql, organId, organName, id) > 0;
    }


    public boolean updateState(String id, Interaction.State state){
        final String sql ="UPDATE site_interaction SET state = ? WHERE id = ?";
        return jdbcTemplate.update(sql, state.name(), id) > 0;
    }

    public boolean top(String id, boolean top){
        final String sql = "UPDATE site_interaction SET is_top = ? WHERE id = ?";
        return jdbcTemplate.update(sql, top, id) > 0;
    }

    public boolean showOrder(String id, Integer showOrder){
        final String sql = "UPDATE site_interaction SET show_order = ? WHERE id = ?";
        return jdbcTemplate.update(sql, showOrder, id) > 0;
    }

    public boolean replay(String id, String replay){
        final String sql = "UPDATE site_interaction SET replay = ? WHERE id = ?";
        return jdbcTemplate.update(sql, replay, id) > 0;
    }

    public boolean incReadCount(String id, int count){
        final String sql = "UPDATE site_interaction SET read_count = read_count + ? WHERE id = ?";
        return jdbcTemplate.update(sql, count, id) > 0;
    }

    public boolean incGoodCount(String id, int count){
        final String sql = "UPDATE site_interaction SET good_count = good_count + ? WHERE id = ?";
        return jdbcTemplate.update(sql, count, id) > 0;
    }

    public boolean incCommentCount(String id, int count){
        final String sql = "UPDATE set_interaction SET comment_count = comment_count + 1 WHERE id = ?";
        return jdbcTemplate.update(sql, count, id) > 0;
    }

    public Interaction findOne(String id){
        final String sql = "SELECT * FROM site_interaction WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String siteId, String title, String nickname, String mobile, Interaction.State state){
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(id) FROM site_interaction ");
        buildWhere(sql, siteId, title, nickname, mobile, state);
        return jdbcTemplate.queryForObject(sql.toString(), params(siteId, title, nickname, mobile, state), Long.class);
    }

    private void buildWhere(StringBuilder sql, String siteId, String title,
                            String nickname, String mobile, Interaction.State state){

        sql.append(" WHERE 1 = 1 ");
        if(StringUtils.isNotBlank(siteId)){
            sql.append(" AND site_id = ? ");
        }
        if(StringUtils.isNotBlank(title)){
            sql.append(" AND title LIKE ? ");
        }
        if(StringUtils.isNotBlank(nickname)){
            sql.append(" AND nickname = ? ");
        }
        if(StringUtils.isNotBlank(mobile)){
            sql.append(" AND mobiles = ? ");
        }
        if(state != null){
            sql.append(" AND state = ? ");
        }
    }

    private Object[] params(String siteId, String title, String nickname, String mobile, Interaction.State state){
        List<Object> params = new ArrayList<>(5);
        if(StringUtils.isNotBlank(siteId)){
            params.add(state);
        }
        if(StringUtils.isNotBlank(title)){
            params.add(DaoUtils.like(title));
        }
        if(StringUtils.isNotBlank(nickname)){
            params.add(DaoUtils.like(nickname));
        }
        if(StringUtils.isNotBlank(mobile)){
            params.add(mobile);
        }
        if(state != null){
            params.add(state);
        }
        return params.toArray();
    }

    public List<Interaction> find(String siteId, String title, String nickname, String mobile, Interaction.State state, int offset, int limit){
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM site_interaction ");
        buildWhere(sql, siteId, title, nickname, mobile, state);
        sql.append(" ORDER BY create_time DESC LIMIT ? OFFSET");
        Object[] params = DaoUtils.appendOffsetLimit(params(title, siteId, nickname, mobile, state), offset, limit);
        return jdbcTemplate.query(sql.toString(), params, mapper);
    }

    public List<Interaction> find(String title, int offset, int limit){
        final String sql = "SELECT * FROM site_interaction WHERE state IN ('HANDING', 'REPLAY') AND open = true " +
                "AND title LIKE ? ORDER BY is_top DESC, show_order ASC, create_time DESC LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, new Object[]{DaoUtils.like(title), limit, offset}, mapper);
    }

    public List<Interaction> findByUserId(String userId, int offset, int limit){
        final String sql = "SELECT * FROM site_interaction WHERE user_id = ? ORDER BY create_time DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{userId, limit, offset}, mapper);
    }

}
