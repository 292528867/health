package com.wonders.xlab.healthcloud.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created by mars on 15/7/2.
 */
public class IdenCode {

    /** 手机 */
    @NotNull(message = "关联的手机号不能为空！")
    @Pattern(regexp = "^1[0-9]{10}$", message = "关联的手机号格式不正确！")
    private String phone;

    /** 验证码 */
    @NotNull(message = "4位随机验证码不能为空！")
    private String code;

    /** 创建时间 */
    private Date createTime;

    public IdenCode() {
        super();
    }

    public IdenCode(String phone, String code, Date createTime) {
        this.phone = phone;
        this.code = code;
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
