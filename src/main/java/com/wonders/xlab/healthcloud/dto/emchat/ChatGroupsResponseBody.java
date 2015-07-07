package com.wonders.xlab.healthcloud.dto.emchat;

import java.util.*;

/**
 * Created by lixuanwu on 15/7/6.
 */
public class ChatGroupsResponseBody {

    private String action;

    private String application;

    private String uri;

    private List<Object> entities = new ArrayList<Object>();

    private Map <String,String>data;

    private Date timestamp;

    private Integer duration;

    private String organization;

    private String applicationName;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Object> getEntities() {
        return entities;
    }

    public void setEntities(List<Object> entities) {
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

    public Map <String,String>getData() {
        return data;
    }

    public void setData(Map<String,String> data) {
        this.data = data;
    }
}
