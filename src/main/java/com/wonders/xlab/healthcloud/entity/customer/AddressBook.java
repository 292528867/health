package com.wonders.xlab.healthcloud.entity.customer;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by lixuanwu on 15/7/12.
 */
@Entity
@Table(name = "HC_USER_ADDRESSBOOK")
public class AddressBook extends AbstractPersistable<Long> {

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public AddressBook(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public AddressBook() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
