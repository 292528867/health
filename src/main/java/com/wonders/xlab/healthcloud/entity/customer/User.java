package com.wonders.xlab.healthcloud.entity.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wonders.xlab.healthcloud.entity.BaseInfo;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "HC_USER")
public class User extends BaseInfo<Long> {
    /**
     * 用户健康包
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @OrderBy(value = "id asc")
    @JoinTable(name = "hc_user_package_relation", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "package_id"))
    private Set<HcPackage> hcPackages;

    /**
     * 分类
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "HC_USER_HEALTHGATEGORY",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "HEALTHCATEGORY_ID")
    )
    private Set<HealthCategory> hcs = new HashSet<>();

    //    invalid 用户无效（未完善用户信息），valid 用户有效
    @Enumerated
    private Valid valid = Valid.invalid;

    public enum Valid {
        invalid, valid
    }

    /**
     * 用户使用平台
     */
    @Enumerated
    private AppPlatform appPlatform;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 用户在环信上的群组id
     */

    private String groupId;

    /**
     * 用户积分
     */
    private int integrals = 0;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 被邀请码
     */
    @JsonIgnore
    private String ByInviteCode;

    /**
     * 邀请二维码的url
     * @return
     */
    private String inviteUrl = "http://7xk3mz.com2.z0.glb.qiniucdn.com/healthinfo-icon-1436850530261";

    public Set<HcPackage> getHcPackages() {
        return hcPackages;
    }

    public void setHcPackages(Set<HcPackage> hcPackages) {
        this.hcPackages = hcPackages;
    }

    public int getValid() {
        return valid.ordinal();
    }

    public void setValid(Valid valid) {
        this.valid = valid;
    }

    public Set<HealthCategory> getHcs() {
        return hcs;
    }

    public void setHcs(Set<HealthCategory> hcs) {
        this.hcs = hcs;
    }

    public AppPlatform getAppPlatform() {
        return appPlatform;
    }

    public void setAppPlatform(AppPlatform appPlatform) {
        this.appPlatform = appPlatform;
    }

    public int getIntegrals() {
        return integrals;
    }

    public void setIntegrals(int integrals) {
        this.integrals = integrals;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteUrl() {
        return inviteUrl;
    }

    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
    }

    public String getByInviteCode() {
        return ByInviteCode;
    }

    public void setByInviteCode(String byInviteCode) {
        ByInviteCode = byInviteCode;
    }

}
