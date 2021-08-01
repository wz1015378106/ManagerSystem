package com.system.manager.entity.biaoshu;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: wangzhi
 * @Title: BiaoShuEntity
 * @Description: 标书实体类
 * @date: 2021/8/1 16:56
 */
@Table(name = "biaoshu")
@Entity(name = "BiaoShuEntity")
@Data
public class BiaoShuEntity {
    /**
     * 自增主键ID,
     */
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    @Column(name = "id")
    private String id;

    /**
     * 对方单位名称
     */
    @Column(name = "opposite_unit")
    private String oppositeUnit;
    /**
     * 账号
     */
    @Column(name = "account_number")
    private String accountNumber;
    /**
     * 开户行
     */
    @Column(name = "bank_of_deposit")
    private String bankOfDeposit;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 承担人
     */
    @Column(name = "undertaker")
    private String undertaker;
    /**
     *金额
     */
    @Column(name = "money")
    private BigDecimal money;
    /**
     * 创建人
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 创建时间
     */
    @Column(name = "create_by")
    private String createBy;
    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * 修改人
     */
    @Column(name = "update_by")
    private String updateBy;
}
