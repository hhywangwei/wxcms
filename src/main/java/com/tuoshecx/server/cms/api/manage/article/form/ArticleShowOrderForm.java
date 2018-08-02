package com.tuoshecx.server.cms.api.manage.article.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文章显示排序
 *
 * @author <a href="mailto:hhywangwei@gmail.com"></a>
 */
public class ArticleShowOrderForm {
    @NotBlank
    @ApiModelProperty("文章编号")
    private String id;
    @NotNull
    @Min(0)
    @ApiModelProperty("排序顺序")
    private Integer showOrder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

}
