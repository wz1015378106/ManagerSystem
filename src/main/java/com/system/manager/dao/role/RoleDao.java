package com.system.manager.dao.role;

import com.system.manager.entity.role.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<RoleEntity,String>, JpaSpecificationExecutor<RoleEntity> {
    @Query(value = "select count(*) from role where ROLE_CODE = ?1",nativeQuery = true)
    int findCountByCode(String roleCode);
}
