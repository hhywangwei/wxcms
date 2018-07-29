package com.tuoshecx.server.cms.api.manage.article.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 拷贝文章
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("拷贝文章")
public class ArticleCopyForm {
    @NotBlank
    @ApiModelProperty("文章编号")
    private String id;
    @NotBlank
    @ApiModelProperty("拷贝到文章频道")
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
