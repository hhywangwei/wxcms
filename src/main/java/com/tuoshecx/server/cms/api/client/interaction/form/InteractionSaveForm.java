package com.tuoshecx.server.cms.api.client.interaction.form;

import com.tuoshecx.server.cms.interaction.domain.Interaction;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 新增政民互动
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class InteractionSaveForm {
    @ApiModelProperty("互动机构编号")
    private String organId;
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty("互动标题")
    private String title;
    @NotBlank
    @Pattern(regexp = "^CONSULT|COMPLAINT|SUGGEST$")
    @ApiModelProperty("互动类型")
    private String type;
    @NotBlank
    @Size(max = 2000)
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("互动图片")
    private String[] images;
    @NotNull
    @ApiModelProperty("是否公开互动")
    private Boolean open = true;
    @NotBlank
    @ApiModelProperty("微信小程序form_id")
    private String formId;

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

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Interaction toDomain(String userId){
        Interaction t = new Interaction();

        t.setUserId(userId);
        t.setOrganId(organId);
        t.setTitle(title);
        t.setType(Interaction.Type.valueOf(type));
        t.setContent(content);
        t.setImages(images);
        t.setOpen(open);
        t.setFormId(formId);

        return t;
    }
}
