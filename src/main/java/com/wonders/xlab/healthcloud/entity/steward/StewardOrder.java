package com.wonders.xlab.healthcloud.entity.steward;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;
import com.wonders.xlab.healthcloud.entity.customer.User;
import javax.persistence.*;
import java.util.*;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created by lixuanwu on 15/7/7.
 */
@Entity
@Table(name = "HC_STEWRADORDER")
public class StewardOrder extends AbstractBaseEntity<Long> {
    /**
     * 订单号
     */
    private String tradeNo;
    /**
     * ping++ 返回的id
     */
    private String chargeId;

    /**
     * 订单金额
     */
    private Integer money;

    /**
     * 付款生成时间
     */
    @Temporal(TIMESTAMP)
    private Date payDate;

    /**
     * 订单状态
     */
    public enum OrderStatus {

        支付成功,未支付,付款中,

    }

    /**
     * 多个用户服务对应一个用户
     */
    @ManyToOne
    @JsonIgnore
    private User user;

    /**
     * 对应一个管家
     */
    @ManyToOne
    private Steward steward;

    /**
     * 服务 一个用户服务对应多个管家服务
     */
    @ManyToMany
    @OrderBy("usedNumber desc")
    private Set<Services> services;

    private OrderStatus orderStatus = OrderStatus.支付成功;

    /**
     * 管家所提供任务的状态
     */
    @Transient
    private Map<String,Object> servicedPeriodStatus = new HashMap<>();

    public StewardOrder() {
    }

    public StewardOrder(String chargeId, String tradeNo, Integer money) {
        this.chargeId = chargeId;
        this.tradeNo = tradeNo;
        this.money = money;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Steward getSteward() {
        return steward;
    }

    public void setSteward(Steward steward) {
        this.steward = steward;
    }

    public Set<Services> getServices() {
        return services;
    }

    public void setServices(Set<Services> services) {
        this.services = services;
    }

    public Map<String, Object> getServicedPeriodStatus() {
        return servicedPeriodStatus;
    }

    public void setServicedPeriodStatus(Map<String, Object> servicedPeriodStatus) {
        this.servicedPeriodStatus = servicedPeriodStatus;
    }
}
