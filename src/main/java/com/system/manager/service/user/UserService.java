package com.system.manager.service.user;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

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
        Specification<UserEntity> specification = splingCondition(query);
        Page<UserEntity> all = userDao.findAll(specification,pageable);
        PageUtils pageUtils = new PageUtils(all);
        return pageUtils;
    }

    /**
     * 动态拼接查询条件
     * @param query
     * @return
     */
    public Specification<UserEntity> splingCondition(Query query){
        String userName = (String) query.get("userName");
        String userCode = (String) query.get("userCode");
        Specification<UserEntity> specification = new Specification<UserEntity>() {
            @Override
            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StrUtil.isNotBlank(userName)){
                    predicates.add(cb.like(root.<String>get("userName"),userName));
                }
                if (StrUtil.isNotBlank(userCode)){
                    predicates.add(cb.like(root.<String>get("userCode"),userCode));
                }
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                return predicate;
            }
        };
        return specification;
    }

    /**
     * 重制密码
     * @param userId
     * @return
     */
    public Result resetPassword(String userId){
        Optional<UserEntity> userOp = userDao.findById(userId);
        UserEntity userEntity = userOp.get();
        if (ObjectUtil.isNull(userEntity)){
            return Result.error("当前用户不存在");
        }
        String newPassword = RandomUtil.randomString(8);
        userEntity.setPassword(newPassword);
        userDao.save(userEntity);
        return Result.ok().put("data",newPassword);
    }

    public Result login(Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
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
            //设置自动登录并将用户登录信息放入session中
            request.setAttribute(AuthConstants.USER_INFO,userEntity);
            setUserInfoToSession(request,userEntity);
            Cookie cookie = new Cookie(AuthConstants.COOKIE_USER_CODE,userEntity.getUserCode());
            //设置cookie持久化时间
            cookie.setMaxAge(15*24*60*60);
            //设置项目下所有请求都携带该cookie
            cookie.setPath(request.getContextPath());
            //向客户端发送cookie
            response.addCookie(cookie);
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
        String userCode = userEntity.getUserCode();
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(password) || StrUtil.isBlank(roleId) || StrUtil.isBlank(userCode)) {
            return Result.error("请检查未填写的必填项");
        }
        //工号不重复验证
        UserEntity userByUserCode = userDao.findUserByUserCode(userCode);
        if (ObjectUtil.isNotNull(userByUserCode)){
            return Result.error("工号重复，请检查");
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
     * 冻结用户
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

    /**
     * 注销用户
     * @return
     */
    public Result loginOut(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute(AuthConstants.USER_INFO);
        Cookie cookie = new Cookie(AuthConstants.COOKIE_USER_CODE,"");
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
        return Result.ok();
    }

    /**
     * 解冻用户
     */
    public Result recoverUser(String roleId){
        userDao.recoverUser(roleId);
        return Result.ok();
    }
}
