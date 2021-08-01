package com.system.manager.controller.menu;

import com.system.manager.common.PageUtils;
import com.system.manager.common.Query;
import com.system.manager.common.Result;
import com.system.manager.service.menu.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("menu")
@Api(tags = "菜单")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 分页查询
     * @param params
     * @return
     */
    @PostMapping("page")
    @ApiOperation(value = "分页查询")
    public Result page(@RequestParam Map<String,Object> params){
        PageUtils page = menuService.page(new Query(params));
        return Result.ok().put("page",page);
    }
}
