package com.tuoshecx.server.cms.api.manage.questionnaire.form;

import com.tuoshecx.server.cms.questionnaire.domain.QueProject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 更新调查问卷项目
 * @author LuJun
 */
@ApiModel(value = "更新调查问卷项目提交数据")
public class QueProjectUpdateForm {

    @NotBlank
    @Size(max = 32)
    @ApiModelProperty(value = "问卷项目编号")
    private String id;
    @Size(max = 500)
    @ApiModelProperty(value = "问卷项目标题")
    private String title;
    @Size(max = 1000)
    @ApiModelProperty(value = "问卷项目类容")
    private String content;
    @NotBlank
    @Size(max = 32)
    @ApiModelProperty(value = "问卷调查表编号")
    private String queInfoId;
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "项目类型")
    private String type;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQueInfoId() {
        return queInfoId;
    }

    public void setQueInfoId(String queInfoId) {
        this.queInfoId = queInfoId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public QueProject toDomain(){
        QueProject q = new QueProject();
        q.setId(id);
        q.setTitle(title);
        q.setContent(content);
        q.setQueInfoId(queInfoId);
        q.setType(type);
        return q;
    }
}
