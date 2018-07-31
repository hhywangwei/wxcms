package com.tuoshecx.server.cms.sns.dao;

import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.cms.sns.domain.Comment;
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
 * 评论数据操作
 *
 * @author <href a="maito:hhywangwei@gmail.com">W[</href>
 */
@Repository
public class CommentDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Comment> mapper = (r, i) ->{
        Comment t = new Comment();

        t.setId(r.getString("id"));
        t.setSiteId(r.getString("site_id"));
        t.setUserId(r.getString("user_id"));
        t.setNickname(r.getString("nickname"));
        t.setHeadImg(r.getString("head_img"));
        t.setRefId(r.getString("ref_id"));
        t.setRefDetail(r.getString("ref_detail"));
        t.setContent(r.getString("content"));
        t.setImages(DaoUtils.toArray(r.getString("images")));
        t.setState(Comment.State.valueOf(r.getString("state")));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public CommentDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Comment t){
        final String sql = "INSERT INTO sns_comment (id, site_id, user_id, nickname, head_img, ref_id, ref_detail, " +
                "content, images, state, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getSiteId(), t.getUserId(), t.getNickname(), t.getHeadImg(), t.getRefId(),
                t.getRefDetail(), t.getContent(), DaoUtils.join(t.getImages()), Comment.State.WAIT.name(),
                DaoUtils.timestamp(new Date()));
    }

    public boolean updateState(String id, Comment.State state){
        final String sql = "UPDATE sns_comment SET state = ? WHERE id = ?";
        return jdbcTemplate.update(sql, state.name(), id) > 0;
    }

    public Comment findOne(String id){
        final String sql = "SELECT * FROM sns_comment WHERE = id";
        return jdbcTemplate.queryForObject(sql, new Object[]{id},mapper);
    }

    public List<Comment> find(String refId, int offset, int limit){
        final String sql = "SELECT * FROM sns_comment WHERE ref_id = ? ORDER BY create_time DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{refId, limit, offset}, mapper);
    }

    public Long count(String nickname, String refDetail, Comment.State state){
        final StringBuilder sql = new StringBuilder(100);
        sql.append("SELECT COUNT(id) FROM sns_comment ");
        buildWhere(sql, nickname, refDetail, state);

        return jdbcTemplate.queryForObject(sql.toString(), params(nickname, refDetail, state), Long.class);
    }

    private void buildWhere(StringBuilder sql, String nickname, String refDetail, Comment.State state){
        sql.append("WHERE 1 = 1 ");
        if(StringUtils.isNotBlank(nickname)){
            sql.append(" AND nickname LIKE ? ");
        }
        if(StringUtils.isNotBlank(refDetail)){
            sql.append(" AND ref_detail LIKE ? ");
        }
        if(state != null){
            sql.append(" AND state = ? ");
        }
    }

    private Object[] params(String nickname, String refDetail, Comment.State state){
        List<Object> params = new ArrayList<>(3);
        if(StringUtils.isNotBlank(nickname)){
            params.add(DaoUtils.like(nickname));
        }
        if(StringUtils.isNotBlank(refDetail)){
            params.add(DaoUtils.like(refDetail));
        }
        if(state != null){
            params.add(state.name());
        }
        return params.toArray();
    }

    public List<Comment> find(String nickname, String refDetail, Comment.State state, int offset, int limit){
        final StringBuilder sql = new StringBuilder(100);
        sql.append("SELECT * FROM sns_comment ");
        buildWhere(sql, nickname, refDetail, state);
        sql.append(" ORDER BY create_time DESC LIMIT ? OFFSET ?");

        Object[] params = DaoUtils.appendOffsetLimit(params(nickname, refDetail, state), offset, limit);
        return jdbcTemplate.query(sql.toString(), params, mapper);
    }
}
