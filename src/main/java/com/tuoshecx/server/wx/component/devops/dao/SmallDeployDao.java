package com.tuoshecx.server.wx.component.devops.dao;

import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.wx.component.devops.domain.SmallDeploy;
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
 * 小程序发布信息数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SmallDeployDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SmallDeploy> mapper = (r, i) -> {
        SmallDeploy t = new SmallDeploy();

        t.setId(r.getString("id"));
        t.setSiteId(r.getString("site_id"));
        t.setAppid(r.getString("appid"));
        t.setSetDomain(r.getBoolean("is_set_domain"));
        t.setTemplateId(r.getInt("template_id"));
        t.setState(r.getString("state"));
        t.setAuditId(r.getInt("audit_id"));
        t.setRemark(r.getString("remark"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public SmallDeployDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SmallDeploy t){
        final String sql = "INSERT INTO wx_small_deploy (id, site_id, appid, is_set_domain, template_id, state, remark, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getSiteId(), t.getAppid(), t.getSetDomain(), t.getTemplateId(), t.getState(),
                StringUtils.defaultString(t.getRemark(), ""), DaoUtils.timestamp(now), DaoUtils.timestamp(now));
    }

    public boolean setDomain(String id, boolean isSetting){
        final String sql = "UPDATE wx_small_deploy SET is_set_domain = ? WHERE id = ?";
        return jdbcTemplate.update(sql, isSetting, id) > 0;
    }

    public boolean updateState(String id, String state, String remark){
        final String sql = "UPDATE wx_small_deploy SET state = ?, remark = ? WHERE id = ?";
        return jdbcTemplate.update(sql, state, remark, id) > 0;
    }

    public boolean updateAuditId(String id, Integer auditId){
        final String sql = "UPDATE wx_small_deploy SET audit_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql, auditId, id) > 0;
    }

    public SmallDeploy findOne(String id){
        final String sql = "SELECT * FROM wx_small_deploy WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public SmallDeploy findOne(String appid, Integer templateId){
        final String sql = "SELECT * FROM wx_small_deploy WHERE appid = ? AND template_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{appid, templateId}, mapper);
    }

    public boolean hasTemplateId(String appid, Integer templateId){
        final String sql = "SELECT COUNT(id) FROM wx_small_deploy WHERE appid = ? AND template_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{appid, templateId}, Integer.class);
        return count != null && count > 0;
    }

    public List<String> findAudit(){
        final String sql = "SELECT id FROM wx_small_deploy WHERE state = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{"AUDIT"}, String.class);
    }

    public Long count(String siteId, String appid, Integer templateId, String state){
        StringBuilder sql = new StringBuilder(200);
        sql.append("SELECT COUNT(id) FROM wx_small_deploy ");
        buildWhere(sql, siteId, appid, templateId, state);

        return jdbcTemplate.queryForObject(sql.toString(), params(siteId, appid, templateId, state), Long.class);
    }

    private void buildWhere(StringBuilder sql, String siteId, String appid, Integer templateId, String state){
        sql.append(" WHERE 1 = 1 ");
        if(StringUtils.isNotBlank(siteId)){
            sql.append(" AND　site_id = ? ");
        }
        if(StringUtils.isNotBlank(appid)){
            sql.append(" AND appid = ? ");
        }
        if(templateId != null){
            sql.append(" AND template_id = ? ");
        }
        if(StringUtils.isNotBlank(state)){
            sql.append(" AND state = ? ");
        }
    }

    private Object[] params(String siteId, String appid, Integer templateId, String state){
        List<Object> params = new ArrayList<>();
        if(StringUtils.isNotBlank(siteId)){
            params.add(siteId);
        }
        if(StringUtils.isNotBlank(appid)){
            params.add(appid);
        }
        if(templateId != null){
            params.add(templateId);
        }
        if(StringUtils.isNotBlank(state)){
            params.add(state);
        }
        return params.toArray();
    }

    public List<SmallDeploy> find(String siteId, String appid, Integer templateId, String state, int offset, int limit){
        StringBuilder sql = new StringBuilder(200);
        sql.append("SELECT * FROM wx_small_deploy ");
        buildWhere(sql, siteId, appid, templateId, state);
        sql.append(" ORDER BY update_time DESC LIMIT ? OFFSET ?");
        Object[] params = DaoUtils.appendOffsetLimit(params(siteId, appid, templateId, state), offset, limit);
        return jdbcTemplate.query(sql.toString(), params, mapper);
    }
}
