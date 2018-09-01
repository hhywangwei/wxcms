package com.tuoshecx.server.cms.api.manage.questionnaire;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.manage.questionnaire.form.QueProjectSaveForm;
import com.tuoshecx.server.cms.api.manage.questionnaire.form.QueProjectUpdateForm;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.questionnaire.domain.QueProject;
import com.tuoshecx.server.cms.questionnaire.service.QueProjectService;
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
 * 调查问卷项目API接口
 * @author LuJun
 */
@RestController
@RequestMapping("/manage/QueProject")
@Api(value = "/manage/QueProject", tags = "M-问卷调查项目API接口")
public class QueProjectController {

    @Autowired
    private QueProjectService queProjectService;


    /**
     * 新增问卷项目
     * @param form
     * @param result
     * @return
     */
    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增调查问卷项目")
    public ResultVo<QueProject> save(@Valid @RequestBody QueProjectSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(queProjectService.save(form.toDomain(),getCredential().getId()));
    }


    /**
     * 更新问卷项目
     * @param form
     * @param result
     * @return
     */
    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改调查问卷项目")
    public ResultVo<QueProject> update(@Valid @RequestBody QueProjectUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(queProjectService.udpate(form.toDomain(),getCredential().getId()));
    }

    /**
     * 得到调查问卷项目
     * @param id
     * @return
     */
    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到调查问卷项目")
    public ResultVo<QueProject> get(@PathVariable("id")String id){
        return ResultVo.success(queProjectService.get(id));
    }


    /**
     * 分页查询调查问卷项目
     * @param queInfoId
     * @param type
     * @param page
     * @param rows
     * @return
     */
    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询调查问卷项目")
    public ResultPageVo<QueProject> query(@RequestParam(required = false) @ApiParam("问卷调查信息编号") String queInfoId,
                                          @RequestParam(required = false) @ApiParam("问卷调查项目类型") String type,
                                          @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                          @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){
        try{
            List<QueProject> data = queProjectService.queryProject(queInfoId,type,page * rows, rows);
            return new ResultPageVo.Builder<>(page, rows, data)
                    .count(true, () -> queProjectService.count(queInfoId, type))
                    .build();
        }catch (Exception e){
            throw new BaseException("查询调查问卷项目错误");
        }
    }



    private Credential getCredential(){
        return ManageCredentialContextUtils.getCredential();
    }
}
