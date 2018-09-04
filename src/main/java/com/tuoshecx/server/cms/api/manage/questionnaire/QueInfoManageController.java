package com.tuoshecx.server.cms.api.manage.questionnaire;


import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.manage.questionnaire.form.QueInfoSaveForm;
import com.tuoshecx.server.cms.api.manage.questionnaire.form.QueInfoUpdateForm;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.questionnaire.domain.QueInfo;
import com.tuoshecx.server.cms.questionnaire.service.QueInfoService;
import com.tuoshecx.server.cms.security.Credential;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 调查问卷信息API接口
 * @author LuJun
 */
@RestController
@RequestMapping("/manage/queInfo")
@Api(value = "/manage/queInfo", tags = "M-问卷调查信息API接口")
public class QueInfoManageController {

    @Autowired
    private QueInfoService queInfoService;

    /**
     * 新增问卷信息
     * @param form
     * @param result
     * @return
     */
    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增调查问卷信息")
    public ResultVo<QueInfo> save(@Valid @RequestBody QueInfoSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(queInfoService.save(form.toDomain(),getCredential().getId()));
    }

    /**
     * 更新问卷信息
     * @param form
     * @param result
     * @return
     */
    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("更新调查问卷信息")
    public ResultVo<QueInfo> udpate(@Valid @RequestBody QueInfoUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(queInfoService.udpate(form.toDomain(),getCredential().getId()));
    }

    /**
     * 删除文件调查信息
     * @param id
     * @return
     */
    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除调查问卷信息")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(queInfoService.delete(id)));
    }


    /**
     * 关闭调查问卷信息
     * @param id
     * @return
     */
    @PutMapping(value = "{id}/close", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("关闭调查问卷信息")
    public ResultVo<OkVo> close(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(queInfoService.close(id,getCredential().getId())));
    }

    /**
     * 开启调查问卷信息
     * @param id
     * @return
     */
    @PutMapping(value = "{id}/open", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(" 开启调查问卷信息")
    public ResultVo<OkVo> open(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(queInfoService.open(id,getCredential().getId())));
    }


    /**
     * 得到调查问卷信息
     * @param id
     * @return
     */
    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到调查问卷项目")
    public ResultVo<QueInfo> get(@PathVariable("id")String id){
        return ResultVo.success(queInfoService.get(id));
    }

    /**
     * 分页查询调查问卷信息
     * @param organId
     * @param state
     * @param page
     * @param rows
     * @return
     */
    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询调查问卷项目")
    public ResultPageVo<QueInfo> query(@RequestParam(required = false) @ApiParam("组织机构编号") String organId,
                                       @RequestParam(required = false) @ApiParam("问卷调查信息状态") String state,
                                       @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                       @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){
        try{
            List<QueInfo> data = queInfoService.queryInfo(organId,state,page * rows, rows);
            return new ResultPageVo.Builder<>(page, rows, data)
                    .count(true, () -> queInfoService.count(organId, state))
                    .build();
        }catch (Exception e){
            throw new BaseException("查询调查问卷信息错误");
        }
    }



    private Credential getCredential(){
        return ManageCredentialContextUtils.getCredential();
    }


}
