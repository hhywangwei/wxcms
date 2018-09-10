package com.tuoshecx.server.cms.questionnaire.dao;

import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.cms.questionnaire.domain.QueProject;
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
 * 问卷调查项目dao
 * @author LuJun
 */
@Repository
public class QueProjectDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public QueProjectDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<QueProject> mapper = (r, i) -> {
        QueProject q = new QueProject();

        q.setId(r.getString("id"));
        q.setTitle(r.getString("title"));
        q.setContent(r.getString("content"));
        q.setQueInfoId(r.getString("que_info_id"));
        q.setType(r.getString("type"));
        q.setCreateUser(r.getString("create_user"));
        q.setCreateTime(r.getTimestamp("create_time"));
        q.setUpdateUser(r.getString("update_user"));
        q.setUpdateTime(r.getTimestamp("update_time"));

        return q;
    };

    /**
     * 新建问卷调查项目
     * @param q
     */
    public void insert(QueProject q){
        final Date now = new Date();
        final String sql = "INSERT INTO que_project (id, title, content, que_info_id, type, create_user, update_user," +
                "create_time,update_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, q.getId(), q.getTitle(), q.getContent(), q.getQueInfoId(), q.getType(), q.getCreateUser(),
                q.getUpdateUser(), now, now);
    }

    /**
     * 修改问卷调查项目
     * @param q
     * @return
     */
    public boolean update(QueProject q){
        final String sql = "UPDATE que_project SET title = ?, content = ?, que_info_id = ?, type = ?, update_user = ?,update_time = ? WHERE id = ?";
        return jdbcTemplate.update(sql, q.getTitle(), q.getContent(), q.getQueInfoId(), q.getType(), q.getUpdateUser(),
                DaoUtils.timestamp(new Date()), q.getId()) > 0;
    }

    /**
     * 通过id编号查询问卷调查项目
     * @param id
     * @return
     */
    public QueProject findOne(String id){
        final String sql = "SELECT * FROM que_project WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }


    /**
     * 通过问卷调查信息id编号,标题,项目类型查询问卷调查项目集合(分页查询)
     * 按题目类型升序(1:单选，2：多选，3：是非)和创建时间降序
     * @param queInfoId
     * @return
     */
    public List<QueProject> findListByInfoIdAndType(String queInfoId,String title,String type,int offset, int limit){
        StringBuilder sql = new StringBuilder(100);
        sql.append("SELECT * FROM que_project WHERE que_info_id = ? ");
        buildWhere(sql,title,type);
        sql.append(" ORDER BY type ASC,create_time DESC LIMIT ? OFFSET ?");
        Object[] params = DaoUtils.appendOffsetLimit(params(queInfoId,title,type),offset,limit);
        return jdbcTemplate.query(sql.toString(),params,mapper);
    }

    /**
     * 通过问卷调查信息id编号和项目类型查询条数
     * @param queInfoId
     * @param type
     * @return
     */
    public Long count(String queInfoId,String title,String type){
        StringBuilder sql = new StringBuilder(100);
        sql.append("SELECT COUNT(id) FROM que_project WHERE que_info_id = ?");
        buildWhere(sql,title,type);
        Object[] params = params(queInfoId,title,type);
        return jdbcTemplate.queryForObject(sql.toString(),params,Long.class);
    }

    private void buildWhere(StringBuilder sql,String title,String type){


        if (StringUtils.isNotBlank(title)){
            sql.append(" AND title LIKE ? ");
        }

        if (StringUtils.isNotBlank(type)){
            sql.append(" AND type = ?");
        }
    }


    private Object[] params(String queInfoId,String title ,String type){
        List<Object>  params = new ArrayList<>(2);
        params.add(queInfoId);

        if(StringUtils.isNotBlank(title)){
            params.add(title);
        }

        if(StringUtils.isNotBlank(type)){
            params.add(type);
        }

        return params.toArray();
    }

}
