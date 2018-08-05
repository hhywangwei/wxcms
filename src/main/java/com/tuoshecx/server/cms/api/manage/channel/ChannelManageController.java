package com.tuoshecx.server.cms.api.manage.channel;

import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.manage.channel.form.ChannelSaveForm;
import com.tuoshecx.server.cms.api.manage.channel.form.ChannelUpdateForm;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.channel.domain.Channel;
import com.tuoshecx.server.cms.channel.service.ChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Collection;
import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 频道管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/channel")
@Api(value = "/manage/channel", tags = "M-频道管理API接口")
public class ChannelManageController {

    @Autowired
    private ChannelService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增频道")
    public ResultVo<Channel> save(@Valid @RequestBody ChannelSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.save(form.toDomain(currentSiteId())));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改频道")
    public ResultVo<Channel> update(@Valid @RequestBody ChannelUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.update(form.toDomain(currentSiteId())));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到频道")
    public ResultVo<Channel> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @PutMapping(value = "{id}/open", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("发布频道")
    public ResultVo<Channel> open(@PathVariable("id")String id){
        return ResultVo.success(service.open(id, currentSiteId()));
    }

    @PutMapping(value = "{id}/close", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("关闭频道")
    public ResultVo<Channel> close(@PathVariable("id")String id){
        return ResultVo.success(service.close(id, currentSiteId()));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除频道")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(service.delete(id, currentSiteId())));
    }

    @GetMapping(value = "{id}/children", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询子频道")
    public ResultVo<Collection<Channel>> queryChildren(@PathVariable("id")String id){

        Collection<Channel> children = StringUtils.equals(id, "0")?
                Collections.singletonList(buildRootChannel())
                : service.queryChildren(currentSiteId(), id, null);

        return ResultVo.success(children);
    }

    private Channel buildRootChannel(){
        Channel channel = new Channel();
        channel.setId("root");
        channel.setName("频道");
        return channel;
    }

    private String currentSiteId(){
        return ManageCredentialContextUtils.currentSiteId();
    }
}
