package com.sysytem.manager.controller.user;

import com.sysytem.manager.common.PageUtils;
import com.sysytem.manager.common.Query;
import com.sysytem.manager.common.Result;
import com.sysytem.manager.entity.user.UserEntity;
import com.sysytem.manager.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("user")
@Api(tags = "用户信息")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("page")
    @ApiOperation(value = "分页查询用户信息",response = UserEntity.class)
    public Result query(@RequestParam Map<String,Object> params){
        PageUtils page = userService.queryPage(new Query(params));
        return Result.ok().put("page",page);
    }

    /**
     * 登录
     * @param params
     * @param request
     * @return
     */
    @PostMapping("login")
    @ApiOperation(value = "用户登录",response = Result.class)
    public Result login(@RequestBody Map<String,Object> params, HttpServletRequest request){
        return userService.login(params,request);
    }

    /**
     * 注册
     * @param userEntity
     * @return
     */
    @PostMapping("register")
    @ApiOperation(value = "用户注册",response = Result.class)
    public Result register(@RequestBody UserEntity userEntity){
        return userService.register(userEntity);
    }

    /**
     * 更新用户信息
     * @param userEntity
     * @return
     */
    @PutMapping("update")
    @ApiOperation(value = "更新用户信息",response = Result.class)
    public Result update(@RequestBody UserEntity userEntity){
        return userService.update(userEntity);
    }

    /**
     * 注销用户
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    @ApiOperation(value = "注销用户",response = Result.class)
    public Result deleteById(@PathVariable("id") String id){
        return userService.deleteById(id);
    }

    @GetMapping("auth")
    @ApiOperation(value = "鉴权，判断用户当前会话是否离线",response = Result.class)
    public Result auth(HttpServletRequest request){
        Object userInfo = request.getSession().getAttribute("userInfo");
        return Result.ok().put("data",userInfo);
    }

}
