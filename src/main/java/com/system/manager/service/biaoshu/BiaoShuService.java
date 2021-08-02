package com.system.manager.service.biaoshu;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.system.manager.common.MyException;
import com.system.manager.common.PageUtils;
import com.system.manager.common.Query;
import com.system.manager.dao.biaoshu.BiaoShuDao;
import com.system.manager.entity.biaoshu.BiaoShuEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangzhi
 * @Title: BiaoShuService
 * @Description:
 * @date: 2021/8/1 17:16
 */
@Service
@Slf4j
public class BiaoShuService {
    @Autowired
    private BiaoShuDao biaoShuDao;

    private Specification<BiaoShuEntity> getSpecification(Query query){
        Specification<BiaoShuEntity> specification = new Specification<BiaoShuEntity>() {
            @Override
            public Predicate toPredicate(Root<BiaoShuEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (StrUtil.isNotBlank(query.get("userId").toString())){
                    list.add(criteriaBuilder.equal(root.get("userId"),query.get("userId")));
                }
                Predicate[] predicates = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(predicates));
            }
        };
        return specification;
    }

    /**
     * 新增标书
     * @param biaoShuEntity
     */
    public void add(BiaoShuEntity biaoShuEntity){
        biaoShuDao.save(biaoShuEntity);
    }

    /**
     * 分页查询标书
     * @param query
     * @return
     */
    public PageUtils queryPage(Query query){
        Pageable pageable = query.getPageable(Sort.Direction.DESC,"createTime");
        Page<BiaoShuEntity> all = biaoShuDao.findAll(getSpecification(query), pageable);
        //转化时间格式，用户名赋值
        List<BiaoShuEntity> entities = all.getContent();
        if (CollectionUtil.isNotEmpty(entities)){
            for (BiaoShuEntity entity : entities){
                if (entity.getPayTime() != null){
                    entity.setPayTimeStr(DateUtil.formatDate(entity.getPayTime()));
                }
                entity.setUserName(entity.getUserEntity().getUserName());
                entity.setUserEntity(null);
            }
        }
        return new PageUtils(all);
    }

    public void delete(String id){
        BiaoShuEntity biaoShuEntity = biaoShuDao.getById(id);
        if (biaoShuEntity == null){
            throw new MyException("当前标书款已删除！");
        }
        if (biaoShuEntity.getStatus() == 1){
            throw new MyException("当前标书款已付款，无法删除！");
        }
        biaoShuDao.delete(biaoShuEntity);
        log.info("删除标书款：{}", JSON.toJSONString(biaoShuEntity));
    }
}
