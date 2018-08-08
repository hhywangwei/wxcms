package com.tuoshecx.server.cms.api.manage.organization;

import com.tuoshecx.server.cms.api.manage.ManageCredentialContextUtils;
import com.tuoshecx.server.cms.api.manage.organization.form.OrganizationSaveForm;
import com.tuoshecx.server.cms.api.manage.organization.form.OrganizationUpdateForm;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.site.domain.Organization;
import com.tuoshecx.server.cms.site.service.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 组织构架管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/organization")
@Api(value = "/manage/organization", tags = "M-组织机构管理API接口")
public class OrganizationManageController {

    @Autowired
    private OrganizationService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增组织机构")
    public ResultVo<Organization> save(@Valid @RequestBody OrganizationSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.save(form.toDomain(currentSiteId())));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改组织机构")
    public ResultVo<Organization> update(@Valid @RequestBody OrganizationUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.update(form.toDomain(currentSiteId())));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到组织机构")
    public ResultVo<Organization> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除组织机构")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(service.delete(id, currentSiteId())));
    }

    @GetMapping(value = "{id}/children", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询子组织机构")
    public ResultVo<Collection<Organization>> queryChildren(@PathVariable("id")String parentId){
        Collection<Organization> children = StringUtils.equals(parentId, "0")?
                Collections.singletonList(buildRootChannel())
                : service.queryChildren(currentSiteId(), parentId);
        return ResultVo.success(children);
    }

    private Organization buildRootChannel(){
        Organization organ = new Organization();
        organ.setId("root");
        organ.setName("组织机构");
        return organ;
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询组织机构")
    public ResultPageVo<Organization> query(@RequestParam(required = false) @ApiParam("频道机构名称") String name,
                                       @RequestParam(defaultValue = "root") @ApiParam("上级机构") String parentId,
                                       @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                       @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){


        String siteId = currentSiteId();
        List<Organization> data = service.query(siteId, parentId, name, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data)
                .count(true, () -> service.count(siteId, parentId, name))
                .build();
    }

    private String currentSiteId(){
        return ManageCredentialContextUtils.currentSiteId();
    }
}
