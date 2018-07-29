package com.tuoshecx.server.cms.api.sys.site.form;

import com.tuoshecx.server.cms.site.domain.Site;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class SiteUpdateForm {
    @NotBlank
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @NotBlank
    @ApiModelProperty(value = "站点名称", required = true)
    private String name;
    @NotBlank
    @ApiModelProperty(value = "联系电话", required = true)
    private String phone;
    @NotBlank
    @ApiModelProperty(value = "联系人", required = true)
    private String contact;
    @NotBlank
    @ApiModelProperty(value = "省份", required = true)
    private String province;
    @ApiModelProperty(value = "省份名称")
    private String provinceName;
    @NotBlank
    @ApiModelProperty(value = "城市", required = true)
    private String city;
    @ApiModelProperty(value = "城市名称")
    private String cityName;
    @NotBlank
    @ApiModelProperty(value = "县", required = true)
    private String county;
    @ApiModelProperty(value = "县名称")
    private String countyName;
    @NotBlank
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

    public Site toDomain(){
        Site t = new Site();

        t.setId(id);
        t.setName(name);
        t.setPhone(phone);
        t.setContact(contact);
        t.setProvince(province);
        t.setProvinceName(provinceName);
        t.setCity(city);
        t.setCityName(cityName);
        t.setCounty(county);
        t.setCountyName(countyName);
        t.setAddress(address);
        t.setLocations(locations);
        t.setIcon(icon);
        t.setImages(images);
        t.setSummary(summary);
        t.setDetail(detail);
        return t;
    }
}
