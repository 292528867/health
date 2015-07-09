package com.wonders.xlab.healthcloud.dto.emchat;

import java.util.*;

/**
 * Created by lixuanwu on 15/7/6.
 */
public class ChatGroupsResponseBody {


    /**
     * timestamp : 1411527886457
     * organization : easemob-demo
     * duration : 125
     * application : 4d7e4ba0-dc4a-11e3-90d5-e1ffbaacdaf5
     * applicationName : chatdemoui
     * data : {"groupid":"1411527886490154"}
     * action : post
     * params : {}
     * entities : []
     * uri : https://a1.easemob.com/easemob-demo/chatdemoui
     */
    private long timestamp;
    private String organization;
    private int duration;
    private String application;
    private String applicationName;
    private DataEntity data;
    private String action;
    private ParamsEntity params;
    private List<?> entities;
    private String uri;

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setParams(ParamsEntity params) {
        this.params = params;
    }

    public void setEntities(List<?> entities) {
        this.entities = entities;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getOrganization() {
        return organization;
    }

    public int getDuration() {
        return duration;
    }

    public String getApplication() {
        return application;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public DataEntity getData() {
        return data;
    }

    public String getAction() {
        return action;
    }

    public ParamsEntity getParams() {
        return params;
    }

    public List<?> getEntities() {
        return entities;
    }

    public String getUri() {
        return uri;
    }

    public class DataEntity {
        /**
         * groupid : 1411527886490154
         */
        private String groupid;

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getGroupid() {
            return groupid;
        }
    }

    public class ParamsEntity {
    }
}
