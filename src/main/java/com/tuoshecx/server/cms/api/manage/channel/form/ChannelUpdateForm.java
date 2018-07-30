package com.tuoshecx.server.cms.api.manage.channel.form;

import com.tuoshecx.server.cms.channel.domain.Channel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改频道
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ChannelUpdateForm extends ChannelSaveForm {
    @NotBlank
    @ApiModelProperty("编号")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Channel toDomain(String siteId){
        Channel t = super.toDomain(siteId);
        t.setId(id);
        return t;
    }
}
