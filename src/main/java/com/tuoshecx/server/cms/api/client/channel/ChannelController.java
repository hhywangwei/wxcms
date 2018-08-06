package com.tuoshecx.server.cms.api.client.channel;

import com.tuoshecx.server.cms.api.client.ClientCredentialContextUtils;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.channel.domain.Channel;
import com.tuoshecx.server.cms.channel.service.ChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 频道查询API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/channel")
@Api(value = "/client/channel", tags = "C-频道查询API接口")
public class ChannelController {

    @Autowired
    private ChannelService service;

    @GetMapping(value = "{id}/children", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询子频道，一级频道用root查询")
    public ResultVo<List<Channel>> queryChildren(@PathVariable("id")String id){
        return ResultVo.success(service.queryChildren(getSiteId(), id, Channel.State.OPEN, StringUtils.EMPTY));
    }

    private String getSiteId(){
        return ClientCredentialContextUtils.currentSiteId();
    }
}
