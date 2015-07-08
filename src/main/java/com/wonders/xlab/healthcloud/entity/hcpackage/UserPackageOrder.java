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
@Table(name = "hc_user_package_order")
public class UserPackageOrder extends AbstractBaseEntity<Long> {

    /** 用户 */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /** 健康包 */
    @ManyToOne(fetch = FetchType.LAZY)
    private HcPackage hcPackage;

    /** 健康包计划 逗号隔开 */
    private String hcPackageDetailIds;

    /** 循环次数 */
    private int cycleIndex;

    /** 是否完成包 同一时间只能存在两个没有完成的包 */
    private boolean packageComplete;

    /** 是否完成包计划 */
    private boolean packageDetailComplete;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HcPackage getHcPackage() {
        return hcPackage;
    }

    public void setHcPackage(HcPackage hcPackage) {
        this.hcPackage = hcPackage;
    }

    public String getHcPackageDetailIds() {
        return hcPackageDetailIds;
    }

    public void setHcPackageDetailIds(String hcPackageDetailIds) {
        this.hcPackageDetailIds = hcPackageDetailIds;
    }

    public int getCycleIndex() {
        return cycleIndex;
    }

    public void setCycleIndex(int cycleIndex) {
        this.cycleIndex = cycleIndex;
    }

    public boolean isPackageComplete() {
        return packageComplete;
    }

    public void setPackageComplete(boolean packageComplete) {
        this.packageComplete = packageComplete;
    }

    public boolean isPackageDetailComplete() {
        return packageDetailComplete;
    }

    public void setPackageDetailComplete(boolean packageDetailComplete) {
        this.packageDetailComplete = packageDetailComplete;
    }
}
