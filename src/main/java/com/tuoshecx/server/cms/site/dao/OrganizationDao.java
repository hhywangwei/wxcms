package com.tuoshecx.server.cms.site.dao;

import com.tuoshecx.server.cms.site.domain.Organization;
import com.tuoshecx.server.cms.common.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 组织机构数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class OrganizationDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Organization> mapper = (r, i) -> {
        Organization t = new Organization();

        t.setId(r.getString("id"));
        t.setSiteId(r.getString("site_id"));
        t.setName(r.getString("name"));
        t.setIcon(r.getString("icon"));
        t.setRemark(r.getString("remark"));
        t.setShowOrder(r.getInt("show_order"));
        t.setParentId(r.getString("parent_id"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public OrganizationDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Organization t){
        final String sql = "INSERT INTO base_organization (id, site_id, name, icon, show_order, remark, parent_id, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getSiteId(), t.getName(), t.getIcon(), t.getShowOrder(), t.getRemark(),
                t.getParentId(), DaoUtils.timestamp(new Date()));
    }

    public boolean update(Organization t){
        final String sql = "UPDATE base_organization SET name = ?, icon = ?, show_order = ?, remark = ?, parent_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getIcon(), t.getShowOrder(), t.getRemark(), t.getParentId(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM base_organization WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public Organization findOne(String id){
        final String sql = "SELECT * FROM base_organization WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean has(String id){
        final String sql= "SELECT COUNT(id) FROM base_organization WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

    public boolean hasChildren(String parentId){
        final String sql = "SELECT COUNT(id) FROM base_organization WHERE parent_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{parentId}, Integer.class);
        return count != null && count > 0;
    }

    public List<Organization> findChildren(String parentId){
        final String sql = "SELECT * FROM base_organization WHERE parent_id = ? ORDER BY  show_order ASC, create_time ASC";
        return jdbcTemplate.query(sql, new Object[]{parentId}, mapper);
    }
}
