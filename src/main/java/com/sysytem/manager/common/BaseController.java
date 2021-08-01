package com.sysytem.manager.common;

import com.sysytem.manager.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author: wangzhi
 * @Title: BaseController
 * @Description:
 * @date: 2021/7/31 16:49
 */
@Controller
public class BaseController {
    @Autowired
    private HttpServletRequest request;

    private HttpSession getSession(){
        HttpSession session = request.getSession();
        return session;
    }

    //获取用户名称
    public String getUserName() {
        return getUser().getUserName();
    }


    public UserEntity getUser(){
        HttpSession session = getSession();
        UserEntity user = (UserEntity) session.getAttribute(AuthConstants.USER_INFO);
        return user;
    }

}
