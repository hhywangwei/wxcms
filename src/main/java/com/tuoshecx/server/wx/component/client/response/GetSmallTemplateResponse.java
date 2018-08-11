package com.tuoshecx.server.wx.component.client.response;

import java.util.Date;
import java.util.Map;

/**
 * 获取代码模版库中的所有小程序代码模版输出
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class GetSmallTemplateResponse extends ComponentResponse {

    public GetSmallTemplateResponse(Map<String, Object> data) {
        super(data);
    }

    public static class SmallTemplate{
        private  String userVersion;
        private String userDesc;
        private Integer templateId;
        private Date createTime;

        public String getUserVersion() {
            return userVersion;
        }

        public void setUserVersion(String userVersion) {
            this.userVersion = userVersion;
        }

        public String getUserDesc() {
            return userDesc;
        }

        public void setUserDesc(String userDesc) {
            this.userDesc = userDesc;
        }

        public Integer getTemplateId() {
            return templateId;
        }

        public void setTemplateId(Integer templateId) {
            this.templateId = templateId;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }
    }
}
