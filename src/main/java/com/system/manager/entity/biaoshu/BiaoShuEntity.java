package com.system.manager.entity.biaoshu;

import com.system.manager.entity.user.UserEntity;
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
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private UserEntity userEntity;

    /**
     * 承担人id
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 承当人用户名
     */
    private String userName;
    /**
     *金额
     */
    @Column(name = "money")
    private BigDecimal money;
    /**
     * 付款日期字符串
     */
    private String payTimeStr;
    /**
     * 付款日期
     */
    @Column(name = "pay_time")
    private Date payTime;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建时间字符串
     */
    private String createTimeStr;
    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;
    /**
     * 修改时间字符串
     */
    private String updateTimeStr;
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
