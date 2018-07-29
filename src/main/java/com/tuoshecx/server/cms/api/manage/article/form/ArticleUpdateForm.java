package com.tuoshecx.server.cms.api.manage.article.form;

import com.tuoshecx.server.cms.article.domain.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 修改文章
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("修改文章")
public class ArticleUpdateForm extends ArticleSaveForm {
    @NotBlank
    @ApiModelProperty("编号")
    private String id;
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty("标题")
    private String title;
    @Size(max = 30)
    @ApiModelProperty("短标题")
    private String shortTitle;
    @Size(max = 50)
    @ApiModelProperty("子标题 ")
    private String subTitle;
    @Size(max = 20)
    @NotBlank
    @ApiModelProperty("作者")
    private String author;
    @ApiModelProperty("新闻来源")
    private String origin;
    @ApiModelProperty("关键字")
    private String[] keywords;
    @ApiModelProperty("文章分类")
    private String[] catalogs;
    @ApiModelProperty("文章标签")
    private String tag;
    @ApiModelProperty("摘要")
    private String summary;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("图片")
    private String image;
    @NotBlank
    @ApiModelProperty("显示模板")
    private String template = "default";
    @NotNull
    @ApiModelProperty("文章排序")
    private Integer showOrder = 9999;
    @NotNull
    @ApiModelProperty("是否顶置")
    private Boolean top = Boolean.FALSE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public String[] getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(String[] catalogs) {
        this.catalogs = catalogs;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Article toDomain(String siteId){
        Article t = new Article();

        t.setId(id);
        t.setSiteId(siteId);
        t.setTitle(title);
        t.setShortTitle(shortTitle);
        t.setSubTitle(subTitle);
        t.setAuthor(author);
        t.setOrigin(origin);
        t.setKeywords(keywords);
        t.setCatalogs(catalogs);
        t.setTag(tag);
        t.setSummary(summary);
        t.setContent(content);
        t.setImage(image);
        t.setTemplate(template);
        t.setShowOrder(showOrder);
        t.setComment(Boolean.FALSE);
        t.setTop(top);

        return t;
    }
}
