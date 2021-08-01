package com.system.manager.dao.user;

import com.system.manager.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<UserEntity,String>, JpaSpecificationExecutor<UserEntity> {
    /*@Query(value = "select count(*) from user where USER_NAME = :userName and PASSWORD = :password",nativeQuery = true)
    int login(@Param("userName") String userName,@Param("password") String password);*/
    @Query(value = "select * from user where USER_NAME = :userName and PASSWORD = :password",nativeQuery = true)
    UserEntity login(@Param("userName") String userName,@Param("password") String password);
    @Modifying
    @Query(value = "update user set ENABLED_FLAG = '1' where ID = :id",nativeQuery = true)
    void deleteOneById(@Param("id")String id);
    @Query(value = "select * from user where USER_CODE = ?1 and ENABLED_FLAG = '0'",nativeQuery = true)
    UserEntity findUserByUserCode(String userCode);

    /**
     * 解冻用户
     * @param id
     */
    @Modifying
    @Query(value = "update user set ENABLED_FLAG = '0' where ID = ?1",nativeQuery = true)
    void recoverUser(String id);

    /**
     * 删除角色用户同步进行更新
     * @return
     */
    @Query(value = "select * from user where MAIN_ROLE_ID = ?1",nativeQuery = true)
    List<UserEntity> findUserByRoleId(String roleId);
}
