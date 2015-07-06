package com.wonders.xlab.healthcloud.dto.emchat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuanwu on 15/7/5.
 * 上传文件成功后返回的json实体
 */
public class ChatFilesResponseBody {


    private String action;

    private String application;

    private String path;

    private String uri;

    private List<ResponseUuidSecret> entities;

    private Date timestamp;

    private Integer duration;

    private String organization;

    private String applicationName;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<ResponseUuidSecret> getEntities() {
        return entities;
    }

    public void setEntities(List<ResponseUuidSecret> entities) {
        this.entities = entities;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
