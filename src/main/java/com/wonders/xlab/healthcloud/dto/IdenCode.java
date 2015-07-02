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
    @Pattern(regexp = "/^1((3|5|8){1}\\d{1}|70|77)\\d{8}$/", message = "关联的手机号格式不正确！")
    private String tel;

    /** 验证码 */
    @NotNull(message = "4位随机验证码不能为空！")
    private String code;

    /** 创建时间 */
    private Date createTime;

    public IdenCode() {
        super();
    }

    public IdenCode(String tel, String code, Date createTime) {
        this.tel = tel;
        this.code = code;
        this.createTime = createTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
