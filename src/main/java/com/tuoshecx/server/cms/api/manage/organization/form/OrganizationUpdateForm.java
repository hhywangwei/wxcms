package com.tuoshecx.server.cms.api.manage.organization.form;

import com.tuoshecx.server.cms.site.domain.Organization;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改组织机构
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class OrganizationUpdateForm extends OrganizationSaveForm{
    @NotBlank
    @ApiModelProperty(value = "编号", required = true)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Organization toDomain(String siteId) {
        Organization t = super.toDomain(siteId);
        t.setId(id);

        return t;
    }
}
