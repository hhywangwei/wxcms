package com.tuoshecx.server.cms.api.client.interaction.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 政民互动评论
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class InteractionCommentForm {
    @NotBlank
    @ApiModelProperty(value = "互动编号", required = true)
    private String id;
    @ApiModelProperty(value = "上传图片")
    private String[] images;
    @NotBlank
    @ApiModelProperty(value = "评论内容")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
