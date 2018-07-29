package com.tuoshecx.server.cms.api.manage.site;

import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.manage.site.form.SiteManageUpdateForm;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.site.domain.Site;
import com.tuoshecx.server.cms.site.service.SiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/manage/site")
@Api(tags = "/manage/site", value = "M-站点管理")
public class SiteManageController {

    @Autowired
    private SiteService service;

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到本站点")
    public ResultVo<Site> get(){
        return ResultVo.success(service.get(currentSiteId()));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改站点信息")
    public ResultVo<Site> update(@Valid @RequestBody SiteManageUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.update(form.toDomain(currentSiteId())));
    }

    private String currentSiteId(){
        return ManageCredentialContextUtils.currentSiteId();
    }
}
