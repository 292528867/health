package com.wonders.xlab.healthcloud.dto.emchat;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lixuanwu on 15/7/5.
 */
public class ResponseUuidSecret {

    private String uuid;

    private String type;

    @JsonProperty("share-secret")
    private String shareSecret;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShareSecret() {
        return shareSecret;
    }

    public void setShareSecret(String shareSecret) {
        this.shareSecret = shareSecret;
    }
}

