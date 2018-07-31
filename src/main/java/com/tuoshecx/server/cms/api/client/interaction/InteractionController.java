package com.tuoshecx.server.cms.api.client.interaction;

import com.tuoshecx.server.cms.api.client.ClientCredentialContextUtils;
import com.tuoshecx.server.cms.api.client.interaction.form.InteractionSaveForm;
import com.tuoshecx.server.cms.api.client.interaction.form.InteractionUpdateForm;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.interaction.domain.Interaction;
import com.tuoshecx.server.cms.interaction.service.InteractionService;
import com.tuoshecx.server.cms.security.Credential;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 政民互动API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/interaction")
@Api(value = "/client/interaction", tags = "M-政民互动API接口")
public class InteractionController {

    @Autowired
    private InteractionService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增政民活动")
    public ResultVo<Interaction> save(@Valid @RequestBody InteractionSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.save(form.toDomain(getCredential().getId())));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改政民互动")
    public ResultVo<Interaction> update(@Valid @RequestBody InteractionUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.update(form.toDomain(getCredential().getId())));
    }

    private Credential getCredential(){
        return ClientCredentialContextUtils.getCredential();
    }
}
