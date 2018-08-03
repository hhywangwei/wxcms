package com.tuoshecx.server.cms.api.sys.wx;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.sys.wx.form.SmallDeployForm;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.wx.small.devops.domain.SmallDeploy;
import com.tuoshecx.server.wx.small.devops.service.DeployService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 小程序发布API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/wx/deploy")
public class SmallDeployController {

    @Autowired
    private DeployService service;

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
}
