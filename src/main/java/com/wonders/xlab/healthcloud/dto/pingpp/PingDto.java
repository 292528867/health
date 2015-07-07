package com.wonders.xlab.healthcloud.dto.pingpp;

import javax.validation.constraints.NotNull;

/**
 * Created by mars on 15/7/7.
 */
public class PingDto {

    @NotNull(message = "主题不能为空")
    private String subject;

    @NotNull(message = "内容不能为空")
    private String body;

    @NotNull(message = "金额不能为空")
    private String money;

    public PingDto() {
    }

    public PingDto(String subject, String body, String money) {
        this.subject = subject;
        this.body = body;
        this.money = money;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
