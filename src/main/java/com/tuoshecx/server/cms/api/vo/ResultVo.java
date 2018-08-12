package com.tuoshecx.server.cms.api.vo;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Collection;
import java.util.List;

/**
 * 返回客户端消息
 *
 * @author WangWei
 */
public class ResultVo<T> {
    @ApiModelProperty(value = "返回编码", notes = "0:处理正常；反之处理异常")
    private final int code;
    @ApiModelProperty(value = "错误信息")
    private final String[] messages;
    @ApiModelProperty(value = "处理结果")
    protected final T rs;

    public static <T> ResultVo<T> success(T rs) {
        return new ResultVo<>(0, new String[0], rs);
    }

    public static <T> ResultVo<T[]> success(T[] rs){
        return new ResultVo<>(0, new String[0], rs);
    }

    public static <T> ResultVo<T> error(int code, String message) {
        Assert.isTrue(code > 0, "Code must gt 0");
        return new ResultVo<>(code, new String[]{message}, null);
    }

    public static <T> ResultVo<T> error(int code, String[] messages) {
        Assert.isTrue(code > 0, "Code must gt 0");
        return new ResultVo<>(code, messages, null);
    }

    public static <T> ResultVo<T> error(String[] messages){
        Assert.notNull(messages, "Message not null");
        return error(1000, messages);
    }

    public static <T> ResultVo<T> error(List<ObjectError> errors){
        String[] messages = errors.stream().map(e ->
                (e instanceof FieldError) ?
                        ((FieldError)e).getField()+ "=" + e.getDefaultMessage() :
                        e.getDefaultMessage()
        ).toArray(String[]::new);
        return error(1001, messages);
    }

    protected ResultVo(int code, String[] messages, T rs) {
        this.code = code;
        this.messages = messages;
        this.rs = rs;
    }

    public int getCode() {
        return code;
    }

    public String[] getMessages() {
        return messages;
    }

    public T getRs() {
        return rs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("rs", rs)
                .append("messages", messages)
                .toString();
    }
}
