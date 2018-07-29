package com.tuoshecx.server.cms.site.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 站点信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("站点信息")
public class Site {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "站点名称", required = true)
    private String name;
    @ApiModelProperty(value = "联系电话", required = true)
    private String phone;
    @ApiModelProperty(value = "联系人", required = true)
    private String contact;
    @ApiModelProperty(value = "省份", required = true)
    private String province;
    @ApiModelProperty(value = "省份名称")
    private String provinceName;
    @ApiModelProperty(value = "城市", required = true)
    private String city;
    @ApiModelProperty(value = "城市名称")
    private String cityName;
    @ApiModelProperty(value = "县", required = true)
    private String county;
    @ApiModelProperty(value = "县名称")
    private String countyName;
    @ApiModelProperty(value = "所在地址", required = true)
    private String address;
    @ApiModelProperty(value = "地理位置")
    private String[] locations;
    @ApiModelProperty(value = "站点图标")
    private String icon;
    @ApiModelProperty(value = "显示图片")
    private String[] images;
    @ApiModelProperty(value = "站点摘要")
    private String summary;
    @ApiModelProperty(value = "站点描述")
    private String detail;
    @ApiModelProperty(value = "站点状态", required = true)
    private State state;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
    @ApiModelProperty(value = "修改时间", required = true)
    private Date updateTime;

    public enum State{
        WAIT, OPEN, CLOSE
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getLocations() {
        return locations;
    }

    public void setLocations(String[] locations) {
        this.locations = locations;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Site)) return false;
        Site shop = (Site) o;
        return Objects.equals(id, shop.id) &&
                Objects.equals(name, shop.name) &&
                Objects.equals(phone, shop.phone) &&
                Objects.equals(contact, shop.contact) &&
                Objects.equals(province, shop.province) &&
                Objects.equals(provinceName, shop.provinceName) &&
                Objects.equals(city, shop.city) &&
                Objects.equals(cityName, shop.cityName) &&
                Objects.equals(county, shop.county) &&
                Objects.equals(countyName, shop.countyName) &&
                Objects.equals(address, shop.address) &&
                Arrays.equals(locations, shop.locations) &&
                Objects.equals(icon, shop.icon) &&
                Arrays.equals(images, shop.images) &&
                Objects.equals(summary, shop.summary) &&
                Objects.equals(detail, shop.detail) &&
                state == shop.state &&
                Objects.equals(createTime, shop.createTime) &&
                Objects.equals(updateTime, shop.updateTime);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, name, phone, contact, province, provinceName, city, cityName, county, countyName, address, icon, summary, detail, state, createTime, updateTime);
        result = 31 * result + Arrays.hashCode(locations);
        result = 31 * result + Arrays.hashCode(images);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("phone", phone)
                .append("contact", contact)
                .append("province", province)
                .append("provinceName", provinceName)
                .append("city", city)
                .append("cityName", cityName)
                .append("county", county)
                .append("countyName", countyName)
                .append("address", address)
                .append("locations", locations)
                .append("icon", icon)
                .append("images", images)
                .append("summary", summary)
                .append("detail", detail)
                .append("state", state)
                .append("createTime", createTime)
                .append("updateTime", updateTime)
                .toString();
    }
}
