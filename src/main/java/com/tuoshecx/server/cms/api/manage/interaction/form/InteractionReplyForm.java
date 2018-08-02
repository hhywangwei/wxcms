package com.tuoshecx.server.cms.api.manage.interaction.form;

import io.swagger.annotations.ApiModelProperty;

/**
 * 回复政民互动
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class InteractionReplyForm {
    @ApiModelProperty("互动编号")
    private String id;
    @ApiModelProperty("回复")
    private String reply;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
