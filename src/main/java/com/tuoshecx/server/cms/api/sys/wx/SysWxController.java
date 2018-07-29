package com.tuoshecx.server.cms.api.sys.wx;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.vo.HasVo;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.site.domain.SiteWxAuthorized;
import com.tuoshecx.server.cms.site.service.SiteWxService;
import com.tuoshecx.server.wx.small.message.domain.SendMessage;
import com.tuoshecx.server.wx.small.message.domain.SmallTemplate;
import com.tuoshecx.server.wx.small.message.service.SendMessageService;
import com.tuoshecx.server.wx.small.message.service.WxSmallTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/sys/wx")
@Api(value = "/sys/wx", tags = "S-站点微信小程序管理")
public class SysWxController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysWxController.class);

    @Autowired
    private SiteWxService service;

    @Autowired
    private WxSmallTemplateService smallTemplateService;

    @Autowired
    private SendMessageService sendMessageService;

    @GetMapping(value = "{shopId}/info", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到站点托管微信小程序公众号信息")
    public ResultVo<HasVo<SiteWxAuthorized>> getInfo(@PathVariable("shopId")String shopId){
        List<SiteWxAuthorized> list = service.queryAuthorized(shopId);
        return ResultVo.success(list.isEmpty()? HasVo.notHas(): HasVo.has(list.get(0)));
    }

    @PutMapping(value = "{shopId}/refreshMessageTemplate")
    @ApiOperation("手动刷新小程序消息模板")
    public ResultVo<Boolean> refreshMessageTemplate(@PathVariable("shopId")String shopId){
        List<SiteWxAuthorized> list = service.queryAuthorized(shopId);
        if(list.isEmpty()){
            throw new BaseException(300, "小程序公众号还未托管");
        }

        String appid = list.get(0).getAppid();
        smallTemplateService.refresh(appid);

        return ResultVo.success(true);
    }

    @GetMapping(value = "messageTemplate", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询微信消息模板信息")
    public ResultPageVo<SmallTemplate> queryMessageTemplate(@RequestParam(required = false) @ApiParam(value = "站点编号") String shopId,
                                                            @RequestParam(required = false) @ApiParam(value = "调用") String callKey,
                                                            @RequestParam(required = false) @ApiParam(value = "标题") String title,
                                                            @RequestParam(required = false) @ApiParam(value = "备注") String remark,
                                                            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                                            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<SiteWxAuthorized> authorizeds = service.queryAuthorized(shopId);
        List<SmallTemplate> data = authorizeds.isEmpty()?
                Collections.emptyList() : smallTemplateService.query(authorizeds.get(0).getAppid(), callKey, title, remark, page *rows, rows);

        return new ResultPageVo.Builder<>(page, rows, data)
                .count(true, () -> authorizeds.isEmpty()? 0L: smallTemplateService.count(authorizeds.get(0).getAppid(), callKey, title, remark))
                .build();
    }

    @GetMapping(value = "sendMessage", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询发送消息日志")
    public ResultPageVo<SendMessage> querySendMessage(@RequestParam(required = false) @ApiParam("站点编号") String shopId,
                                                      @RequestParam(required = false) @ApiParam("消息标题") String title,
                                                      @RequestParam(required = false) @ApiParam("调用关键字") String callKey,
                                                      @RequestParam(required = false) @ApiParam("接收用户openid") String openid,
                                                      @RequestParam(required = false) @ApiParam("消息状态") String status,
                                                      @RequestParam(required = false) @ApiParam("查询数据开始日期") Date fromTime,
                                                      @RequestParam(required = false) @ApiParam("查询数据结束日期") Date toTime,
                                                      @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                                      @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<SiteWxAuthorized> authorizeds = service.queryAuthorized(shopId);
        List<SendMessage> data = authorizeds.isEmpty()?
                Collections.emptyList() :
                sendMessageService.query(authorizeds.get(0).getAppid(), title, callKey, openid, status, fromTime, toTime, page * rows, rows);

        return new ResultPageVo.Builder<>(page, rows, data)
                .count(true, () -> authorizeds.isEmpty() ? 0L : sendMessageService.count(authorizeds.get(0).getAppid(), title, callKey, openid, status, fromTime, toTime))
                .build();
    }
}
