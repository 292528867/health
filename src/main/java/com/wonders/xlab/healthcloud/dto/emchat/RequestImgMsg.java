package com.wonders.xlab.healthcloud.dto.emchat;

/**
 * Created by lixuanwu on 15/7/5.
 */
public class RequestImgMsg {

    private String type;

    private String url;

    private String filename;

    private String secret;

    private Object size;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Object getSize() {
        return size;
    }

    public void setSize(Object size) {
        this.size = size;
    }
}
