package com.wonders.xlab.healthcloud.entity.steward;

import com.wonders.xlab.healthcloud.entity.customer.User;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Jeffrey on 15/7/7.
 */
@Entity
@Table(name = "HC_USER_STEWARD_SERVICES")
public class UserStewardServices extends AbstractPersistable<Long> {

    /** 对应一个管家 */
    @ManyToOne
    private Steward steward;

    /** 服务 一个用户服务对应多个管家服务 */
    @ManyToMany
    @JoinTable(
            name = "HC_USER_SERVICE_RELATION",
            joinColumns = @JoinColumn(name = "USER_SERVICE_ID"),
            inverseJoinColumns = @JoinColumn(name = "STEWARD_SERVICE_ID")
    )
    private Set<Services> stewardServices;

    /** 多个用户服务对应一个用户 */
    @ManyToOne
    private User user;

    public Steward getSteward() {
        return steward;
    }

    public void setSteward(Steward steward) {
        this.steward = steward;
    }

    public Set<Services> getStewardServices() {
        return stewardServices;
    }

    public void setStewardServices(Set<Services> stewardServices) {
        this.stewardServices = stewardServices;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
