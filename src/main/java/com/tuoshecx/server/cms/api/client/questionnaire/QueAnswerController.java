package com.tuoshecx.server.cms.api.client.questionnaire;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.client.ClientCredentialContextUtils;
import com.tuoshecx.server.cms.api.client.questionnaire.form.QueAnswerSaveForm;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.questionnaire.domain.QueAnswer;
import com.tuoshecx.server.cms.questionnaire.domain.QueInfo;
import com.tuoshecx.server.cms.questionnaire.service.QueAnswerService;
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
@RequestMapping("/client/QueAnswer")
@Api(value = "/client/QueAnswer", tags = "C-调查问卷答案接口")
public class QueAnswerController {

    @Autowired
    private QueAnswerService queAnswerService;

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

        if (isAnswer(form.getQueInfoId(),getCredential().getId())){
            throw new BaseException("该用户已经提交答案，不能重复提交");
        }else{
            return ResultVo.success(queAnswerService.save(form.toDomain(getCredential().getId())));
        }
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
     * 分页查询调查问卷答案
     * @param userId
     * @param queInfoId
     * @param page
     * @param rows
     * @return
     */
    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询调查问卷项目")
    public ResultPageVo<QueAnswer> query(@RequestParam(required = false) @ApiParam("用户编号id") String userId,
                                         @RequestParam(required = false) @ApiParam("问卷调查信息编号id") String queInfoId,
                                         @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                         @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){
        try{
            List<QueAnswer> data = queAnswerService.queryAnswer(userId,queInfoId,page * rows, rows);
            return new ResultPageVo.Builder<>(page, rows, data)
                    .count(true, () -> queAnswerService.count(userId, queInfoId))
                    .build();
        }catch (Exception e){
            throw new BaseException("查询调查问卷答案错误");
        }
    }


    private boolean isAnswer(String userId,String queInfoId){
        return queAnswerService.isAnswer(userId,queInfoId);
    }

    private Credential getCredential(){
        return ClientCredentialContextUtils.getCredential();
    }


}
