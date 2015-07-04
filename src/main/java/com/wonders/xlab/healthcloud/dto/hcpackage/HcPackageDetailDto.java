package com.wonders.xlab.healthcloud.dto.hcpackage;

import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;

import javax.validation.constraints.NotNull;

/**
 * Created by mars on 15/7/4.
 */
public class HcPackageDetailDto {

    @NotNull(message = "详细不能为空")
    private String detail;

    public HcPackageDetail toNewHcPackageDetail(HcPackage hp) {
        HcPackageDetail hpd = new HcPackageDetail();
        hpd.setHcPackage(hp);
        hpd.setDetail(detail);
        return hpd;
    }

    public HcPackageDetail updateHcPackageDetail(HcPackageDetail hpd) {
        hpd.setDetail(detail);
        return hpd;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
