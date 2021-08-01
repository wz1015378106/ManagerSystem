package com.system.manager.dao.role;

import com.system.manager.entity.role.RoleMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RoleMenuDao extends JpaRepository<RoleMenuEntity,String>, JpaSpecificationExecutor<RoleMenuEntity> {

    /**
     * 根据角色ID删除关联关系
     * @param roleId
     */
    @Modifying
    @Query(value = "delete * from role_menu where ROLE_ID = ?1",nativeQuery = true)
    void deleteByRoleId(String roleId);
}
