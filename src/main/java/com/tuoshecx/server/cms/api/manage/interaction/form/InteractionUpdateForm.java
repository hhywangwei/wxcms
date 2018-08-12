package com.tuoshecx.server.cms.api.manage.interaction.form;

import com.tuoshecx.server.cms.interaction.domain.Interaction;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 修改政民互动
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class InteractionUpdateForm {
    @NotBlank
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty("互动机构编号")
    private String organId;
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty("互动标题")
    private String title;
    @NotBlank
    @Pattern(regexp = "^CONSULT|COMPLAINT|SUGGEST$")
    @ApiModelProperty("互动类型")
    private String type;                  //兼容VUE处理
    @NotBlank
    @Size(max = 2000)
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("互动图片")
    private String[] images;
    @ApiModelProperty("是否置顶")
    private Boolean top = Boolean.FALSE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Interaction toDomain(String userId) {
        Interaction t = new Interaction();

        t.setId(id);
        t.setUserId(userId);
        t.setOrganId(organId);
        t.setTitle(title);
        t.setType(Interaction.Type.valueOf(type));
        t.setContent(content);
        t.setImages(images);
        t.setTop(top);

        return t;
    }
}
