package com.wonders.xlab.healthcloud.dto.hcpackage;

/**
 * Created by mars on 15/7/8.
 */
public class ProgressDto {

    /** 标题 */
    private String title;

    /** 任务名称 */
    private String description;

    /** 图片url */
    private String iconUrl;

    /** 进程 */
    private int progressNum;

    public ProgressDto() {
    }

    public ProgressDto(String title, String description, String iconUrl, int progressNum) {
        this.title = title;
        this.description = description;
        this.iconUrl = iconUrl;
        this.progressNum = progressNum;
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getProgressNum() {
        return progressNum;
    }

    public void setProgressNum(int progressNum) {
        this.progressNum = progressNum;
    }
}
