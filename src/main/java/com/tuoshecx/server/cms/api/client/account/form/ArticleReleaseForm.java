package com.tuoshecx.server.cms.api.client.account.form;

import io.swagger.annotations.ApiModelProperty;

/**
 * 文章发布提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ArticleReleaseForm {
    @ApiModelProperty(value = "文章编号")
    private String articleId;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}
