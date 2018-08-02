package com.tuoshecx.server.cms.api.manage.article.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文章置顶
 *
 * @author <a href="maito:hhywangwei@gmail.co ">WangWei</a>
 */
public class ArticleTopForm {
    @NotBlank
    @ApiModelProperty("文章编号")
    private String id;
    @NotNull
    @ApiModelProperty("true:置顶")
    private Boolean top = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }
}
