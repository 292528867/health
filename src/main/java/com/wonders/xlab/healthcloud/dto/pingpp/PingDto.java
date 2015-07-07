package com.wonders.xlab.healthcloud.dto.pingpp;

import javax.validation.constraints.NotNull;

/**
 * Created by mars on 15/7/7.
 */
public class PingDto {

    @NotNull(message = "金额不能为空")
    private String money;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
