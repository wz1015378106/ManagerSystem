package com.system.manager.service.role;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.system.manager.common.PageUtils;
import com.system.manager.common.Query;
import com.system.manager.common.Result;
import com.system.manager.dao.role.RoleDao;
import com.system.manager.dao.role.RoleMenuDao;
import com.system.manager.dao.user.UserDao;
import com.system.manager.entity.role.RoleEntity;
import com.system.manager.entity.role.RoleMenuEntity;
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
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 角色Service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private UserDao userDao;
    /**
     * 分页查询角色
     * @param query
     * @return
     */
    public Result page(Query query){
        Pageable pageable = query.getPageable(Sort.Direction.DESC, "createDate");
        Specification<RoleEntity> specification = splingCondition(query);
        Page<RoleEntity> all = roleDao.findAll(specification,pageable);
        PageUtils pageUtils = new PageUtils(all);
        return Result.ok().put("page",pageUtils);
    }

    private Specification<RoleEntity> splingCondition(Query query){
        String roleCode = (String) query.get("roleCode");
        String roleName = (String) query.get("roleName");
        Specification<RoleEntity> specification = new Specification() {
            List<Predicate> predicates = new ArrayList<>();
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder cb) {
                if (StrUtil.isNotBlank(roleCode)){
                    predicates.add(cb.like(root.<String>get("roleCode"),roleCode));
                }
                if (StrUtil.isNotBlank(roleName)){
                    predicates.add(cb.like(root.<String>get("roleName"),roleName));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return specification;
    }

    /**
     * 新增角色
     * @return
     */
    public Result addRole(RoleEntity roleEntity, HttpServletRequest request){
        //写创建人
        UserEntity user = (UserEntity)request.getSession().getAttribute("userInfo");
        if (ObjectUtil.isNull(user)){
            return Result.error(401,"未登录");
        }
        //查询当前角色编码是否存在
        String roleCode = roleEntity.getRoleCode();
        int countByCode = roleDao.findCountByCode(roleCode);
        if (countByCode>0){
            return Result.error("当前角色编码已存在");
        }
        String userId = user.getId();
        String date = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN);
        roleEntity.setCreateBy(userId);
        roleEntity.setEnabledFlag("0");
        roleEntity.setCreateDate(date);
        RoleEntity save = roleDao.save(roleEntity);
        //添加对应角色与菜单对应关系
        List<String> menuList = roleEntity.getMenuList();
        List<RoleMenuEntity> roleMenuEntityList = new ArrayList<>(menuList.size());
        //入库后才会生成uuid
        String roleId = save.getId();
        menuList.stream().forEach(menuId->{
            RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
            roleMenuEntity.setRoleId(roleId);
            roleMenuEntity.setMenuId(menuId);
            roleMenuEntityList.add(roleMenuEntity);
        });
        roleMenuDao.saveAll(roleMenuEntityList);
        return Result.ok();
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    public Result deleteRole(String roleId){
        if (StrUtil.isBlank(roleId)){
            return Result.ok();
        }
        //先删除角色和菜单关联关系
        roleMenuDao.deleteByRoleId(roleId);
        //删除用户主角色，副角色暂时不启用（需要再做优化）
        List<UserEntity> userEntities = userDao.findUserByRoleId(roleId);
        List<UserEntity> newUserEntities = new ArrayList<>(userEntities.size());
        userEntities.forEach(user->{
            user.setRoleId(null);
            newUserEntities.add(user);
        });
        userDao.saveAll(newUserEntities);
        //删除当前角色
        roleDao.deleteById(roleId);
        return Result.ok();
    }

    /**
     * 更新角色
     * @param roleEntity
     * @return
     */
    public Result updateRole(RoleEntity roleEntity){
        String roleId = roleEntity.getId();
        if (StrUtil.isBlank(roleId)){
            return Result.error("角色ID不能为空");
        }
        //修改角色
        RoleEntity save = roleDao.save(roleEntity);
        //先删除角色菜单关联关系， 再更新关联关系
        List<String> menuList = roleEntity.getMenuList();
        if (CollUtil.isNotEmpty(menuList)){
            roleMenuDao.deleteByRoleId(roleId);
            String newRoleId = save.getId();
            //构造新的关联关系
            List<RoleMenuEntity> list = new ArrayList<RoleMenuEntity>(menuList.size());
            menuList.forEach(menuId->{
                RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
                roleMenuEntity.setMenuId(menuId);
                roleMenuEntity.setRoleId(roleId);
                list.add(roleMenuEntity);
            });
            roleMenuDao.saveAll(list);
        }
        return Result.ok();
    }
}
