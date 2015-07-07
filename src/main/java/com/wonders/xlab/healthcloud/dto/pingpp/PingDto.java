package com.wonders.xlab.healthcloud.dto.pingpp;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by mars on 15/7/7.
 */
public class PingDto {

    @NotNull(message = "金额不能为空")
    @NotEmpty(message = "金额不能为空")
    private Integer money;

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
