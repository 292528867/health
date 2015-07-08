package com.wonders.xlab.healthcloud.entity.hcpackage;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by mars on 15/7/8.
 */
@Entity
@Table(name = "hc_user_package_statement")
public class UserPackageStatement extends AbstractBaseEntity<Long> {

    /** 健康包 */
    @ManyToOne(fetch = FetchType.LAZY)
    private HcPackage hcPackage;

    /** 用户记录语言 */
    private String statement;

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
