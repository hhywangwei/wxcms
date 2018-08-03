package com.tuoshecx.server.cms.api.sys.wx;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.sys.wx.vo.ProgramCategoryVo;
import com.tuoshecx.server.cms.api.vo.HasVo;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.site.domain.SiteWxAuthorized;
import com.tuoshecx.server.cms.site.service.SiteWxService;
import com.tuoshecx.server.wx.small.client.response.GetQrcodeResponse;
import com.tuoshecx.server.wx.small.devops.service.DeployService;
import com.tuoshecx.server.wx.small.message.domain.SendMessage;
import com.tuoshecx.server.wx.small.message.domain.SmallTemplate;
import com.tuoshecx.server.wx.small.message.service.SendMessageService;
import com.tuoshecx.server.wx.small.message.service.WxSmallTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/sys/wx/site")
@Api(value = "/sys/wx/site", tags = "S-站点微信小程序管理")
public class SiteWxSysController {

    @Autowired
    private SiteWxService service;

    @Autowired
    private DeployService deployService;

    @Autowired
    private WxSmallTemplateService smallTemplateService;

    @Autowired
    private SendMessageService sendMessageService;

    @GetMapping(value = "{siteId}/authorized", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到站点托管微信小程序公众号信息")
    public ResultVo<HasVo<SiteWxAuthorized>> getAuthorized(@PathVariable("siteId")String siteId){
        List<SiteWxAuthorized> list = service.queryAuthorized(siteId);
        return ResultVo.success(list.isEmpty()? HasVo.notHas(): HasVo.has(list.get(0)));
    }

    @PutMapping(value = "{siteId}/refreshMessageTemplate")
    @ApiOperation("手动刷新小程序消息模板")
    public ResultVo<Boolean> refreshMessageTemplate(@PathVariable("siteId")String siteId){
        List<SiteWxAuthorized> list = service.queryAuthorized(siteId);
        if(list.isEmpty()){
            throw new BaseException(300, "小程序公众号还未托管");
        }

        String appid = list.get(0).getAppid();
        smallTemplateService.refresh(appid);
        return ResultVo.success(true);
    }

    @GetMapping(value = "messageTemplate", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询微信消息模板信息")
    public ResultPageVo<SmallTemplate> queryMessageTemplate(@RequestParam(required = false) @ApiParam(value = "站点编号") String siteId,
                                                            @RequestParam(required = false) @ApiParam(value = "调用") String callKey,
                                                            @RequestParam(required = false) @ApiParam(value = "标题") String title,
                                                            @RequestParam(required = false) @ApiParam(value = "备注") String remark,
                                                            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                                            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return service.getAppid(siteId)
                .map(e -> {
                    List<SmallTemplate> data = smallTemplateService.query(e, callKey, title, remark, page* rows, rows);
                    return new ResultPageVo.Builder<>(page, rows, data)
                            .count(true, () -> smallTemplateService.count(e, callKey, title, remark))
                            .build();
                })
                .orElseGet(() -> new ResultPageVo.Builder<SmallTemplate>(page, rows, Collections.emptyList()).count(true, ()-> 0L).build());
    }

    @GetMapping(value = "sendMessage", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询发送消息日志")
    public ResultPageVo<SendMessage> querySendMessage(@RequestParam(required = false) @ApiParam("站点编号") String siteId,
                                                      @RequestParam(required = false) @ApiParam("消息标题") String title,
                                                      @RequestParam(required = false) @ApiParam("调用关键字") String callKey,
                                                      @RequestParam(required = false) @ApiParam("接收用户openid") String openid,
                                                      @RequestParam(required = false) @ApiParam("消息状态") String state,
                                                      @RequestParam(required = false) @ApiParam("查询数据开始日期") Date fromTime,
                                                      @RequestParam(required = false) @ApiParam("查询数据结束日期") Date toTime,
                                                      @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                                      @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return service.getAppid(siteId)
                .map(e -> {
                    List<SendMessage> data = sendMessageService.query(e, title, callKey, openid, state, fromTime, toTime, page* rows, rows);
                    return new ResultPageVo.Builder<>(page, rows, data)
                            .count(true, () -> sendMessageService.count(e, title, callKey, openid, state, fromTime, toTime))
                            .build();
                })
                .orElseGet(() -> new ResultPageVo.Builder<SendMessage>(page, rows, Collections.emptyList()).count(true, ()-> 0L).build());
    }

    @GetMapping(value = "{siteId}/qrcode")
    @ApiOperation(value = "得到小程序体验二维码")
    public ResponseEntity<byte[]> getQrcode(@PathVariable("siteId")String siteId,
                                            @RequestParam(value = "path", required = false)String path){

        GetQrcodeResponse response = deployService.getQrcode(siteId, StringUtils.defaultString(path, ""));
        return response.isOk()?
                ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.IMAGE_JPEG)
                        .contentLength(response.getContent().length)
                        .body(response.getContent()):
                ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(qrcodeError());
    }

    @GetMapping(value = "{siteId}/category", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到小程序分类")
    public ResultVo<List<ProgramCategoryVo>> category(@PathVariable("siteId")String siteId){
        List<ProgramCategoryVo> data = deployService
                .getCategory(siteId).getCategories().stream()
                .map(ProgramCategoryVo::new).collect(Collectors.toList());
        return ResultVo.success(data);
    }

    private static  byte[] qrcodeError(){
        return String.format("{\"code\":%d, \"message\": \"%s\"}", 1000, "获取体验二维吗失败").getBytes(Charset.forName("UTF-8"));
    }
}
