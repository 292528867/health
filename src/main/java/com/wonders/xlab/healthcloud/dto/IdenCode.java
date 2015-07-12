package com.wonders.xlab.healthcloud.dto;

import com.wonders.xlab.healthcloud.entity.BaseInfo;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by mars on 15/7/2.
 */
public class IdenCode implements Serializable {

    /** 手机 */
    @NotBlank(message = "关联的手机号不能为空！")
    @Pattern(regexp = "^1((3|5|8){1}\\d{1}|70|77)\\d{8}$", message = "关联的手机号格式不正确！")
    private String tel;

    /** 验证码 */
    @NotBlank(message = "4位随机验证码不能为空！")
    private String code;

    @NotNull(message = "用户登陆平台不能为空！")
    private BaseInfo.AppPlatform appPlatform;

    public IdenCode() {
        super();
    }

    public IdenCode(String tel, String code, BaseInfo.AppPlatform appPlatform) {
        this.tel = tel;
        this.code = code;
        this.appPlatform = appPlatform;
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

    public BaseInfo.AppPlatform getAppPlatform() {
        return appPlatform;
    }

    public void setAppPlatform(BaseInfo.AppPlatform appPlatform) {
        this.appPlatform = appPlatform;
    }
}
