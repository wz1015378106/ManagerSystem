package com.system.manager.dao.biaoshu;

import com.system.manager.entity.biaoshu.BiaoShuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author: wangzhi
 * @Title: BiaoShuDao
 * @Description:
 * @date: 2021/8/1 17:10
 */
@Repository
public interface BiaoShuDao extends JpaRepository<BiaoShuEntity,Integer>, JpaSpecificationExecutor<BiaoShuEntity> {
}
