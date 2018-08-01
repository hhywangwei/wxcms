package com.tuoshecx.server.cms.api.client.article;

import com.tuoshecx.server.cms.api.client.ClientCredentialContextUtils;
import com.tuoshecx.server.cms.api.client.article.vo.ArticleVo;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.article.domain.Article;
import com.tuoshecx.server.cms.article.service.ArticleService;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.sns.domain.Counter;
import com.tuoshecx.server.cms.sns.service.CounterService;
import com.tuoshecx.server.cms.sns.service.GoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 文章显示API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/article")
@Api(value = "/client/article", tags = "C-文章查询接口")
public class ArticleController {

    @Autowired
    private ArticleService service;

    @Autowired
    private CounterService counterService;

    @Autowired
    private GoodService goodService;

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询文章")
    public ResultPageVo<Article> query(@RequestParam @ApiParam("频道编号") String channelId,
                                       @RequestParam(required = false) @ApiParam("文章标题") String title,
                                       @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                       @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<Article> data = service.query(getSiteId(), channelId, Article.State.RELEASE, title, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data).build();
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("文章")
    public ResultVo<ArticleVo> get(@PathVariable("id")String id){
        Article t = service.get(id);
        return ResultVo.success(counterService.getOptional(id)
                .map(e -> new ArticleVo(t, e.getReadCount(), e.getGoodCount()))
                .orElseGet(() -> new ArticleVo(t, 0, 0)));
    }

    @PostMapping(value = "{id}/good", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("点赞文章")
    public ResultVo<OkVo> good(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(goodService.good(getCredential().getId(), id)));
    }

    private Credential getCredential(){
        return ClientCredentialContextUtils.getCredential();
    }

    private String getSiteId(){
        return ClientCredentialContextUtils.currentSiteId();
    }
}
