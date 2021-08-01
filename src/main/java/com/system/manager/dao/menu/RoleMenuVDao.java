package com.system.manager.dao.menu;

import com.system.manager.entity.menu.RoleMenuVEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenuVDao extends JpaRepository<RoleMenuVEntity,String>, JpaSpecificationExecutor<RoleMenuVEntity> {
}
