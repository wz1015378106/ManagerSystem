package com.system.manager.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.system.manager.common.AuthConstants;
import com.system.manager.common.Result;
import com.system.manager.dao.user.UserDao;
import com.system.manager.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户登录拦截
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private UserDao userDao;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取Cookie
        Cookie[] cookies = request.getCookies();
        //不存在cookie信息，重定向到登录页面
        if (null == cookies){
            setReturn(response, AuthConstants.UN_LOGIN_CODE,"未登录");
            return false;
        }
        //取cookie中的员工工号
        String userCode = null;
        for (Cookie cookie :
                cookies) {
            String cookieName = cookie.getName();
            if (StrUtil.equals(AuthConstants.COOKIE_USER_CODE,cookieName)){
                userCode = cookie.getValue();
                break;
            }
        }
        if (StrUtil.isBlank(userCode)){
            setReturn(response,AuthConstants.UN_LOGIN_CODE,"未登录");
            return false;
        }
        //根据工号查询当前用户
        UserEntity user = userDao.findUserByUserCode(userCode);
        if (ObjectUtil.isNull(user)){
            //当前用户不存在
            setReturn(response,AuthConstants.ERR_CODE,"当前用户不存在或已失效");
        }
        //seesion过期，重新设置session
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(AuthConstants.USER_INFO);
        if (null == attribute){
            session.setAttribute(AuthConstants.USER_INFO,user);
        }
        return true;
    }
    //返回错误信息
    private static void setReturn(HttpServletResponse response, int status, String msg) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        //UTF-8编码
        httpResponse.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        Result build = Result.error(status, msg);
        String json = JSON.toJSONString(build);
        httpResponse.getWriter().print(json);
    }
}

