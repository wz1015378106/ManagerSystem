package com.system.manager.dao.menu;

import com.system.manager.entity.menu.RoleMenuVEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenuVDao extends JpaRepository<RoleMenuVEntity,String>, JpaSpecificationExecutor<RoleMenuVEntity> {
    @Query(value = "select * from role_menu_v where ROLE_ID = ?1",nativeQuery = true)
    RoleMenuVEntity findByRoleId(String roleId);
}
