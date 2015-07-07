package com.wonders.xlab.healthcloud.dto.steward;

import javax.validation.constraints.NotNull;

/**
 * Created by mars on 15/7/7.
 */
public class ServiceDto {

    /** 健康包id 可为空，空就是自定义包 */
    private String packageId;

    /** 服务ID，逗号隔开 */
    @NotNull(message = "服务不能为空")
    private String serviceIds;

    /** 管家id */
    @NotNull(message = "管家不能为空")
    private String stewardId;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(String serviceIds) {
        this.serviceIds = serviceIds;
    }

    public String getStewardId() {
        return stewardId;
    }

    public void setStewardId(String stewardId) {
        this.stewardId = stewardId;
    }
}
