package com.tuoshecx.server.cms.api.sys.account;

import com.tuoshecx.server.cms.api.sys.SysCredentialContextUtils;
import com.tuoshecx.server.cms.api.sys.account.form.AccountPasswordUpdateForm;
import com.tuoshecx.server.cms.api.sys.account.form.AccountSysUpdateForm;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.base.domain.Sys;
import com.tuoshecx.server.cms.base.service.SysService;
import com.tuoshecx.server.cms.security.Credential;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 系统管理员账户管理
 *
 * @author  <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/account")
@Api(value = "/sys/account", tags = "S-系统管理员账户管理")
public class AccountSysController {

    @Autowired
    private SysService service;

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到本人信息")
    public ResultVo<Sys> get(){
        return ResultVo.success(service.get(getCredential().getId()));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("更新本人信息")
    public ResultVo<Sys> update(@Valid @RequestBody AccountSysUpdateForm form, BindingResult result){
        if (result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.update(form.toDomain(getCredential().getId())));
    }

    @PutMapping(value = "updatePassword", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改密码")
    public ResultVo<OkVo> updatePassword(@Valid @RequestBody AccountPasswordUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        boolean ok = service.updatePassword(getCredential().getId(), form.getPassword(), form.getNewPassword());
        return ResultVo.success(new OkVo(ok));
    }

    private Credential getCredential(){
        return SysCredentialContextUtils.getCredential();
    }
}
