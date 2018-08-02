package com.tuoshecx.server.cms.api.manage.comment;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.sns.domain.Comment;
import com.tuoshecx.server.cms.sns.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 评论管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/comment")
@Api(value = "/manage/comment", tags = "M-评论管理API接口")
public class CommentManageController {

    @Autowired
    private CommentService service;

    @PutMapping(value = "{id}/pass", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("评论审核通过")
    public ResultVo<Comment> pass(@PathVariable("id")String id){
        return ResultVo.success(service.pass(id, currentSiteId()));
    }

    @PutMapping(value = "{id}/refuse", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("评论审核拒绝")
    public ResultVo<Comment> refuse(@PathVariable("id")String id){
        return ResultVo.success(service.refuse(id, currentSiteId()));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询评论")
    public ResultPageVo<Comment> query(@RequestParam(required = false) @ApiParam("用户昵称") String nickname,
                                       @RequestParam(required = false) @ApiParam("关联信息") String detail,
                                       @RequestParam(required = false) @ApiParam("状态") String state,
                                        @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                        @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        try{
            Comment.State stateObj = StringUtils.isBlank(state)? null: Comment.State.valueOf(state);
            List<Comment> data = service.query(nickname, detail, stateObj, page * rows, rows);
            return new ResultPageVo.Builder<>(page, rows, data)
                    .count(true, () -> service.count(nickname, detail, stateObj))
                    .build();
        }catch (Exception e){
            throw new BaseException("评论状态错误");
        }

    }

    private String currentSiteId(){
        return ManageCredentialContextUtils.currentSiteId();
    }
}
