package com.tuoshecx.server.cms.api.client.questionnaire;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.client.ClientCredentialContextUtils;
import com.tuoshecx.server.cms.api.client.questionnaire.form.QueAnswerSaveForm;
import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.base.domain.Sys;
import com.tuoshecx.server.cms.questionnaire.domain.QueAnswer;
import com.tuoshecx.server.cms.questionnaire.domain.QueInfo;
import com.tuoshecx.server.cms.questionnaire.domain.QueProject;
import com.tuoshecx.server.cms.questionnaire.service.QueAnswerService;
import com.tuoshecx.server.cms.questionnaire.service.QueInfoService;
import com.tuoshecx.server.cms.questionnaire.service.QueProjectService;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.user.service.UserService;
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
 * 调查问卷答题API接口
 * @author LuJun
 */
@RestController
@RequestMapping("/client/queAnswer")
@Api(value = "/client/queAnswer", tags = "C-调查问卷答案接口")
public class QueAnswerClientController {

    @Autowired
    private QueAnswerService queAnswerService;

    @Autowired
    private QueInfoService queInfoService;

    @Autowired QueProjectService queProjectService;

    @Autowired
    private UserService userService;

    /**
     * 新增问卷信息
     * @param form
     * @param result
     * @return
     */
    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增调查问卷信息")
    public ResultVo<QueAnswer> save(@Valid @RequestBody QueAnswerSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(queAnswerService.save(form.toDomain(getSiteId()),getCredential().getId()));
    }

    /**
     * 得到调查问卷信息
     * @param id
     * @return
     */
    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到调查问卷答案")
    public ResultVo<QueAnswer> get(@PathVariable("id")String id){
        return ResultVo.success(queAnswerService.get(id));
    }


    /**
     * 获取问卷调查信息
     * @return
     */
    @GetMapping(value = "getQueInfo",produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("获取调查问卷信息")
    public ResultPageVo<QueInfo> getQueInfo(@RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        try{
            List<QueInfo> data = queInfoService.queryInfo(getSiteId(),"",QueInfo.State.OPEN,page * rows, rows);
            return new ResultPageVo.Builder<>(page, rows, data)
                    .count(true, () -> queInfoService.count(getSiteId(), "",QueInfo.State.OPEN))
                    .build();
        }catch (Exception e){
            throw new BaseException("获取调查问卷信息错误！");
        }
    }

    /**
     * 获取问卷调查项目
     * @param queInfoId
     * @param page
     * @param rows
     * @return
     */
    @GetMapping(value = "getQueProject",produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("获取调查问卷项目")
    public ResultPageVo<QueProject> getQueProject(@RequestParam(required = true) @ApiParam("调查问卷信息编号id") String queInfoId,
                                                  @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                                  @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        if (queAnswerService.isAnswer(getCredential().getId(),queInfoId)){
            throw new BaseException("您已经提交过答案了！");
        }

        try{
                List<QueProject> data = queProjectService.queryProject(queInfoId,"","",page * rows, rows);
                return new ResultPageVo.Builder<>(page, rows, data)
                        .count(true, () -> queProjectService.count(queInfoId, "",""))
                        .build();
        }catch (Exception e){
            throw new BaseException("获取调查问卷项目错误！");
        }
    }

    /**
     * 分页查询调查问卷答案
     * @param page
     * @param rows
     * @return
     */
    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询调查问卷答案")
    public ResultPageVo<QueAnswer> query(@RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                         @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){
        try{
            List<QueAnswer> data = queAnswerService.queryAnswer(getCredential().getId(),getSiteId(),"",page * rows, rows);
            return new ResultPageVo.Builder<>(page, rows, data)
                    .count(true, () -> queAnswerService.count(getCredential().getId(), getSiteId(),""))
                    .build();
        }catch (Exception e){
            throw new BaseException("查询调查问卷答案错误"+e.getMessage());
        }
    }


    private String getSiteId(){
        return ClientCredentialContextUtils.currentSiteId();
    }

    private Credential getCredential(){
        return ClientCredentialContextUtils.getCredential();
    }


}
