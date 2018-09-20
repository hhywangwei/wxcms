package com.tuoshecx.server.cms.api.manage.questionnaire;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.client.ClientCredentialContextUtils;
import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.questionnaire.domain.QueAnswer;
import com.tuoshecx.server.cms.questionnaire.domain.QueInfo;
import com.tuoshecx.server.cms.questionnaire.domain.QueProject;
import com.tuoshecx.server.cms.questionnaire.service.QueAnswerService;
import com.tuoshecx.server.cms.questionnaire.service.QueInfoService;
import com.tuoshecx.server.cms.questionnaire.service.QueProjectService;
import com.tuoshecx.server.cms.security.Credential;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 调查问卷答案API接口
 * @author LuJun
 */
@RestController
@RequestMapping("/manage/queAnswer")
@Api(value = "/manage/queAnswer", tags = "M-问卷调查答案API接口")
public class QueAnswerManageController {

    @Autowired
    private QueAnswerService queAnswerService;

    @Autowired
    private QueInfoService queInfoService;

    @Autowired
    QueProjectService queProjectService;

    /**
     * 删除调查问卷答案
     * @param id
     * @return
     */
    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除调查问卷答案")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(queAnswerService.delete(id)));
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
     * 分页查询调查问卷答案
     * @param page
     * @param rows
     * @return
     */
    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询调查问卷答案")
    public ResultPageVo<QueAnswer> query(@RequestParam(required = true) @ApiParam("调查问卷信息编号id") String queInfoId,
                                         @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                         @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){
        try{
            List<QueAnswer> data = queAnswerService.queryAnswer("",getSiteId(),queInfoId,page * rows, rows);
            return new ResultPageVo.Builder<>(page, rows, data)
                    .count(true, () -> queAnswerService.count("", getSiteId(),queInfoId))
                    .build();
        }catch (Exception e){
            throw new BaseException("查询调查问卷答案错误");
        }
    }

    private String getSiteId(){
        return ManageCredentialContextUtils.currentSiteId();
    }

}
