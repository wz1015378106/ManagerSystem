package com.system.manager.service.user;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.system.manager.common.AuthConstants;
import com.system.manager.common.PageUtils;
import com.system.manager.common.Query;
import com.system.manager.common.Result;
import com.system.manager.dao.user.UserDao;
import com.system.manager.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 分页查询用户
     *
     * @param query
     * @return
     */
    public PageUtils queryPage(Query query) {
        //获取当前分页信息
        Pageable pageable = query.getPageable(Sort.Direction.DESC, "createDate");
        Page<UserEntity> all = userDao.findAll(pageable);
        PageUtils pageUtils = new PageUtils(all);
        return pageUtils;
    }

    public Result login(Map<String, Object> params, HttpServletRequest request) {
        String userName = (String) params.get("userName");
        String password = (String) params.get("password");
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(password)) {
            return Result.error("用户名或密码不能为空");
        }
        UserEntity userEntity = userDao.login(userName, password);
        if (ObjectUtil.isNotNull(userEntity)) {
            if (!"0".equals(userEntity.getEnabledFlag())){
                return Result.error("当前用户已被冻结");
            }
            //将用户登录信息放入session中
            request.setAttribute(AuthConstants.USER_INFO,userEntity);
            setUserInfoToSession(request,userEntity);
            return Result.ok().put("data",userEntity);
        }
        return Result.error("用户名或密码错误");
    }

    private void setUserInfoToSession(HttpServletRequest request,UserEntity userEntity){
        HttpSession session = request.getSession();
        session.setAttribute(AuthConstants.USER_INFO,userEntity);
    }

    /**
     * 用户注册接口
     *
     * @param userEntity
     * @return
     */
    public Result register(UserEntity userEntity) {
        userEntity.setEnabledFlag("0");
        String dateStr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_FORMAT);
        userEntity.setCreateDate(dateStr);
        //校验必填项
        String userName = userEntity.getUserName();
        String password = userEntity.getPassword();
        String roleId = userEntity.getRoleId();
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(password) || StrUtil.isBlank(roleId)) {
            return Result.error("请检查未填写的必填项");
        }
        // TODO: 2021/7/28 自定义异常捕获
        UserEntity save = userDao.save(userEntity);
        return Result.ok().put("data", save);
    }

    /**
     * 修改用户信息
     *
     * @param userEntity
     * @return
     */
    public Result update(UserEntity userEntity) {
        boolean isExists = userDao.existsById(userEntity.getId());
        if (!isExists) {
            return Result.error("当前用户信息不存在");
        }
        // TODO: 2021/7/28 捕获自定义异常
        UserEntity save = userDao.save(userEntity);
        return Result.ok().put("data", save);
    }

    /**
     * 注销用户
     *
     * @param id
     * @return
     */
    public Result deleteById(String id) {
        boolean isExists = userDao.existsById(id);
        if (ObjectUtil.isNull(isExists)) {
            return Result.error("当前用户信息不存在");
        }
        userDao.deleteOneById(id);
        return Result.ok();
    }

}
