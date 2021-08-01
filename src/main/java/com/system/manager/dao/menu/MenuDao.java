package com.system.manager.dao.menu;

import com.system.manager.entity.menu.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao extends JpaRepository<MenuEntity, String>, JpaSpecificationExecutor<MenuEntity> {
    /**
     * 多个id查询
     * @param ids
     * @return
     */
    @Query(value = "select * from menu where ID in (?1)", nativeQuery = true)
    List<MenuEntity> findMenuListById(List<String> ids);
}
