package com.tuoshecx.server.cms.api.manage.wx.form;

import com.tuoshecx.server.wx.component.devops.domain.SmallTester;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 绑定小程序测试
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class BindTesterForm {
    @NotBlank
    @ApiModelProperty(value = "微信编号", required = true)
    private String wechatid;
    @ApiModelProperty("备注")
    private String remark;

    public String getWechatid() {
        return wechatid;
    }

    public void setWechatid(String wechatid) {
        this.wechatid = wechatid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SmallTester toDomain(String siteId){
        SmallTester t = new SmallTester();

        t.setSiteId(siteId);
        t.setWechatid(wechatid);
        t.setRemark(remark);

        return t;
    }
}
