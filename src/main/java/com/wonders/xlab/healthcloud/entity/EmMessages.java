package com.wonders.xlab.healthcloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wukai on 15/7/4.
 */
@Entity
@Table(name = "EM_MESSAGES")
public class EmMessages  extends AbstractBaseEntity<Long> {
    /**
     * 发消息用户ID
     */
    private String fromUser;

    /**
     * 接收用户ID
     */
    @JsonIgnore
    private String toUser;
    /**
     * 返回前台数据
     */
    @Transient
    private List<String> toUsers;

    /**
     * 文本消息内容
     */
    private String msg;

    /**
     * 文件地址 图片文件 or 语音文件
     */
    private String fileUrl;

    /**
     * 是否已回复
     */
    private boolean isReplied;

    /**
     * 消息类型 img,msg,audio
     */
    private String messageType;

    /**
     * 聊天室名称
     */
    private String chatName;

    /**
     * 聊天类型 users单聊 chatgroups群聊
     */
    private String targetType;

    /**
     * 自定义消息扩展属性
     */
    private String ext;

    /**
     * 消息是否是发送给医生的，方便过滤发送给医生的消息
     */
    private boolean doctorFlag;

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public boolean getIsReplied() {
        return isReplied;
    }

    public void setIsReplied(boolean isReplied) {
        this.isReplied = isReplied;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public boolean isDoctorFlag() {
        return doctorFlag;
    }

    public void setDoctorFlag(boolean doctorFlag) {
        this.doctorFlag = doctorFlag;
    }

    public List<String> getToUsers() {
        return Arrays.asList(toUser.split(","));
    }
}