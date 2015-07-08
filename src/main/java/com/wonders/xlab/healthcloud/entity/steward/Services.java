package com.wonders.xlab.healthcloud.entity.steward;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by lixuanwu on 15/7/7.
 */
@Entity
@Table(name = "HC_STEWARD_SERVICE")
public class Services extends AbstractPersistable<Long> {

    /**
     * 服务id
     */
    private Integer serviceId;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务的区域(上海，全国)
     */
    private enum ServiceArea{
        全国,上海
    }

    private ServiceArea serviceArea = ServiceArea.上海;

    /**
     * 服务积分
     */
    private Integer serviceIntegration;

    /**
     * 服务描述
     * @return
     */
    private String ServiceDescription;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public ServiceArea getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(ServiceArea serviceArea) {
        this.serviceArea = serviceArea;
    }

    public Integer getServiceIntegration() {
        return serviceIntegration;
    }

    public void setServiceIntegration(Integer serviceIntegration) {
        this.serviceIntegration = serviceIntegration;
    }

    public String getServiceDescription() {
        return ServiceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        ServiceDescription = serviceDescription;
    }
}
