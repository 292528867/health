package com.wonders.xlab.healthcloud.dto.customer;

import com.wonders.xlab.healthcloud.entity.BaseInfo;
import com.wonders.xlab.healthcloud.entity.customer.User;

/**
 * Created by Jeffrey on 15/7/3.
 */
public class UserDto {

    /**
     * invalid 用户无效（未完善用户信息），valid 用户有效
     */
    private User.Valid valid;

    /**
     * 手机
     */
    private String tel;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像地址
     */
    private String iconUrl;

    /**
     * 性别
     */
    private User.Sex sex;

    /**
     * 身高
     */
    private Double height;

    /**
     * 体重
     */
    private Double weight;

    /**
     * 年龄
     */
    private Integer age;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public User.Sex getSex() {
        return sex;
    }

    public void setSex(User.Sex sex) {
        this.sex = sex;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User.Valid getValid() {
        return valid;
    }

    public void setValid(User.Valid valid) {
        this.valid = valid;
    }

}
