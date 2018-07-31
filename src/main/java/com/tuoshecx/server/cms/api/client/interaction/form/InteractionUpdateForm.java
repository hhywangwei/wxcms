package com.tuoshecx.server.cms.api.client.interaction.form;

import com.tuoshecx.server.cms.interaction.domain.Interaction;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改政民互动
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class InteractionUpdateForm extends InteractionSaveForm {
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
    public Interaction toDomain(String userId) {
        Interaction t = super.toDomain(userId);

        t.setId(id);

        return t;
    }
}
