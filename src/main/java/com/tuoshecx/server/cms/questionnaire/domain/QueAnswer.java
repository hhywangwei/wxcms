package com.tuoshecx.server.cms.questionnaire.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 问卷答题实体类
 * @author LuJun
 */
@ApiModel("问卷答题")
public class QueAnswer {

    @ApiModelProperty(value = "问卷答案编号", required = true)
    private String id;
    @ApiModelProperty(value = "问卷答案用户编号", required = true)
    private String userId;
    @ApiModelProperty(value = "问卷答案用户头像")
    private String headImg;
    @ApiModelProperty(value = "问卷答案用户头像")
    private String nickName;
    @ApiModelProperty(value = "问卷调查表编号", required = true)
    private String queInfoId;
    @ApiModelProperty(value = "组织机构编号", required = true)
    private String organId;
    @ApiModelProperty(value = "问卷答案答题时间")
    private String answerTime;
    @ApiModelProperty(value = "问卷答案")
    private String answer;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
    @ApiModelProperty(value = "true:删除,false:未删除", required = true)
    private Boolean isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueAnswer)) return false;
        QueAnswer queAnswer = (QueAnswer) o;
        return Objects.equals(id, queAnswer.id) &&
                Objects.equals(userId, queAnswer.userId) &&
                Objects.equals(headImg, queAnswer.headImg) &&
                Objects.equals(nickName, queAnswer.nickName) &&
                Objects.equals(queInfoId, queAnswer.queInfoId) &&
                Objects.equals(organId, queAnswer.organId) &&
                Objects.equals(answerTime, queAnswer.answerTime) &&
                Objects.equals(answer, queAnswer.answer) &&
                Objects.equals(createTime, queAnswer.createTime) &&
                Objects.equals(isDelete, queAnswer.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, headImg, nickName, queInfoId, organId, answerTime, answer, createTime, isDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userId", userId)
                .append("headImg", headImg)
                .append("nickName", nickName)
                .append("queInfoId", queInfoId)
                .append("organId", organId)
                .append("answerTime", answerTime)
                .append("answer", answer)
                .append("createTime", createTime)
                .append("isDelete", isDelete)
                .toString();
    }
}
