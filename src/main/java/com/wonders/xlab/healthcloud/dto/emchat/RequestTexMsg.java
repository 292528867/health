package com.wonders.xlab.healthcloud.dto.emchat;

/**
 * Created by lixuanwu on 15/7/5.
 */
public class RequestTexMsg {

    /**
     * 消息类型
     */

    private String type;

    /**
     * 消息内容
     */

    private String msg;

    public enum EmMessageType {
        txt, img, audio, video, cmd
    }


    public RequestTexMsg() {
    }

    public RequestTexMsg(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
