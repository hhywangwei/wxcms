package com.tuoshecx.server.cms.api.client.main;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.client.ClientCredentialContextUtils;
import com.tuoshecx.server.cms.api.client.main.form.WxLoginForm;
import com.tuoshecx.server.cms.api.form.LoginForm;
import com.tuoshecx.server.cms.api.vo.LoginVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.security.token.TokenService;
import com.tuoshecx.server.cms.site.domain.SiteWxAuthorized;
import com.tuoshecx.server.cms.site.service.SiteWxService;
import com.tuoshecx.server.cms.user.domain.User;
import com.tuoshecx.server.cms.user.service.UserService;
import com.tuoshecx.server.wx.small.client.WxSmallClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 客户端基础API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client")
@Api(value = "/client", tags = "客户端基础API接口")
public class MainClientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainClientController.class);

    @Autowired
    private UserService service;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WxSmallClientService wxService;

    @Autowired
    private SiteWxService shopWxService;

    @PostMapping(value = "wxLogin", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "微信小程序用户登陆")
    public ResultVo<LoginVo<User>> wxLogin(@Validated @RequestBody WxLoginForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        Optional<String> optional = wxService.login(form.getAppid(), form.getCode());
        return optional.map(e -> loginOpenid(e, form.getAppid())).orElseGet(() -> ResultVo.error(100, "用登陆失败"));
    }

    private ResultVo<LoginVo<User>> loginOpenid(String openid, String appid){
        return service.getByOpenid(openid)
                .map(e -> ResultVo.success(new LoginVo<>(createToken(e, "wx", "user"), e)))
                .orElseGet(()-> {
                    User u = tempUser(openid, appid);
                    LOGGER.debug("Temp user shop id is {}", u.getSiteId());
                    return ResultVo.success(new LoginVo<>(createToken(u, "wx", "temp"), u));
                });
    }

    private String createToken(User t, String enter, String type){
        final List<String> roles = Collections.singletonList("ROLE_USER");
        final List<Credential.Attach> attaches = new ArrayList<>(3);

        attaches.add(new Credential.Attach(ClientCredentialContextUtils.SITE_ID_KEY, t.getSiteId()));
        if(isWxEnter(enter)){
            attaches.add(new Credential.Attach(ClientCredentialContextUtils.OPENID_KEY, t.getOpenid()));
        }
        return tokenService.generate(new Credential(t.getId(),
                enter, type, roles, Collections.unmodifiableList(attaches)));
    }

    private boolean isWxEnter(String enter){
        return StringUtils.equals("wx", enter);
    }

    private User tempUser(String openid, String appid){
        Optional<SiteWxAuthorized> optional = shopWxService.getAuthorized(appid);
        if(!optional.isPresent()){
            throw new BaseException("用户无权限");
        }

        User t = new User();
        t.setAppid(appid);
        t.setOpenid(openid);
        t.setSiteId(optional.get().getSiteId());

        return t;
    }

    @PostMapping(value = "login", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("用户登陆")
    public ResultVo<LoginVo<User>> login(@Validated @RequestBody LoginForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        User user = service.getValidate(form.getUsername(), form.getPassword());
        String token = createToken(user,"base", "user");
        return ResultVo.success(new LoginVo<>(token, user));
    }
}
