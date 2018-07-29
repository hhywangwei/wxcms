package com.tuoshecx.server.cms.api.manage.account.form;

import com.tuoshecx.server.cms.site.domain.Manager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

/**
 * 修改站点管理员雇员信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("修改站点管理员信息")
public class ManageAccountUpdateForm {
    @Size(max = 20)
    @ApiModelProperty(value = "姓名")
    private String name;
    @Size(max = 200)
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @Size(max = 20)
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "权限角色")
    private String[] roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Manager toDomain(String id, String siteId){
        Manager t = new Manager();

        t.setId(id);
        t.setSiteId(siteId);
        t.setName(name);
        t.setHeadImg(icon);
        t.setPhone(phone);
        t.setRoles(roles);

        return t;
    }
}
