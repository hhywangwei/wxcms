package com.tuoshecx.server.cms.api.manage.wx;

import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.site.service.SiteWxService;
import com.tuoshecx.server.wx.small.message.domain.SendMessage;
import com.tuoshecx.server.wx.small.message.service.SendMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("manage/wx/message")
@Api(value = "manage/wx/message", tags = "M-微信小程序推送消息查询")
public class SmallMessageController {

    @Autowired
    private SiteWxService service;

    @Autowired
    private SendMessageService sendMessageService;

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询发送消息日志")
    public ResultPageVo<SendMessage> query(@RequestParam(required = false) @ApiParam("消息标题") String title,
                                           @RequestParam(required = false) @ApiParam("调用关键字") String callKey,
                                           @RequestParam(required = false) @ApiParam("接收用户openid") String openid,
                                           @RequestParam(required = false) @ApiParam("消息状态") String state,
                                           @RequestParam(required = false) @ApiParam("查询数据开始日期") Date fromTime,
                                           @RequestParam(required = false) @ApiParam("查询数据结束日期") Date toTime,
                                           @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                           @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return service.getAppid(currentSiteId())
                .map(e -> {
                    List<SendMessage> data = sendMessageService.query(e, title, callKey, openid, state, fromTime, toTime, page* rows, rows);
                    return new ResultPageVo.Builder<>(page, rows, data)
                            .count(true, () -> sendMessageService.count(e, title, callKey, openid, state, fromTime, toTime))
                            .build();
                })
                .orElseGet(() -> new ResultPageVo.Builder<SendMessage>(page, rows, Collections.emptyList()).count(true, ()-> 0L).build());
    }

    private String currentSiteId(){
        return ManageCredentialContextUtils.currentSiteId();
    }
}
