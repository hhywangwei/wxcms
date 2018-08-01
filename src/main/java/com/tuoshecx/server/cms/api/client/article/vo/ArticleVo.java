package com.tuoshecx.server.cms.api.client.article.vo;

import com.tuoshecx.server.cms.article.domain.Article;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文章输出对象
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ArticleVo {
    @ApiModelProperty("文章")
    private final Article article;
    @ApiModelProperty("阅读数")
    private final Integer readCount;
    @ApiModelProperty("点赞数")
    private final Integer goodCount;

    public ArticleVo(Article article, Integer readCount, Integer goodCount) {
        this.article = article;
        this.readCount = readCount;
        this.goodCount = goodCount;
    }

    public Article getArticle() {
        return article;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public Integer getGoodCount() {
        return goodCount;
    }
}
