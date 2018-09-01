package com.tuoshecx.server.cms.api.manage.questionnaire.form;

import com.tuoshecx.server.cms.questionnaire.domain.QueInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 新增调查问卷信息
 * @author LuJun
 */
@ApiModel(value = "新增调查问卷信息提交数据")
public class QueInfoSaveForm {

    @Size(max = 500)
    @ApiModelProperty(value = "问卷调查信息标题")
    private String title;
    @Size(max = 1000)
    @ApiModelProperty(value = "问卷调查信息内容")
    private String content;
    @NotBlank
    @Size(max = 32)
    @ApiModelProperty(value = "组织机构编号", required = true)
    private String organId;

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


    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public QueInfo toDomain(){
        QueInfo q= new QueInfo();
        q.setTitle(title);
        q.setContent(content);
        q.setOrganId(organId);
        return q;
    }
}
