package com.system.manager.service.role;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.system.manager.common.PageUtils;
import com.system.manager.common.Query;
import com.system.manager.common.Result;
import com.system.manager.dao.role.RoleDao;
import com.system.manager.dao.role.RoleMenuDao;
import com.system.manager.entity.role.RoleEntity;
import com.system.manager.entity.role.RoleMenuEntity;
import com.system.manager.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    /**
     * 分页查询角色
     * @param query
     * @return
     */
    public Result page(Query query){
        Pageable pageable = query.getPageable(Sort.Direction.DESC, "createDate");
        Page<RoleEntity> all = roleDao.findAll(pageable);
        PageUtils pageUtils = new PageUtils(all);
        return Result.ok().put("page",pageUtils);
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
}
