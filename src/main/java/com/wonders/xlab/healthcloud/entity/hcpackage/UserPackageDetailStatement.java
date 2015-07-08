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
@Table(name = "hc_user_package_detail_statement")
public class UserPackageDetailStatement extends AbstractBaseEntity<Long> {

    /** 健康包详情 */
    @ManyToOne(fetch = FetchType.LAZY)
    private HcPackageDetail hcPackageDetail;

    /** 用户语句 */
    private String statement;

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
}
