package com.tuoshecx.server.cms.questionnaire.dao;

import com.tuoshecx.server.cms.base.domain.Sys;
import com.tuoshecx.server.cms.channel.domain.Channel;
import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.cms.questionnaire.domain.QueInfo;
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
 * 问卷调查信息dao
 * @author LuJun
 */
@Repository
public class QueInfoDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public QueInfoDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<QueInfo> mapper = (r, i) -> {
        QueInfo q = new QueInfo();

        q.setId(r.getString("id"));
        q.setTitle(r.getString("title"));
        q.setContent(r.getString("content"));
        q.setOrganId(r.getString("organ_id"));
        q.setState(QueInfo.State.valueOf(r.getString("state")));
        q.setDelete(r.getBoolean("is_delete"));
        q.setCreateUser(r.getString("create_user"));
        q.setCreateTime(r.getTimestamp("create_time"));
        q.setUpdateUser(r.getString("update_user"));
        q.setUpdateTime(r.getTimestamp("update_time"));

        return q;
    };


    /**
     * 新建问卷调查信息
     * @param q
     */
    public void insert(QueInfo q){
        final Date now = new Date();
        final String sql = "INSERT INTO que_info (id, title, content, state, organ_id,is_delete, create_user, update_user," +
                "create_time,update_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, q.getId(), q.getTitle(), q.getContent(), QueInfo.State.WAIT.name(), q.getOrganId(),q.getDelete() ,q.getCreateUser(),
                q.getUpdateUser(), now, now);
    }

    /**
     * 修改问卷调查信息
     * @param q
     * @return
     */
    public boolean update(QueInfo q){
        final String sql = "UPDATE que_info SET title = ?, content = ?,organ_id = ? ,update_user = ?,update_time = ? WHERE id = ?";
        return jdbcTemplate.update(sql, q.getTitle(), q.getContent(), q.getOrganId(), q.getUpdateUser(),
                DaoUtils.timestamp(new Date()), q.getId()) > 0;
    }

    /**
     * 删除问卷调查信息
     * @param id
     * @return
     */
    public boolean delete(String id){
        final String sql = "UPDATE que_info SET is_delete =true, update_time =? WHERE id =?";
        return jdbcTemplate.update(sql, DaoUtils.timestamp(new Date()), id) > 0;
    }

    /**
     * 修改问卷调查信息状态
     * @param id
     * @param state
     * @return
     */
    public boolean updateState(String id,QueInfo.State state,String updateUser){
        final String sql = "UPDATE que_info SET state = ? ,update_user = ?,update_time = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, state.name(),updateUser,DaoUtils.timestamp(new Date()), id) > 0;
    }

    /**
     * 通过id编号查询问卷调查信息
     * @param id
     * @return
     */
    public QueInfo findOne(String id){
        final String sql = "SELECT * FROM que_info WHERE id = ? AND is_delete = false";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }


    /**
     * 通过组织机构id编号查询问卷调查信息集合(分页查询)
     * @param organId
     * @return
     */
    public List<QueInfo> findListByOrganId(String organId,String title,QueInfo.State state ,int offset, int limit){
        StringBuilder sql = new StringBuilder(100);
        sql.append("SELECT * FROM que_info");
        buildWhere(sql,organId,title,state);
        sql.append("ORDER BY create_time DESC LIMIT ? OFFSET ?");
        Object[] params = DaoUtils.appendOffsetLimit(params(organId,title,state),offset,limit);
        return jdbcTemplate.query(sql.toString(),params,mapper);
    }

    /**
     * 通过组织机构编号和状态查询条数
     * @param organId
     * @param state
     * @return
     */
    public Long count(String organId,String title,QueInfo.State state){
        StringBuilder sql = new StringBuilder(100);
        sql.append("SELECT COUNT(id) FROM que_info ");
        buildWhere(sql,organId,title,state);
        Object[] params = params(organId,title,state);
        return jdbcTemplate.queryForObject(sql.toString(),params,Long.class);
    }

    private void buildWhere(StringBuilder sql,String organId,String title,QueInfo.State state){

        sql.append(" WHERE is_delete = false");

        if (StringUtils.isNotBlank(organId)){
            sql.append(" AND organ_id = ?");
        }

        if (StringUtils.isNotBlank(title)){
            sql.append(" AND title LIKE ? ");
        }

        if (state != null){
            sql.append(" AND state = ?");
        }
    }


    private Object[] params(String organId,String title, QueInfo.State state){
        List<Object>  params = new ArrayList<>(3);

        if (StringUtils.isNotBlank(organId)){
            params.add(organId);
        }

        if(StringUtils.isNotBlank(title)){
            params.add(title);
        }

        if(state != null){
            params.add(state.name());
        }

        return params.toArray();
    }
}
