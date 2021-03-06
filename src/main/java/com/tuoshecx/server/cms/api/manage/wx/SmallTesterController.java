package com.tuoshecx.server.cms.api.manage.wx;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.manage.wx.form.BindTesterForm;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.wx.component.devops.domain.SmallTester;
import com.tuoshecx.server.wx.component.devops.service.SmallTesterService;
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
 * 管理小程序测试账号
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/wx/smallTester")
@Api(value = "/manage/wx/smallTester", tags = "M-管理小程序测试账号")
public class SmallTesterController {

    @Autowired
    private SmallTesterService service;

    @PostMapping(value = "bind", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("绑定微信小程序测试账号")
    public ResultVo<SmallTester> bind(@Valid @RequestBody BindTesterForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.bindTest(form.toDomain(currentSiteId())));
    }

    @PutMapping(value = "{id}/unbind", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("解绑微信小程序测试账号")
    public ResultVo<OkVo> unbind(@PathVariable("id")String id){
        SmallTester o = service.get(id);
        if(!StringUtils.equals(o.getSiteId(), currentSiteId())){
            throw new BaseException("小程序测试员不存");
        }
        return  ResultVo.success(new OkVo(service.unbindTester(id, currentSiteId())));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询微信小程序测试账号")
    public ResultPageVo<SmallTester> query(@RequestParam(required = false) @ApiParam("测试员微信号") String wechatid,
                                           @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                           @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<SmallTester> data = service.query(currentSiteId(), wechatid, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data)
                .count(true, () -> service.count(currentSiteId(), wechatid))
                .build();
    }

    private String currentSiteId(){
        return ManageCredentialContextUtils.currentSiteId();
    }
}
