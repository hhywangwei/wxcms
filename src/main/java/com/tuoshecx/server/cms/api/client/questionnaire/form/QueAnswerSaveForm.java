package com.tuoshecx.server.cms.api.client.questionnaire.form;

import com.tuoshecx.server.cms.questionnaire.domain.QueAnswer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.spring.web.json.Json;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 新增调查问卷答案
 * @author LuJun
 */
@ApiModel(value = "新增调查问卷答案提交数据")
public class QueAnswerSaveForm {

    @NotBlank
    @Size(max = 32)
    @ApiModelProperty(value = "问卷调查表编号")
    private String queInfoId;
    @NotBlank
    @Size(max = 32)
    @ApiModelProperty(value = "组织机构编号")
    private String organId;
    @ApiModelProperty(value = "问卷答案答题时间")
    private String answerTime;
    @NotBlank
    @Size(max = 1000)
    @ApiModelProperty(value = "问卷答案")
    private String answer;

    public String getQueInfoId() {
        return queInfoId;
    }

    public void setQueInfoId(String queInfoId) {
        this.queInfoId = queInfoId;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public QueAnswer toDomain(String userId){
        QueAnswer q = new QueAnswer();
        q.setUserId(userId);
        q.setOrganId(organId);
        q.setQueInfoId(queInfoId);
        q.setAnswerTime(answerTime);
        q.setAnswer(answer);
        return q;
    }
}
