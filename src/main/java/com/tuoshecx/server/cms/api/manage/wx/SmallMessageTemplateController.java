package com.tuoshecx.server.cms.api.manage.wx;

import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.site.service.SiteWxService;
import com.tuoshecx.server.wx.small.message.domain.SmallTemplate;
import com.tuoshecx.server.wx.small.message.service.WxSmallTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("manage/wx/messageTemplate")
@Api(value = "manage/wx/messageTemplate", tags = "M-站点微信小程序推送消息模板小查询")
public class SmallMessageTemplateController {

    @Autowired
    private SiteWxService service;

    @Autowired
    private WxSmallTemplateService smallTemplateService;

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询微信消息模板信息")
    public ResultPageVo<SmallTemplate> query(@RequestParam(required = false) @ApiParam(value = "调用") String callKey,
                                             @RequestParam(required = false) @ApiParam(value = "标题") String title,
                                             @RequestParam(required = false) @ApiParam(value = "备注") String remark,
                                             @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                             @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return service.getAppid(currentSiteId())
                .map(e -> {
                    List<SmallTemplate> data = smallTemplateService.query(e, callKey, title, remark, page* rows, rows);
                    return new ResultPageVo.Builder<>(page, rows, data)
                            .count(true, () -> smallTemplateService.count(e, callKey, title, remark))
                            .build();
                })
                .orElseGet(() -> new ResultPageVo.Builder<SmallTemplate>(page, rows, Collections.emptyList()).count(true, ()-> 0L).build());
    }


    private String currentSiteId(){
        return ManageCredentialContextUtils.currentSiteId();
    }
}
