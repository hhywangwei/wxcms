package com.tuoshecx.server.cms.api.manage.main;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.form.LoginForm;
import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.vo.LoginVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.security.authenticate.GlobalRole;
import com.tuoshecx.server.cms.security.token.TokenService;
import com.tuoshecx.server.cms.site.domain.Manager;
import com.tuoshecx.server.cms.site.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 站点管理基础接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("manage")
@Api(value = "manage", tags = "M-站点管理基础接口")
public class MainManageController {

    @Autowired
    private ManagerService service;

    @Autowired
    private TokenService tokenService;


    @PostMapping(value = "login", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("站点管理登陆")
    public ResultVo<LoginVo<Manager>> login(@Valid @RequestBody LoginForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        try{
            Manager o = service.getValidate(form.getUsername(), form.getPassword());
            String  token = createToken(o);
            return ResultVo.success(new LoginVo<>(token, o));
        }catch (BaseException e){
            return ResultVo.error(e.getCode(), e.getMessage());
        }
    }


    private String createToken(Manager t){
        List<Credential.Attach> attaches = Collections.singletonList(
                new Credential.Attach(ManageCredentialContextUtils.SITE_ID_KEY, t.getSiteId()));
        Credential c = new Credential(t.getId(), "base", "manager", initRoles(t.getRoles()), attaches);
        return tokenService.generate(c);
    }

    private List<String> initRoles(String[] roles){
        List<String> list = new ArrayList<>(Arrays.asList(roles));
        list.add(GlobalRole.ROLE_ANONYMOUS.name());
        list.add(GlobalRole.ROLE_AUTHENTICATION.name());
        return Collections.unmodifiableList(list);
    }
}
