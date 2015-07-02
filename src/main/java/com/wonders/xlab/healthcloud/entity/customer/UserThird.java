package com.wonders.xlab.healthcloud.entity.customer;

import com.wonders.xlab.healthcloud.entity.ThirdBaseInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Jeffrey on 15/7/2.
 */

@Entity
@Table(name = "HC_USER_THIRD")
public class UserThird extends ThirdBaseInfo<Long> {

    public UserThird() {
        super();
    }

    public UserThird(String thirdId, ThirdType thirdType, User user) {
        super(thirdId, thirdType);
        this.user = user;
    }

    /**
     * 用户信息
     */
    @Column(nullable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
