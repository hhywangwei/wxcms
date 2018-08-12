package com.tuoshecx.server.cms.api.manage.account;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.manage.account.form.ManageAccountUpdateForm;
import com.tuoshecx.server.cms.api.manage.account.form.ManagePasswordUpdateForm;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.site.domain.Manager;
import com.tuoshecx.server.cms.site.service.ManagerService;
import com.tuoshecx.server.cms.site.service.SiteWxService;
import com.tuoshecx.server.wx.configure.properties.WxSmallProperties;
import com.tuoshecx.server.wx.small.client.WxSmallClientService;
import com.tuoshecx.server.wx.small.client.response.SmallQrcodeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.nio.charset.Charset;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 管理员账户管理
 *
 * @author  <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/account")
@Api(value = "/manage/account", tags = "M-管理员账户API接口")
public class AccountManageController {

    @Autowired
    private ManagerService service;

    @Autowired
    private WxSmallClientService clientService;

    @Autowired
    private WxSmallProperties properties;

    @Autowired
    private SiteWxService wxService;

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改本人信息")
    public ResultVo<Manager> update(@Valid @RequestBody ManageAccountUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.update(form.toDomain(getCredential().getId(), currentSiteId())));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到本人信息")
    public ResultVo<Manager> get(){
        return ResultVo.success(service.get(getCredential().getId()));
    }

    @PutMapping(value = "updatePassword", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改站点管理员密码")
    public ResultVo<OkVo> updatePassword(@Valid @RequestBody ManagePasswordUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        boolean ok = service.updatePassword(
                getCredential().getId(), currentSiteId(), form.getPassword(), form.getNewPassword());
        return ResultVo.success(new OkVo(ok));
    }

    @GetMapping(value = "bindUser")
    @ApiOperation("得到微信发布二维码")
    public ResponseEntity<byte[]> bindUserQrcode(){
        Optional<String> optional = wxService.getAppid(currentSiteId());
        if(!optional.isPresent()){
            return responseErrorMessage("公众号未授权");
        }
        String appid = optional.get();
        SmallQrcodeResponse response = clientService.getSmallQrcodeLimit(
                appid, properties.getQrcode().getBindUserPath(), getCredential().getId());

        return response.isOk()?
                ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(response.getImage()):
                responseErrorMessage(response.getMessage());
    }

    private ResponseEntity<byte[]> responseErrorMessage(String message){
        String m = String.format("{\"code\": %d, \"messages\": [\"%s\"]}", 1000, message);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(m.getBytes(Charset.forName("UTF-8")));
    }

    private String currentSiteId(){
        return ManageCredentialContextUtils.currentSiteId();
    }

    private Credential getCredential(){
        return ManageCredentialContextUtils.getCredential();
    }
}
