package com.tuoshecx.server.cms.api.client.comment;

import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.sns.domain.Comment;
import com.tuoshecx.server.cms.sns.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 查询评论API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/comment")
@Api(value = "/client/comment", tags = "C-查看评论API接口")
public class CommentController {

    @Autowired
    private CommentService service;

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询评论")
    public ResultPageVo<Comment> query(@RequestParam String refId,
                                       @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                       @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<Comment> data = service.query(refId, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data)
                .build();
    }
}
