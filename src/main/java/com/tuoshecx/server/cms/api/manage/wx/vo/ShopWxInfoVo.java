package com.tuoshecx.server.cms.api.manage.wx.vo;

import com.tuoshecx.server.cms.site.domain.SiteWxAuthorized;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 站点托管微信小程序公众号信息输出对象
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel(value = "站点托管微信小程序公众号信息输出对象")
public class ShopWxInfoVo {
    @ApiModelProperty(value = "true:有托管公众号信息,false:无托管公众号信息")
    private final Boolean has;
    @ApiModelProperty(value = "托管公众号信息")
    private final SiteWxAuthorized info;

    private ShopWxInfoVo(Boolean has, SiteWxAuthorized info){
        this.has = has;
        this.info = info;
    }

    public Boolean getHas() {
        return has;
    }

    public SiteWxAuthorized getInfo() {
        return info;
    }

    public static ShopWxInfoVo has(SiteWxAuthorized info){
        return new ShopWxInfoVo(true, info);
    }

    public static ShopWxInfoVo noHas(){
        return new ShopWxInfoVo(false, null);
    }
}
