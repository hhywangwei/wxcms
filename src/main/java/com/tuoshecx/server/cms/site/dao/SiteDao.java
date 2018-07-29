package com.tuoshecx.server.cms.site.dao;

import com.tuoshecx.server.cms.common.utils.DaoUtils;
import com.tuoshecx.server.cms.site.domain.Site;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 站点数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SiteDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Site> mapper =(r, i) -> {
        Site t = new Site();

        t.setId(r.getString("id"));
        t.setName(r.getString("name"));
        t.setPhone(r.getString("phone"));
        t.setContact(r.getString("contact"));
        t.setProvince(r.getString("province"));
        t.setProvinceName(r.getString("province_name"));
        t.setCity(r.getString("city"));
        t.setCityName(r.getString("city_name"));
        t.setCounty(r.getString("county"));
        t.setCountyName(r.getString("county_name"));
        t.setAddress(r.getString("address"));
        t.setLocations(DaoUtils.toArray(r.getString("location")));
        t.setIcon(r.getString("icon"));
        t.setImages(DaoUtils.toArray(r.getString("images")));
        t.setSummary(r.getString("summary"));
        t.setDetail(r.getString("detail"));
        t.setState(Site.State.valueOf(r.getString("state")));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public SiteDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Site t){
        final String sql = "INSERT INTO site_info " +
                "(id, name, phone, contact, province, province_name, city, city_name, county, county_name, address, " +
                "location, icon, images, summary, detail, state, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        final Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getName(), t.getPhone(), t.getContact(), t.getProvince(), t.getProvinceName(),
                t.getCity(),  t.getCityName(), t.getCounty(), t.getCountyName(), t.getAddress(), DaoUtils.join(t.getLocations()),
                t.getIcon(), DaoUtils.join(t.getImages()), StringUtils.defaultString(t.getSummary()),
                StringUtils.defaultString(t.getDetail()), t.getState().name(), DaoUtils.timestamp(now), DaoUtils.timestamp(now));
    }

    public boolean update(Site t){
        final String sql = "UPDATE site_info " +
                "SET name =?, phone =?, contact =?, province =?, province_name = ?, city =?, city_name = ?, county = ?, county_name = ?, " +
                "address =?, location =?, icon =?, images =?, summary = ?, detail =?, update_time =? WHERE id =?";
        return jdbcTemplate.update(sql, t.getName(), t.getPhone(), t.getContact(), t.getProvince(), t.getProvinceName(),
                t.getCity(), t.getCityName(), t.getCounty(), t.getCountyName(), t.getAddress(), DaoUtils.join(t.getLocations()),
                t.getIcon(), DaoUtils.join(t.getImages()), StringUtils.defaultString(t.getSummary()),
                StringUtils.defaultString(t.getDetail()), DaoUtils.timestamp(new Date()), t.getId()) > 0;
    }

    public Site findOne(String id){
        final String sql = "SELECT * FROM site_info WHERE id =? AND is_delete =false";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean delete(String id){
        final String sql = "UPDATE site_info SET is_delete =true, update_time =? WHERE id =?";
        return jdbcTemplate.update(sql, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public boolean updateState(String id, Site.State state){
        final String sql = "UPDATE site_info SET state =?, update_time =? WHERE id =?";
        return jdbcTemplate.update(sql, state.name(), DaoUtils.timestamp(new Date()), id) > 0;
    }

    public Long count(String name, String phone){
        final String sql = "SELECT COUNT(id) FROM site_info WHERE is_delete =false AND name LIKE ? AND phone LIKE ?";

        return jdbcTemplate.queryForObject(sql, buildParameters(name, phone), Long.class);
    }

    private Object[] buildParameters(String name, String phone){
        return new Object[]{DaoUtils.blankLike(name), DaoUtils.blankLike(phone)};
    }

    public List<Site> find(String name, String phone, int offset, int limit){

        final String sql = "SELECT * FROM site_info " +
                "WHERE is_delete =false AND name LIKE ? AND phone LIKE ? ORDER BY create_time DESC LIMIT ? OFFSET ?";

        Object[] params = DaoUtils.appendOffsetLimit(buildParameters(name, phone), offset, limit);
        return jdbcTemplate.query(sql, params, mapper);
    }
}

