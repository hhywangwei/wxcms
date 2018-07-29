package com.tuoshecx.server.cms.api.manage.channel.form;

import com.tuoshecx.server.cms.channel.domain.Channel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ChannelUpdateForm {
    @ApiModelProperty("编号")
    private String id;
    @NotBlank
    @ApiModelProperty("频道名称")
    private String name;
    @ApiModelProperty(value = "频道图标")
    private String icon;
    @NotBlank
    @ApiModelProperty(value = "频道模板")
    private String template = "default";
    @NotNull
    @ApiModelProperty(value = "显示顺序，按照正序显示")
    private Integer showOrder = 9999;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public Channel toDomain(String siteId){
        Channel t = new Channel();

        t.setId(id);
        t.setSiteId(siteId);
        t.setName(name);
        t.setIcon(icon);
        t.setShowOrder(showOrder);
        t.setTemplate(template);

        return t;
    }
}
