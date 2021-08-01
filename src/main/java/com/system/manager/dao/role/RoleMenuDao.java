package com.system.manager.dao.role;

import com.system.manager.entity.role.RoleMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleMenuDao extends JpaRepository<RoleMenuEntity,String>, JpaSpecificationExecutor<RoleMenuEntity> {

}
