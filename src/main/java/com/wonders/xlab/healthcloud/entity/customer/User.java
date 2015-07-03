package com.wonders.xlab.healthcloud.entity.customer;

import com.wonders.xlab.healthcloud.entity.BaseInfo;
import com.wonders.xlab.healthcloud.entity.healthpackage.Package;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "HC_USER")
public class User extends BaseInfo<Long> {

    /** 用户健康包 */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @OrderBy(value = "id asc")
    @JoinTable(name = "hc_user_package_relation", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "package_id"))
    private Set<Package> packages;

    //    invalid 用户无效（未完善用户信息），valid 用户有效
    @Enumerated
    private Valid valid = Valid.invalid;

    public enum Valid {
        invalid,valid
    }

    public Set<Package> getPackages() {
        return packages;
    }

    public void setPackages(Set<Package> packages) {
        this.packages = packages;
    }

    public Valid getValid() {
        return valid;
    }

    public void setValid(Valid valid) {
        this.valid = valid;
    }
}
