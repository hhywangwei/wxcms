package com.tuoshecx.server.cms.api.client.account;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.client.ClientCredentialContextUtils;
import com.tuoshecx.server.cms.api.client.account.form.AccountUpdateForm;
import com.tuoshecx.server.cms.api.client.account.form.ArticleReleaseForm;
import com.tuoshecx.server.cms.api.client.account.form.BindManagerForm;
import com.tuoshecx.server.cms.api.client.account.form.UpdatePasswordClientForm;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.article.domain.Article;
import com.tuoshecx.server.cms.article.service.ArticleService;
import com.tuoshecx.server.cms.interaction.domain.Interaction;
import com.tuoshecx.server.cms.interaction.service.InteractionService;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.site.domain.Manager;
import com.tuoshecx.server.cms.site.service.ManagerService;
import com.tuoshecx.server.cms.user.domain.User;
import com.tuoshecx.server.cms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountClientController.class);

    private static final List<String> RELEASE_ROLES = initReleaseRoles();

    @Autowired
    private UserService service;

    @Autowired
    private InteractionService interactionService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ArticleService articleService;

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
    public ResultPageVo<Interaction> myInteraction(@RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                                   @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<Interaction> data= interactionService.queryByUserId(getCredential().getId(), page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data).build();
    }

    @PostMapping(value = "bindManager", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("绑定管理员")
    public ResultVo<Manager> bindManager(@Validated @RequestBody BindManagerForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        User user = service.get(getCredential().getId());
        return ResultVo.success(managerService.bindUser(form.getManagerId(), user));
    }

    @PutMapping(value = "articleRelease", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("发布文章")
    public ResultVo<Article> articleRelease(@Validated @RequestBody ArticleReleaseForm form, BindingResult result){

        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        LOGGER.info("User id {}, site is {}", getCredential().getId(), getSiteId());
        Optional<Manager> optional = managerService.getByBindUser(getCredential().getId(), getSiteId());
        if(!optional.isPresent()){
            throw new BaseException(200, "无发布文章权限");
        }
        Manager manager = optional.get();
        if(!hasReleaseRoles(manager.getRoles())){
            throw new BaseException(200, "无发布文章权限");
        }

        Article t = articleService.release(form.getArticleId(), manager.getSiteId(), manager.getId());
        return ResultVo.success(t);
    }

    private boolean hasReleaseRoles(String[] roles){
        return Arrays.stream(roles).anyMatch(RELEASE_ROLES::contains);
    }

    private Credential getCredential(){
        return ClientCredentialContextUtils.getCredential();
    }

    private String getSiteId(){
        return ClientCredentialContextUtils.currentSiteId();
    }

    private static List<String> initReleaseRoles(){
        List<String> roles = new ArrayList<>();

        roles.add("ROLE_SITE_ADMIN");
        roles.add("ROLE_SITE_MANAGE");
        roles.add("ROLE_SITE_ARTICLE");

        return roles;
    }
}
