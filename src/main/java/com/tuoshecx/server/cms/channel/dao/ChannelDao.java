package com.tuoshecx.server.cms.channel.dao;

import com.tuoshecx.server.cms.channel.domain.Channel;
import com.tuoshecx.server.cms.common.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

@Repository
public class ChannelDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Channel> mapper = (r, i) -> {
        Channel t = new Channel();

        t.setId(r.getString("id"));
        t.setParentId(r.getString("parent_id"));
        t.setSiteId(r.getString("site_id"));
        t.setName(r.getString("name"));
        t.setIcon(r.getString("icon"));
        t.setTemplate(r.getString("template"));
        t.setState(Channel.State.valueOf(r.getString("state")));
        t.setShowOrder(r.getInt("show_order"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ChannelDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Channel t){
        final Date now = new Date();
        final String sql = "INSERT INTO site_channel (id, parent_id, site_id, name, icon, template, state, show_order, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getParentId(), t.getSiteId(), t.getName(), t.getIcon(), t.getTemplate(),
                Channel.State.WAIT.name(), t.getShowOrder(), now, now);
    }

    public boolean update(Channel t){
        final String sql = "UPDATE site_channel SET name = ?, icon = ?, template = ?, show_order = ?, parent_id = ?, update_time = ? " +
                "WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, t.getName(), t.getIcon(), t.getTemplate(), t.getShowOrder(), t.getParentId(),
                DaoUtils.timestamp(new Date()), t.getId()) > 0;
    }

    public boolean updateState(String id, Channel.State state){
        final String sql = "UPDATE site_channel SET state = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, state.name(), id) > 0;
    }

    public boolean delete(String id){
        final String sql = "UPDATE site_channel SET is_delete = ? WHERE id = ?";
        return jdbcTemplate.update(sql, true, id) > 0;
    }

    public Channel findOne(String id){
        final String sql = "SELECT * FROM site_channel WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean has(String id){
        final String sql = "SELECT COUNT(id) FROM site_channel WHERE id = ? AND is_delete = false";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

    public boolean hasChildren(String parentId){
        final String sql = "SELECT COUNT(id) FROM site_channel WHERE parent_id = ? AND is_delete = false";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{parentId}, Integer.class);
        return count != null && count > 0;
    }

    public List<Channel> findChildren(String parentId, Channel.State state){
        final String sql = "SELECT * FROM site_channel WHERE parent_id = ? AND state LIKE ? Order By show_order ASC, create_time ASC";
        String stateLike = state == null? "%" : state.name();
        return jdbcTemplate.query(sql, new Object[]{parentId, stateLike}, mapper);
    }
}
