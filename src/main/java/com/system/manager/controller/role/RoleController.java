package com.system.manager.controller.role;

import com.system.manager.common.Query;
import com.system.manager.common.Result;
import com.system.manager.entity.role.RoleEntity;
import com.system.manager.service.role.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("role")
@Api(tags = "角色信息")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @ApiOperation(value = "新增角色")
    @PostMapping("add")
    public Result addRole(@RequestBody RoleEntity roleEntity, HttpServletRequest request) {
        return roleService.addRole(roleEntity,request);
    }
    @ApiOperation(value = "角色列表")
    @GetMapping("page")
    public Result page(@RequestParam Map<String,Object> params){
        Result page = roleService.page(new Query(params));
        return page;
    }
    @ApiOperation(value = "更新")
    @PutMapping("update")
    public Result update(@RequestBody RoleEntity roleEntity){
        return roleService.updateRole(roleEntity);
    }
    @ApiOperation(value = "删除角色")
    @DeleteMapping("delete/{roleId}")
    public Result delete(@PathVariable("roleId") String roleId){
        return roleService.deleteRole(roleId);
    }
}
