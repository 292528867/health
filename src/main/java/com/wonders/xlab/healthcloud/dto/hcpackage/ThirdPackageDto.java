package com.wonders.xlab.healthcloud.dto.hcpackage;

/**
 * Created by mars on 15/7/8.
 */
public class ThirdPackageDto {

    private Long id;

    private String title;

    private String iconUrl;

    public ThirdPackageDto() {
    }

    public ThirdPackageDto(Long id, String title, String iconUrl) {
        this.id = id;
        this.title = title;
        this.iconUrl = iconUrl;
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
}
