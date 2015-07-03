package com.wonders.xlab.healthcloud.entity.healthpackage;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.*;

/**
 * Created by mars on 15/7/3.
 * 健康包详细
 */
@Entity
@Table(name = "hc_package_detail")
public class PackageDetail extends AbstractBaseEntity<Long> {

    /** 健康包 */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Package hcPackage;

    /** 详细信息 */
    @Column(columnDefinition = "TEXT")
    private String detail;


    public PackageDetail() {
    }

    public PackageDetail(Package hcPackage, String detail) {
        this.hcPackage = hcPackage;
        this.detail = detail;
    }

    public Package getHcPackage() {
        return hcPackage;
    }

    public void setHcPackage(Package hcPackage) {
        this.hcPackage = hcPackage;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
