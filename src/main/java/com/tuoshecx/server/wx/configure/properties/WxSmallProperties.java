package com.tuoshecx.server.wx.configure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 微信小程序配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ConfigurationProperties(prefix = "wx.small")
public class WxSmallProperties {
    private List<MessageTemplate> msgTemplates;
    private Qrcode qrcode;

    public List<MessageTemplate> getMsgTemplates() {
        return msgTemplates;
    }

    public void setMsgTemplates(List<MessageTemplate> msgTemplates) {
        this.msgTemplates = msgTemplates;
    }

    public Qrcode getQrcode() {
        return qrcode;
    }

    public void setQrcode(Qrcode qrcode) {
        this.qrcode = qrcode;
    }

    public static class MessageTemplate {
        private String callKey;
        private String id;
        private List<Integer> keywordIds;
        private String remark;

        public String getCallKey() {
            return callKey;
        }

        public void setCallKey(String callKey) {
            this.callKey = callKey;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Integer> getKeywordIds() {
            return keywordIds;
        }

        public void setKeywordIds(List<Integer> keywordIds) {
            this.keywordIds = keywordIds;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public static class Qrcode{
        private String bindUserPath;
        private String articleReleasePath;

        public String getBindUserPath() {
            return bindUserPath;
        }

        public void setBindUserPath(String bindUserPath) {
            this.bindUserPath = bindUserPath;
        }

        public String getArticleReleasePath() {
            return articleReleasePath;
        }

        public void setArticleReleasePath(String articleReleasePath) {
            this.articleReleasePath = articleReleasePath;
        }
    }
}
