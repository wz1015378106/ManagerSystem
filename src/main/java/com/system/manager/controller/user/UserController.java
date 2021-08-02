package com.system.manager.controller.user;

import cn.hutool.core.util.ObjectUtil;
import com.system.manager.common.AuthConstants;
import com.system.manager.common.PageUtils;
import com.system.manager.common.Query;
import com.system.manager.common.Result;
import com.system.manager.entity.user.UserEntity;
import com.system.manager.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public Result login(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
        return userService.login(params,request,response);
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
     * 冻结用户
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    @ApiOperation(value = "冻结用户",response = Result.class)
    public Result deleteById(@PathVariable("id") String id){
        return userService.deleteById(id);
    }

    /**
     * 鉴权接口
     * @param request
     * @return
     */
    @GetMapping("auth")
    @ApiOperation(value = "鉴权，判断用户当前会话是否离线",response = Result.class)
    public Result auth(HttpServletRequest request){
        Object userInfo = request.getSession().getAttribute(AuthConstants.USER_INFO);
        return Result.ok().put("data",userInfo);
    }


    @GetMapping("logout")
    @ApiOperation(value = "注销")
    public Result logout(HttpServletRequest request,HttpServletResponse reponse){
        userService.loginOut(request,reponse);
        return Result.ok();
    }

    /**
     * 获取当前登录用户信息
     * @param request
     * @return
     */
    @GetMapping("getCurrentUserInfo")
    @ApiOperation(value = "获取当前登录用户登录信息")
    public Result getCurrentUserInfo(HttpServletRequest request){
        Object userInfo = request.getSession().getAttribute(AuthConstants.USER_INFO);
        if (ObjectUtil.isNull(userInfo)){
            return Result.error(401,"未登录");
        }
        return Result.ok().put("data",userInfo);
    }
    @ApiOperation(value = "解冻用户")
    @PutMapping("recoverUser/{userId}")
    public Result recoverUser(@PathVariable("userId") String userId){
        return userService.recoverUser(userId);
    }

    @ApiOperation(value = "重制密码")
    @PutMapping("resetPassword/{userId}")
    public Result resetPassword(@PathVariable("userId") String userId){
        return userService.resetPassword(userId);
    }

    /**
     * 查询当前用户菜单权限
     * @param session
     * @return
     */
    @ApiOperation(value = "查询当前用户菜单权限")
    @GetMapping("findCurrentUserMenu")
    public Result findCurrentUserMenu(HttpSession session){
        return userService.findCurrentUserMenu(session);
    }

}
