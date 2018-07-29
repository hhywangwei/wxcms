package com.tuoshecx.server.cms.api.manage.article.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 文章移动到其他频道
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ArticleMoveForm {
    @NotBlank
    @ApiModelProperty("文章编号")
    private String id;
    @NotBlank
    @ApiModelProperty("移动到文章频道")
    private String toChannelId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToChannelId() {
        return toChannelId;
    }

    public void setToChannelId(String toChannelId) {
        this.toChannelId = toChannelId;
    }
}
