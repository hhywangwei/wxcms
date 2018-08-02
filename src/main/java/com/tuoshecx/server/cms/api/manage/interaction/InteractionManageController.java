package com.tuoshecx.server.cms.api.manage.interaction;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.manage.interaction.form.InteractionReplyForm;
import com.tuoshecx.server.cms.api.manage.interaction.form.InteractionUpdateForm;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.interaction.domain.Interaction;
import com.tuoshecx.server.cms.interaction.service.InteractionService;
import com.tuoshecx.server.cms.security.Credential;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
/**
 * 政民互动管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/interaction")
@Api(value = "/manage/interaction", tags = "M-政民互动管理API接口")
public class InteractionManageController {

    @Autowired
    private InteractionService service;

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改政民互动")
    public ResultVo<Interaction> update(@Valid @RequestBody InteractionUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        Interaction t = service.get(form.getId());
        if(!StringUtils.equals(t.getSiteId(), currentSiteId())){
            throw new BaseException("互动不存在");
        }

        return ResultVo.success(service.update(form.toDomain(t.getUserId())));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到政民互动 ")
    public ResultVo<Interaction> get(@PathVariable("id") String id){
        return ResultVo.success(service.get(id));
    }

    @PutMapping(value = "{id}/handling", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("开始处理政民互动")
    public ResultVo<Interaction> handling(@PathVariable("id")String id){
        return ResultVo.success(service.handling(id, currentSiteId()));
    }

    @PutMapping(value = "reply", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("回复政民互动")
    public ResultVo<Interaction> reply(@Valid @RequestBody InteractionReplyForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.reply(form.getId(), form.getReply(), currentSiteId(), getCredential().getId()));
    }

    @PutMapping(value = "refuse", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("拒绝政民互动")
    public ResultVo<Interaction> refuse(@Valid @RequestBody InteractionReplyForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.refuse(form.getId(), form.getReply(), currentSiteId(), getCredential().getId()));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询政民互动")
    public ResultPageVo<Interaction> query(@RequestParam(required = false) @ApiParam("互动标题") String title,
                                           @RequestParam(required = false) @ApiParam("提议用户昵称") String nickname,
                                           @RequestParam(required = false) @ApiParam("状态") String state,
                                           @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                           @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        try{
            Interaction.State stateObj = StringUtils.isBlank(state)? null: Interaction.State.valueOf(state);
            List<Interaction> data = service.query(currentSiteId(), title, nickname, StringUtils.EMPTY, stateObj, page* rows, rows);
            return new ResultPageVo.Builder<>(page, rows, data)
                    .count(true , () -> service.count(currentSiteId(), title, nickname, "", stateObj))
                    .build();
        }catch (Exception e){
            throw new BaseException("查询政民互动状态错误");
        }
    }

    private String currentSiteId(){
        return ManageCredentialContextUtils.currentSiteId();
    }

    private Credential getCredential(){
        return ManageCredentialContextUtils.getCredential();
    }
}
