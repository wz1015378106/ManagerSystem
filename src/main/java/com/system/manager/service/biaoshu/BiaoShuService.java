package com.system.manager.service.biaoshu;

import com.system.manager.dao.biaoshu.BiaoShuDao;
import com.system.manager.entity.biaoshu.BiaoShuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: wangzhi
 * @Title: BiaoShuService
 * @Description:
 * @date: 2021/8/1 17:16
 */
@Service
public class BiaoShuService {
    @Autowired
    private BiaoShuDao biaoShuDao;

    public void add(BiaoShuEntity biaoShuEntity){
        biaoShuDao.save(biaoShuEntity);
    }
}
