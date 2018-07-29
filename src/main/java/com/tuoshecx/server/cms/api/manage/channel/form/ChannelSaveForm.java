package com.tuoshecx.server.cms.api.manage.channel.form;

import com.tuoshecx.server.cms.channel.domain.Channel;
import io.netty.channel.ChannelFutureListener;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增频道
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("新增频道")
public class ChannelSaveForm {
    @ApiModelProperty("上级频道编号")
    private String parentId;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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
        Channel t= new Channel();

        t.setSiteId(siteId);
        t.setName(name);
        t.setIcon(icon);
        t.setParentId(parentId);
        t.setShowOrder(showOrder);
        t.setTemplate(template);

        return t;
    }
}
