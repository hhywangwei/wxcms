package com.tuoshecx.server.cms.api.sys.site;

import com.tuoshecx.server.cms.api.sys.site.form.SiteResetPasswordForm;
import com.tuoshecx.server.cms.api.sys.site.form.SiteSaveForm;
import com.tuoshecx.server.cms.api.sys.site.form.SiteUpdateForm;
import com.tuoshecx.server.cms.api.vo.OkVo;
import com.tuoshecx.server.cms.api.vo.ResultPageVo;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import com.tuoshecx.server.cms.site.domain.Manager;
import com.tuoshecx.server.cms.site.domain.Site;
import com.tuoshecx.server.cms.site.service.ManagerService;
import com.tuoshecx.server.cms.site.service.SiteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 站点管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/site")
public class SiteSysController {

    @Autowired
    private SiteService service;

    @Autowired
    private ManagerService managerService;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增站点")
    public ResultVo<Site> save(@Valid @RequestBody SiteSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.save(form.toDomain(), form.getManager(), form.getPassword() ));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改站点")
    public ResultVo<Site> update(@Valid @RequestBody SiteUpdateForm form, BindingResult result){

        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.update(form.toDomain()));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到站点")
    public ResultVo<Site> get(@PathVariable("id") String id){
        return ResultVo.success(service.get(id));
    }

    @PutMapping(value = "{id}/open", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("发布站点")
    public ResultVo<Site> open(@PathVariable("id")String id){
        return ResultVo.success(service.open(id));
    }

    @PutMapping(value = "{id}/close", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("关闭站点")
    public ResultVo<Site> close(@PathVariable("id")String id){
        return ResultVo.success(service.close(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除站点")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(value = "{id}/manager", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到站点管理员")
    public ResultVo<Manager> getManager(@PathVariable("id")String id){
        return ResultVo.success(managerService.getSiteManager(id));
    }

    @PutMapping(value = "resetPassword", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("重置站点密码")
    public ResultVo<OkVo> resetPassword(@Valid @RequestBody SiteResetPasswordForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        Manager t = managerService.getSiteManager(form.getSiteId());
        return ResultVo.success(new OkVo(managerService.resetPassword(t.getId(), t.getSiteId(), form.getNewPassword())));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询站点")
    public ResultPageVo<Site> query(@RequestParam(required = false) @ApiParam("站点名称") String name,
                                       @RequestParam(required = false) @ApiParam("联系电话") String phone,
                                       @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                       @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<Site> data = service.query(name, phone, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data)
                .count(true, () -> service.count(name, phone))
                .build();
    }
}
