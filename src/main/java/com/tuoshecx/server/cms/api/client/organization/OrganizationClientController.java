package com.tuoshecx.server.cms.api.client.organization;

import com.tuoshecx.server.cms.api.client.ClientCredentialContextUtils;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.site.domain.Organization;
import com.tuoshecx.server.cms.site.service.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/client/organization")
@Api(value = "/client/organization", tags = "C-组织机构查询")
public class OrganizationClientController {

    @Autowired
    private OrganizationService service;

    @GetMapping(value = "{id}/children", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询下级组织机构，root顶级组织机构")
    public ResultVo<List<Organization>> queryChildren(@PathVariable("id")String id){
        return ResultVo.success(service.queryChildren(getSiteId(), id));
    }

    private String getSiteId(){
        return ClientCredentialContextUtils.currentSiteId();
    }
}
