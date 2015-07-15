package com.wonders.xlab.healthcloud.entity.hcpackage;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;
import com.wonders.xlab.healthcloud.entity.customer.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by mars on 15/7/8.
 */
@Entity
@Table(name = "hc_user_package_detail_statement")
public class UserPackageDetailStatement extends AbstractBaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    private HcPackage hcPackage;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /** 健康包详情 */
    @ManyToOne(fetch = FetchType.LAZY)
    private HcPackageDetail hcPackageDetail;

    /** 用户语句 */
    private String statement;

    public UserPackageDetailStatement() {
    }

    public UserPackageDetailStatement(User user, HcPackageDetail hcPackageDetail, String statement) {
        this.user = user;
        this.hcPackageDetail = hcPackageDetail;
        this.statement = statement;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HcPackageDetail getHcPackageDetail() {
        return hcPackageDetail;
    }

    public void setHcPackageDetail(HcPackageDetail hcPackageDetail) {
        this.hcPackageDetail = hcPackageDetail;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public HcPackage getHcPackage() {
        return hcPackage;
    }

    public void setHcPackage(HcPackage hcPackage) {
        this.hcPackage = hcPackage;
    }
}
