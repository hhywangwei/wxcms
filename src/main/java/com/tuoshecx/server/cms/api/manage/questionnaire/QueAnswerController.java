package com.tuoshecx.server.cms.api.manage.questionnaire;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.questionnaire.domain.QueAnswer;
import com.tuoshecx.server.cms.questionnaire.service.QueAnswerService;
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
@RequestMapping("/manage/QueAnswer")
@Api(value = "/manage/QueAnswer", tags = "M-问卷调查答案API接口")
public class QueAnswerController {

    @Autowired
    private QueAnswerService queAnswerService;

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

}
