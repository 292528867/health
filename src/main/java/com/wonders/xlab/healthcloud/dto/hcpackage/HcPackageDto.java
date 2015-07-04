package com.wonders.xlab.healthcloud.dto.hcpackage;

import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;

import javax.validation.constraints.NotNull;

/**
 * Created by mars on 15/7/4.
 */
public class HcPackageDto {

    /** 标题 */
    @NotNull(message = "标题不能为空")
    private String title;

    /** 描述 */
    @NotNull(message = "描述不能为空")
    private String description;

    /** 是否推荐 */
    @NotNull(message = "推荐值不能为空")
    private Boolean recommend;

    /** 图片地址 */
    @NotNull(message = "图片不能为空")
    private String prictureUrl;


    public HcPackage toNewHcPackage() {
        HcPackage hcPackage = new HcPackage();
        hcPackage.setTitle(title);
        hcPackage.setDescription(description);
        hcPackage.setRecommend(recommend);
        hcPackage.setIconUrl(prictureUrl);
        return hcPackage;
    }

    public HcPackage updateHcPackage(HcPackage hp) {
        hp.setTitle(title);
        hp.setDescription(description);
        hp.setRecommend(recommend);
        hp.setIconUrl(prictureUrl);
        return hp;
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

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public String getPrictureUrl() {
        return prictureUrl;
    }

    public void setPrictureUrl(String prictureUrl) {
        this.prictureUrl = prictureUrl;
    }
}
