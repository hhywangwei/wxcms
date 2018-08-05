package com.tuoshecx.server.cms.api.sys.wx;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.sys.wx.form.SmallDeployForm;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.wx.small.devops.domain.SmallDeploy;
import com.tuoshecx.server.wx.small.devops.service.DeployService;
import com.tuoshecx.server.wx.small.devops.service.SmallDeployService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 小程序发布API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/wx/deploy")
@Api(value = "/sys/wx/deploy", tags = "发布小程序")
public class SmallDeployController {

    @Autowired
    private DeployService service;

    @Autowired
    private SmallDeployService deployService;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "发布小程序")
    public ResultVo<SmallDeploy> deploy(@Validated @RequestBody SmallDeployForm form, BindingResult result){

        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        SmallDeploy t = service.deploy(form.getSiteId(), form.getTemplateId());
        if(isAudit(t.getState())){
            throw new BaseException("小程序正在等待审核");
        }

        service.setDomain(t.getId());
        t = service.programCommit(t.getId());
        if(form.getCommit()){
            t = service.submitAudit(t.getId());
        }
        return ResultVo.success(t);
    }

    private boolean isAudit(String state){
        return StringUtils.equals(state, "AUDIT");
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询发布信息")
    public ResultPageVo<SmallDeploy> query(@RequestParam(required = false) String siteId,
                                           @RequestParam(required = false) String appid,
                                           @RequestParam(required = false) Integer templateId,
                                           @RequestParam(required = false) String state,
                                           @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                           @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<SmallDeploy> data = deployService.query(siteId, appid, templateId, state, page * rows, rows);

        return new ResultPageVo.Builder<>(page, rows, data)
                .count(true, () -> deployService.count(siteId, appid, templateId, state))
                .build();
    }
}
