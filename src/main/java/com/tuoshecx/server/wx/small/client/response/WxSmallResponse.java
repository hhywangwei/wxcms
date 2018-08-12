package com.tuoshecx.server.wx.small.client.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

/**
 * 微信小程序API接口基础输出对象
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class WxSmallResponse {
    private final Integer code;
    private final String message;

    public WxSmallResponse(Map<String, Object> data){
        this((Integer)data.getOrDefault("errcode", 0),
                (String)data.getOrDefault("errmsg", ""));
    }

    public WxSmallResponse(Integer code, String message){
        this.code= code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isOk(){
        return code == 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("message", message)
                .toString();
    }
}
