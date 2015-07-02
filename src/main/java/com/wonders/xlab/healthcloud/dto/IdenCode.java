package com.wonders.xlab.healthcloud.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by mars on 15/7/2.
 */
public class IdenCode implements Serializable {

    /** 手机 */
    @NotNull(message = "关联的手机号不能为空！")
    @Pattern(regexp = "^1[0-9]{10}$", message = "关联的手机号格式不正确！")
    private String tel;

    /** 验证码 */
    @NotNull(message = "4位随机验证码不能为空！")
    private String code;

    public IdenCode() {
        super();
    }

    public IdenCode(String tel, String code) {
        this.tel = tel;
        this.code = code;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof IdenCode))
            return false;
        IdenCode cast = (IdenCode) obj;
        return new EqualsBuilder()
                .append(this.tel, cast.tel)
                .append(this.code, cast.code)
                .isEquals();
    }
}
