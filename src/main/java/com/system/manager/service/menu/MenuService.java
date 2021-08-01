package com.system.manager.service.menu;

import cn.hutool.core.util.StrUtil;
import com.system.manager.common.PageUtils;
import com.system.manager.common.Query;
import com.system.manager.dao.menu.MenuDao;
import com.system.manager.dao.menu.RoleMenuVDao;
import com.system.manager.entity.menu.RoleMenuVEntity;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuService {
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RoleMenuVDao roleMenuVDao;
    /**
     * 查视图，包含角色信息
     * @param query
     * @return
     */
    public PageUtils page(Query query){
        Pageable pageable = query.getPageable(Sort.Direction.DESC, "createDate");
        Specification<RoleMenuVEntity> specification = splingCondition(query);
        Page<RoleMenuVEntity> all = roleMenuVDao.findAll(specification, pageable);
        PageUtils pageUtils = new PageUtils(all);
        return pageUtils;
    }

    /**
     * 动态拼接查询条件
     * @param query
     * @return
     */
    private Specification<RoleMenuVEntity> splingCondition(Query query){
        String menuCode = (String)query.get("menuCode");
        String menuName = (String)query.get("menuName");
        String roleId = (String)query.get("roleId");
        Specification<RoleMenuVEntity> specification = new Specification<RoleMenuVEntity>() {
            @Override
            public Predicate toPredicate(Root<RoleMenuVEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StrUtil.isNotBlank(menuCode)){
                    predicates.add(cb.like(root.<String>get("menuCode"),menuCode));
                }
                if (StrUtil.isNotBlank(menuName)){
                    predicates.add(cb.like(root.<String>get("menuName"),menuName));
                }
                if (StrUtil.isNotBlank(roleId)){
                    predicates.add(cb.equal(root.<String>get("roleId"),roleId));
                }
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                return predicate;
            }
        };
        return specification;
    }
}
