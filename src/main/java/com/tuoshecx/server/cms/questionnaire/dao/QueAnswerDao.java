package com.tuoshecx.server.cms.questionnaire.dao;

import com.tuoshecx.server.cms.article.domain.Article;
import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.cms.questionnaire.domain.QueAnswer;
import org.apache.catalina.LifecycleState;
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
 * 问卷答题dao
 * @author LuJun
 */
@Repository
public class QueAnswerDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public QueAnswerDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<QueAnswer> mapper = (r, i) -> {
        QueAnswer q = new QueAnswer();

        q.setId(r.getString("id"));
        q.setAnswer(r.getString("answer"));
        q.setUserId(r.getString("user_id"));
        q.setHeadImg(r.getString("head_img"));
        q.setNickName(r.getString("nickname"));
        q.setDelete(r.getBoolean("is_delete"));
        q.setQueInfoId(r.getString("que_info_id"));
        q.setOrganId(r.getString("organ_id"));
        q.setCreateTime(r.getTimestamp("create_time"));
        q.setAnswerTime(r.getString("answer_time"));

        return q;
    };

    /**
     * 新建问卷用户答案
     * @param q
     */
    public void insert(QueAnswer q){
        final Date now = new Date();
        final String sql = "INSERT INTO que_answer (id, user_id, head_img, nickname, que_info_id,organ_id,answer_time,answer,is_delete,create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, q.getId(), q.getUserId(), q.getHeadImg(), q.getNickName(), q.getQueInfoId(),q.getOrganId(),q.getAnswerTime(),q.getAnswer(),q.getDelete(),now);
    }

    /**
     * 通过id编号删除文件用户答案
     * @param id
     * @return
     */
    public boolean delete(String id){
        final String sql = "UPDATE que_answer SET is_delete =true, update_time =? WHERE id =?";
        return jdbcTemplate.update(sql, DaoUtils.timestamp(new Date()), id) > 0;
    }

    /**
     * 通过id编号查询问卷用户答案
     * @param id
     * @return
     */
    public QueAnswer findOne(String id){
        final String sql = "SELECT * FROM que_answer WHERE id = ? AND is_delete = false";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }


    /**
     * 通过userId或问卷调查信息id查询问卷用户答案
     * @param userId
     * @param queInfoId
     * @return
     */
    public List<QueAnswer> findList(String userId,String queInfoId,int offset, int limit){
        StringBuilder sql = new StringBuilder(100);
        sql.append("SELECT * FROM que_answer ");
        buildWhere(sql,userId,queInfoId);
        sql.append("ORDER BY create_time DESC");
        Object[] params = DaoUtils.appendOffsetLimit(params(userId, queInfoId),offset,limit);
        return jdbcTemplate.query(sql.toString(), params,mapper);
    }

    /**
     * 通过userId和问卷调查信息编号查询条数
     * @param userId
     * @param queInfoId
     * @return
     */
    public Long count(String userId,String queInfoId){
        StringBuilder sql = new StringBuilder(100);
        sql.append("SELECT COUNT(id) FROM que_answer ");
        buildWhere(sql,userId,queInfoId);
        Object[] params = params(userId, queInfoId);
        return jdbcTemplate.queryForObject(sql.toString(),params,Long.class);
    }

    private void buildWhere(StringBuilder sql,String userId,String queInfoId){

        sql.append(" WHERE is_delete = false ");
        if (StringUtils.isNotBlank(userId)){
            sql.append(" AND user_id = ? ");
        }

        if (StringUtils.isNotBlank(queInfoId)){
            sql.append("AND que_info_id = ?");
        }
    }


    private Object[] params(String userId, String queInfoId){
        List<Object>  params = new ArrayList<>(2);

        if(StringUtils.isNotBlank(userId)){
            params.add(userId);
        }

        if(StringUtils.isNotBlank(queInfoId)){
            params.add(queInfoId);
        }

        return params.toArray();
    }
}
