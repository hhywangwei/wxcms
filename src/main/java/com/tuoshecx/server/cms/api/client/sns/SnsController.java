package com.tuoshecx.server.cms.api.client.sns;

import com.tuoshecx.server.cms.api.client.ClientCredentialContextUtils;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.sns.domain.Counter;
import com.tuoshecx.server.cms.sns.service.CounterService;
import com.tuoshecx.server.cms.sns.service.GoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
/**
 * 文章评论等统计数
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/sns")
@Api(value = "/client/sns", tags = "C-文章等统计数")
public class SnsController {
    private static final Counter EMPTY_COUNTER = empty();

    @Autowired
    private CounterService counterService;

    @Autowired
    private GoodService goodService;

    @GetMapping(value = "counter/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到统计数")
    public ResultVo<Counter> get(@PathVariable("id") String id){
        Optional<Counter> optional = counterService.getOptional(id);
        return ResultVo.success(optional.orElse(EMPTY_COUNTER));
    }

    private static Counter empty(){
        Counter t = new Counter();

        t.setCommentCount(0);
        t.setGoodCount(0);
        t.setReadCount(0);
        t.setShareCount(0);

        return t;
    }

    @PutMapping(value = "good/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("点赞")
    public ResultVo<OkVo> good(@PathVariable("id")String id){
        return  ResultVo.success(new OkVo(goodService.good(getCredential().getId(), id)));
    }

    private Credential getCredential(){
        return ClientCredentialContextUtils.getCredential();
    }
}
