package com.wonders.xlab.healthcloud.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by mars on 15/7/2.
 */
public class ThirdLoginToken implements Serializable {


    /** 手机 */
//    @NotNull(message = "关联的手机号不能为空！")
//    @Pattern(regexp = "^1[0-9]{10}$", message = "关联的手机号格式不正确！")
    private String tel;

    /** 第三方id */
    @NotNull(message = "第三方登陆id不能为空")
    private String thirdId;

    /** 第三方登陆平台 0 新浪 1 扣扣 2 微信 */
    @NotNull(message = "第三方登陆平台标识不能为空")
    @Pattern(regexp = "^(0|1|2)$", message = "登陆平台标识必须为0 sina, 1 tencent, 2 wechat")
    private String thirdType;

    /** 验证码 */
//    @NotNull(message = "4位随机验证码不能为空！")
    private String code;

    public ThirdLoginToken() {
        super();
    }

    public ThirdLoginToken(String tel, String thirdId, String thirdType, String code) {
        this.tel = tel;
        this.thirdId = thirdId;
        this.thirdType = thirdType;
        this.code = code;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public String getThirdType() {
        return thirdType;
    }

    public void setThirdType(String thirdType) {
        this.thirdType = thirdType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
