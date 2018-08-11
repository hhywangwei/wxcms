package com.tuoshecx.server.cms.api.manage.wx;

import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.vo.HasVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.site.domain.SiteWxAuthorized;
import com.tuoshecx.server.cms.site.service.SiteWxService;
import com.tuoshecx.server.wx.component.client.response.GetQrcodeResponse;
import com.tuoshecx.server.wx.component.devops.service.DeployService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 站点托管微信小程序信息管理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/wx/site")
@Api(value = "/manage/wx/site", tags = "M-站点托管微信小程序信息")
public class SiteWxController {

    @Autowired
    private SiteWxService service;

    @Autowired
    private DeployService deployService;

    @GetMapping(value = "authorized", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到站点托管微信小程序公众号信息")
    public ResultVo<HasVo> getAuthorized(){
        List<SiteWxAuthorized> list = service.queryAuthorized(currentSiteId());
        return ResultVo.success(list.isEmpty()? HasVo.notHas(): HasVo.has(list.get(0)));
    }

    @GetMapping(value = "qrcode")
    @ApiOperation(value = "得到小程序体验二维码")
    public ResponseEntity<byte[]> getQrcode(@RequestParam(value = "path", required = false)String path){

        GetQrcodeResponse response = deployService.getQrcode(currentSiteId(), StringUtils.defaultString(path, ""));

        return response.isOk()?
                ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.IMAGE_JPEG)
                        .contentLength(response.getContent().length)
                        .body(response.getContent()):
                ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(qrcodeError());
    }

    private String currentSiteId(){
        return ManageCredentialContextUtils.currentSiteId();
    }

    private static  byte[] qrcodeError(){
        return String.format("{\"code\":%d, \"message\": \"%s\"}", 1000, "获取体验二维吗失败").getBytes(Charset.forName("UTF-8"));
    }
}
