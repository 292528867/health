package com.wonders.xlab.healthcloud.entity;

import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by mars on 15/7/2.
 */
@MappedSuperclass
public abstract class ThirdBaseInfo<ID extends Serializable> extends AbstractBaseEntity<ID> {


    /**  */
    private String thirdId;

    @Enumerated
    private ThirdType thirdType;

    public enum ThirdType {
        Sina, Tencent, WeChat
    }

    public ThirdBaseInfo() {
        super();
    }

    public ThirdBaseInfo(String thirdId, ThirdType thirdType) {
        this.thirdId = thirdId;
        this.thirdType = thirdType;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public ThirdType getThirdType() {
        return thirdType;
    }

    public void setThirdType(ThirdType thirdType) {
        this.thirdType = thirdType;
    }
}
