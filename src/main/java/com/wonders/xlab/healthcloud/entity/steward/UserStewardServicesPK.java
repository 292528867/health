package com.wonders.xlab.healthcloud.entity.steward;

import com.wonders.xlab.healthcloud.entity.customer.User;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * Created by Jeffrey on 15/7/7.
 */
@Embeddable
public class UserStewardServicesPK {

    @ManyToOne
    private Steward steward;

    @ManyToOne
    private StewardService stewardService;

    @ManyToOne
    private User user;

    public Steward getSteward() {
        return steward;
    }

    public void setSteward(Steward steward) {
        this.steward = steward;
    }

    public StewardService getStewardService() {
        return stewardService;
    }

    public void setStewardService(StewardService stewardService) {
        this.stewardService = stewardService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
