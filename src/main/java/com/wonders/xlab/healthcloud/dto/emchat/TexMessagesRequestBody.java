package com.wonders.xlab.healthcloud.dto.emchat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixuanwu on 15/7/5.
 * 发送消息请求的参数实体
 */
public class TexMessagesRequestBody {

    /**
     * users 给用户发消息
     */
    @JsonProperty("target_type")
    private String targetType;

    /**
     * 注意这里需要用数组,数组长度建议不大于20, 即使只有一个用户,也要用数组 ['u1']
     */
    private List<String> target = new ArrayList<String>();

    /**
     * 消息内容
     */
    private RequestTexMsg msg;

    /**
     * 表示这个消息是谁发出来的
     */
    private String from;

   /* *//**
     * 扩展属性
     *//*
    private String ext;*/


    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public List<String> getTarget() {
        return target;
    }

    public void setTarget(List<String> target) {
        this.target = target;
    }

    public RequestTexMsg getMsg() {
        return msg;
    }

    public void setMsg(RequestTexMsg msg) {
        this.msg = msg;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    /*public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }*/
}
