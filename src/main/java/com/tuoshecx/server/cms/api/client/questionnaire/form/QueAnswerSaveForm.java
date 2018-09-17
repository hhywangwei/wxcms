package com.tuoshecx.server.cms.api.client.questionnaire.form;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.base.domain.Sys;
import com.tuoshecx.server.cms.questionnaire.domain.QueAnswer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.spring.web.json.Json;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

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
    @ApiModelProperty(value = "问卷答案答题时间")
    private String answerTime;
    @NotNull
    @Size(max = 1000)
    @ApiModelProperty(value = "问卷答案")
    private Map<String,String> answer;

    public String getQueInfoId() {
        return queInfoId;
    }

    public void setQueInfoId(String queInfoId) {
        this.queInfoId = queInfoId;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public Map<String, String> getAnswer() {
        return answer;
    }

    public void setAnswer(Map<String, String> answer) {
        this.answer = answer;
    }

    public QueAnswer toDomain(String organId){
        QueAnswer q = new QueAnswer();
        q.setOrganId(organId);
        q.setQueInfoId(queInfoId);
        q.setAnswerTime(answerTime);
        try {
            ObjectMapper map = new ObjectMapper();
            String answerJson = map.writeValueAsString(answer);
            q.setAnswer(answerJson);
        }catch (Exception e){
            new BaseException("答案类型转换失败");
        }
        q.setDelete(Boolean.FALSE);
        return q;
    }
}
