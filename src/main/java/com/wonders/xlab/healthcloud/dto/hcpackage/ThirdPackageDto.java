package com.wonders.xlab.healthcloud.dto.hcpackage;

/**
 * Created by mars on 15/7/8.
 */
public class ThirdPackageDto {

    private Long id;

    private String title;

    private String iconUrl;

    private Integer countPackage;

    private String description;

    public ThirdPackageDto() {
    }

    public ThirdPackageDto(Long id, String title, String iconUrl, Integer countPackage) {
        this.id = id;
        this.title = title;
        this.iconUrl = iconUrl;
        this.countPackage = countPackage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getCountPackage() {
        return countPackage;
    }

    public void setCountPackage(Integer countPackage) {
        this.countPackage = countPackage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
