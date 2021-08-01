package com.system.manager.common;

import cn.hutool.core.util.StrUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author chenshun
 */
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    /**
     * 当前页码
     */
    private int page = 0;
    /**
     * 每页条数
     */
    private int limit = 10;

    /**
     * 排序字段
     */
    private String orderField;
    /**
     * 排序方式  DESC、ASC
     */
    private String order;

    public Query(Map<String, Object> params){
        this.putAll(params);

        //分页参数
        if(params.get("page") != null){
            Object p = params.get("page");
            if(p != null && StrUtil.isNotBlank(p.toString())){
                page = Integer.parseInt(p.toString()) - 1;
            }

            if(page < 0){
                page = 0;
            }
        }

        if(params.get("limit") != null){
            Object l = params.get("limit");
            if(l != null && StrUtil.isNotBlank(l.toString())){
                limit = Integer.parseInt(l.toString());
            }
        }

        orderField = (String)params.get("orderField");
        order = (String)params.get("order");
    }


    public Pageable getPageable(Sort.Direction direction, String... properties){
        Pageable pageable;

        if(StrUtil.isNotBlank(orderField) && StrUtil.isNotBlank(order)){
            direction = "DESC".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
            pageable = PageRequest.of(page, limit, direction, StrUtil.split(orderField, ","));
        }else{
            pageable = PageRequest.of(page, limit, direction, properties);
        }

        return pageable;
    }

    public Pageable getPageableMoreSort(Sort sort){
        return PageRequest.of(page, limit, sort);
    }

    public Sort getSort(Sort.Direction direction, String... properties){
        Sort sort;
        if(StrUtil.isNotBlank(orderField) && StrUtil.isNotBlank(order)){
            direction = "DESC".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = Sort.by(direction, orderField);
        }else{
            sort = Sort.by(direction, properties);
        }

        return sort;
    }

    public Sort getSort(String direction, String... properties){
        Sort.Direction directionSort = "DESC".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return this.getSort(directionSort, properties);
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public String getOrderField() {
        return orderField;
    }

    public String getOrder() {
        return order;
    }
}
