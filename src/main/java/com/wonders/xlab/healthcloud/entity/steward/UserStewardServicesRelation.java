package com.wonders.xlab.healthcloud.entity.steward;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Jeffrey on 15/7/7.
 */
@Entity
@Table(name = "HC_USER_STEWARD_SERVICES_RALATION")
public class UserStewardServicesRelation extends AbstractPersistable<Long> {

    @Embedded
    private UserStewardServicesPK pk;


    public UserStewardServicesPK getPk() {
        return pk;
    }

    public void setPk(UserStewardServicesPK pk) {
        this.pk = pk;
    }
}
