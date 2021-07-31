package com.sysytem.manager.controller.role;

import com.sysytem.manager.common.Result;
import com.sysytem.manager.entity.role.RoleEntity;
import com.sysytem.manager.service.role.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @ApiOperation(value = "新增角色")
    @PostMapping("add")
    public Result addRole(@RequestBody RoleEntity roleEntity, HttpServletRequest request) {
        return roleService.addRole(roleEntity,request);
    }
}
