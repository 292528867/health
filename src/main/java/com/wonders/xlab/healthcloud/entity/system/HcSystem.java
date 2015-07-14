package com.wonders.xlab.healthcloud.entity.system;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by lixuanwu on 15/7/14.
 */
@Entity
@Table(name = "HC_SYSTEM")
public class HcSystem extends AbstractPersistable<Long> {

    /**
     * 用户协议
     */
    @Column(length = 1024)
    public String userAgreement;

    /**
     * 操作手册
     */
    @Column(length = 1024)
    private String operator;

    /**
     * 运营的二维码
     * @return
     */
    private String serviceUrl;

    /**
     * 版本号
     * @return
     */
    private String versionNumber;


    public String getUserAgreement() {
        return userAgreement;
    }

    public void setUserAgreement(String userAgreement) {
        this.userAgreement = userAgreement;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }
}
