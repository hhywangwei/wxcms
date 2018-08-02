package com.tuoshecx.server.cms.api.manage.article;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.manage.article.form.*;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.article.domain.Article;
import com.tuoshecx.server.cms.article.service.ArticleService;
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
 * 文章管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/article")
@Api(value = "/manage/article", tags = "M-文章管理")
public class ArticleManageController {

    @Autowired
    private ArticleService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增文章")
    public ResultVo<Article> save(@Valid @RequestBody ArticleSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.save(form.toDomain(currentSiteId())));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改文章")
    public ResultVo<Article> update(@Valid @RequestBody ArticleUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.update(form.toDomain(currentSiteId())));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到文章")
    public ResultVo<Article> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @PutMapping(value = "{id}/release", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("发布文章")
    public ResultVo<Article> release(@PathVariable("id")String id){
        return ResultVo.success(service.release(id, currentSiteId()));
    }

    @PutMapping(value = "{id}/close", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("关闭文章")
    public ResultVo<Article> close(@PathVariable("id")String id){
        return ResultVo.success(service.close(id, currentSiteId()));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除文章")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(service.delete(id, currentSiteId())));
    }

    @PostMapping(value = "copy", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("拷贝文章")
    public ResultVo<Article> copy(@Valid @RequestBody ArticleCopyForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.copy(form.getId(), form.getToChannelId(), currentSiteId()));
    }

    @PostMapping(value = "move", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("移动文章")
    public ResultVo<Article> move(@Valid @RequestBody ArticleMoveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.move(form.getId(), form.getToChannelId(), currentSiteId()));
    }

    @PutMapping(value = "top", consumes = APPLICATION_JSON_UTF8_VALUE, produces =  APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("置顶文章")
    public ResultVo<Article> top(@Valid @RequestBody ArticleTopForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.top(form.getId(), form.getTop(), currentSiteId()));
    }

    @PutMapping(value = "showOrder", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("设置文章显示排序")
    public ResultVo<Article> showOrder(@Valid @RequestBody ArticleShowOrderForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.showOrder(form.getId(), form.getShowOrder(), currentSiteId()));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询文章")
    public ResultPageVo<Article> query(@RequestParam(required = false) @ApiParam("站点编号") String siteId,
                                       @RequestParam(required = false) @ApiParam("频道编号") String channelId,
                                       @RequestParam(required = false) @ApiParam("状态") String state,
                                       @RequestParam(required = false) @ApiParam("标题") String title,
                                       @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                       @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        try{
            Article.State stateObj = StringUtils.isBlank(state)? null: Article.State.valueOf(state);
            List<Article> data = service.query(siteId, channelId, stateObj, title, page * rows, rows);
            return new ResultPageVo.Builder<>(page, rows, data)
                    .count(true, () -> service.count(siteId, channelId, stateObj, title))
                    .build();
        }catch (Exception e){
            throw new BaseException("文章状态错误");
        }
    }

    private String currentSiteId(){
        return ManageCredentialContextUtils.currentSiteId();
    }
}
