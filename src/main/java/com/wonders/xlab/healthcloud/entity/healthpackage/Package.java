package com.wonders.xlab.healthcloud.entity.healthpackage;

/**
 * Created by mars on 15/7/3.
 * 健康包
 */
import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "hc_package")
public class Package extends AbstractBaseEntity<Long> {

    /** 标题 */
    private String title;

    /** 描述 */
    @Column(columnDefinition = "TEXT")
    private String description;

    public Package() {
    }

    public Package(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
