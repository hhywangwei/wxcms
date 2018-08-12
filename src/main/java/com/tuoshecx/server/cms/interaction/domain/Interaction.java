package com.tuoshecx.server.cms.interaction.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 政名互动
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class Interaction {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("站点编号")
    private String siteId;
    @ApiModelProperty("用户编号")
    private String userId;
    @ApiModelProperty("用户昵称")
    private String nickname;
    @ApiModelProperty("用户头像")
    private String headImg;
    @ApiModelProperty("互动机构编号")
    private String organId;
    @ApiModelProperty("互动机构名称")
    private String organName;
    @ApiModelProperty("互动标题")
    private String title;
    @ApiModelProperty("互动类型")
    private Type type;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("互动图片")
    private String[] images;
    @ApiModelProperty("回复")
    private String reply;
    @ApiModelProperty("是否公开互动")
    private Boolean open;
    @ApiModelProperty("互动状态")
    private State state;
    @ApiModelProperty("是否置顶")
    private Boolean top;
    @ApiModelProperty("显示顺序")
    private Integer showOrder;
    @ApiModelProperty("微信form_id")
    private String formId;
    @ApiModelProperty("文本内容是否合法")
    private SecCheck secCheck;
    @ApiModelProperty("回复时间")
    private Date replyTime;
    @ApiModelProperty("创建时间")
    private Date createTime;

    public enum  State {
        WAIT, HANDING, REPLY, REFUSE
    }

    public enum Type {
        CONSULT, COMPLAINT, SUGGEST
    }

    public enum SecCheck {
        UNKNOWN, OK, RISKY
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public SecCheck getSecCheck() {
        return secCheck;
    }

    public void setSecCheck(SecCheck secCheck) {
        this.secCheck = secCheck;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interaction that = (Interaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(siteId, that.siteId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(headImg, that.headImg) &&
                Objects.equals(organId, that.organId) &&
                Objects.equals(organName, that.organName) &&
                Objects.equals(title, that.title) &&
                type == that.type &&
                Objects.equals(content, that.content) &&
                Arrays.equals(images, that.images) &&
                Objects.equals(reply, that.reply) &&
                Objects.equals(open, that.open) &&
                state == that.state &&
                Objects.equals(top, that.top) &&
                Objects.equals(showOrder, that.showOrder) &&
                Objects.equals(formId, that.formId) &&
                secCheck == that.secCheck &&
                Objects.equals(replyTime, that.replyTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, siteId, userId, nickname, headImg, organId, organName, title, type, content, reply, open, state, top, showOrder, formId, secCheck, replyTime, createTime);
        result = 31 * result + Arrays.hashCode(images);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("siteId", siteId)
                .append("userId", userId)
                .append("nickname", nickname)
                .append("headImg", headImg)
                .append("organId", organId)
                .append("organName", organName)
                .append("title", title)
                .append("type", type)
                .append("content", content)
                .append("images", images)
                .append("reply", reply)
                .append("open", open)
                .append("state", state)
                .append("top", top)
                .append("showOrder", showOrder)
                .append("formId", formId)
                .append("secCheck", secCheck)
                .append("replyTime", replyTime)
                .append("createTime", createTime)
                .toString();
    }
}
