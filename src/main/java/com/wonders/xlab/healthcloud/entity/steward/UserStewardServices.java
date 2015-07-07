package com.wonders.xlab.healthcloud.entity.steward;

import com.wonders.xlab.healthcloud.entity.customer.User;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Jeffrey on 15/7/7.
 */
@Entity
@Table(name = "HC_USER_STEWARD_SERVICES")
public class UserStewardServices extends AbstractPersistable<Long> {

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
