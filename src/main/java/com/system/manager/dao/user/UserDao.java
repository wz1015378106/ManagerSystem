package com.system.manager.dao.user;

import com.system.manager.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity,String>, JpaSpecificationExecutor<UserEntity> {
    /*@Query(value = "select count(*) from user where USER_NAME = :userName and PASSWORD = :password",nativeQuery = true)
    int login(@Param("userName") String userName,@Param("password") String password);*/
    @Query(value = "select * from user where USER_NAME = :userName and PASSWORD = :password",nativeQuery = true)
    UserEntity login(@Param("userName") String userName,@Param("password") String password);
    @Modifying
    @Query(value = "update user set ENABLED_FLAG = '1' where ID = :id",nativeQuery = true)
    void deleteOneById(@Param("id")String id);
}
