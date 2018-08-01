package com.tuoshecx.server.cms.api.client.interaction;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.client.ClientCredentialContextUtils;
import com.tuoshecx.server.cms.api.client.interaction.form.InteractionCommentForm;
import com.tuoshecx.server.cms.api.client.interaction.form.InteractionSaveForm;
import com.tuoshecx.server.cms.api.client.interaction.form.InteractionUpdateForm;
import com.tuoshecx.server.cms.api.client.interaction.vo.InteractionVo;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.interaction.domain.Interaction;
import com.tuoshecx.server.cms.interaction.service.InteractionService;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.sns.domain.Comment;
import com.tuoshecx.server.cms.sns.domain.Counter;
import com.tuoshecx.server.cms.sns.service.CounterService;
import com.tuoshecx.server.cms.sns.service.ReadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 政民互动API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/interaction")
@Api(value = "/client/interaction", tags = "M-政民互动API接口")
public class InteractionController {

    @Autowired
    private InteractionService service;

    @Autowired
    private CounterService counterService;

    @Autowired
    private ReadService readService;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增政民活动")
    public ResultVo<Interaction> save(@Valid @RequestBody InteractionSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.save(form.toDomain(getCredential().getId())));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改政民互动")
    public ResultVo<Interaction> update(@Valid @RequestBody InteractionUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.update(form.toDomain(getCredential().getId())));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到政民互动")
    public ResultVo<InteractionVo> get(@PathVariable("id")String id){
        Optional<Counter> optional = counterService.getOptional(id);
        Interaction t = service.get(id);
        readService.read(getOpenid(), id);
        return ResultVo.success(optional
                .map(e -> new InteractionVo(t, e.getReadCount(), e.getGoodCount()))
                .orElseGet(() -> new InteractionVo(t, 0, 0)));
    }

    @PostMapping(value = "comment", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增评论")
    public ResultVo<Comment> comment(@Valid @RequestBody InteractionCommentForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.comment(form.getId(), getCredential().getId(), form.getImages(), form.getContent()));
    }

    @PostMapping(value = "{id}/good", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<OkVo> good(@PathVariable("id")String id){
        if(ClientCredentialContextUtils.isTemp(getCredential().getType())){
            throw new BaseException(101, "未认证用户");
        }
        return ResultVo.success(new OkVo(service.good(id, getCredential().getId())));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询政民互动")
    public ResultPageVo<Interaction> query(@RequestParam(required = false) @ApiParam("互动标题") String title,
                                           @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                           @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String siteId = ClientCredentialContextUtils.currentSiteId();
        List<Interaction> data = service.query(siteId, title, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data).build();
    }

    private Credential getCredential(){
        return ClientCredentialContextUtils.getCredential();
    }

    private String getOpenid(){
        return ClientCredentialContextUtils.currentOpenid();
    }
}
