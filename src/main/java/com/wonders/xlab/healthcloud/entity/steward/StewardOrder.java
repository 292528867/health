package com.wonders.xlab.healthcloud.entity.steward;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created by lixuanwu on 15/7/7.
 */
@Entity
@Table(name = "HC_STEWRAD_ORDER")
public class StewardOrder extends AbstractBaseEntity<Long> {

    /**
     * 订单号
     */
    private String tradeNo;

    /**
     * ping++ 返回的id
     */
    private String chargeId;

    @OneToOne
    private UserStewardServices userStewardServices;

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
    private enum OrderStatus{

        未支付,付款中,已支付

    }
    private OrderStatus orderStatus = OrderStatus.未支付;

    public StewardOrder() {
    }

    public StewardOrder(String tradeNo, Integer money) {
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

    public UserStewardServices getUserStewardServices() {
        return userStewardServices;
    }

    public void setUserStewardServices(UserStewardServices userStewardServices) {
        this.userStewardServices = userStewardServices;
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
}
