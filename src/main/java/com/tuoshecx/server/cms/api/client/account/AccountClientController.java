package com.tuoshecx.server.cms.api.client.account;

import com.tuoshecx.server.cms.api.client.ClientCredentialContextUtils;
import com.tuoshecx.server.cms.api.client.account.form.AccountUpdateForm;
import com.tuoshecx.server.cms.api.client.account.form.UpdatePasswordClientForm;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.interaction.domain.Interaction;
import com.tuoshecx.server.cms.interaction.service.InteractionService;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.user.domain.User;
import com.tuoshecx.server.cms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 用户账户管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/account")
@Api(value = "/client/account", tags = "C-用户信息管理API接口")
public class AccountClientController {

    @Autowired
    private UserService service;

    @Autowired
    private InteractionService interactionService;

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到用户信息")
    public ResultVo<User> get(){
        User u = service.get(getCredential().getId());
        return ResultVo.success(u);
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("更新用户信息")
    public ResultVo<User> update(@Validated @RequestBody AccountUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.update(form.toDomain(getCredential().getId())));
    }

    @PutMapping(value = "updatePassword", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("更新用户密码")
    public ResultVo<OkVo> updatePassword(@Validated @RequestBody UpdatePasswordClientForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        boolean ok = service.updatePassword(
                getCredential().getId(), form.getPassword(), form.getNewPassword());
        return ResultVo.success(new OkVo(ok));
    }

    @GetMapping(value = "interaction", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询我的政民互动")
    public ResultPageVo<Interaction> myInteraction( @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                                       @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<Interaction> data= interactionService.queryByUserId(getCredential().getId(), page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data).build();
    }

    private Credential getCredential(){
        return ClientCredentialContextUtils.getCredential();
    }
}
