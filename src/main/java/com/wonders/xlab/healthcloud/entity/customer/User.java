package com.wonders.xlab.healthcloud.entity.customer;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "HC_USER")
public class User extends AbstractPersistable<Long> {

    /**
     * 用户名
     */
    private String usserName;

    /**
     * 用户肖像
     */
    private String portrait;

    /**
     * 用户电话
     */
    private String telephone;

    public String getUsserName() {
        return usserName;
    }

    public void setUsserName(String usserName) {
        this.usserName = usserName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
