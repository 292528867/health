package com.wonders.xlab.healthcloud.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Jeffrey on 15/7/9.
 */
@Entity
@Table(name = "HC_APP_MENU")
public class AppMenu extends AbstractPersistable<Long> {

    private String title;

    private int appMenuType;

    private String icon;

    private int menuSort;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAppMenuType() {
        return appMenuType;
    }

    public void setAppMenuType(int appMenuType) {
        this.appMenuType = appMenuType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(int menuSort) {
        this.menuSort = menuSort;
    }
}
