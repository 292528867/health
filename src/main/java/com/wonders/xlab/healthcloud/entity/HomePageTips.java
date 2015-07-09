package com.wonders.xlab.healthcloud.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 首页任务做完以后的小贴士
 * Created by wukai on 15/7/9.
 */
@Entity
@Table(name = "HC_HOME_TIPS")
public class HomePageTips extends AbstractBaseEntity<Long> {
    /**
     * 小贴士内容
     */
    private String tips;

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
