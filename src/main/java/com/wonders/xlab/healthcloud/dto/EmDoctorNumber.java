package com.wonders.xlab.healthcloud.dto;

import com.wonders.xlab.healthcloud.entity.EmMessages;

import java.util.List;

/**
 * Created by yk on 15/7/11.
 */
public class EmDoctorNumber {

    //0表示进入问答页面    1代表等待页面
    private int lastQuestionStatus;

    private String content;

    //存放的是doctor数量的提示，ios要求要这样的格式
    private EmMessages emMessages;

    //返回前2条纪录
    private List<EmMessages> list;

    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getLastQuestionStatus() {
        return lastQuestionStatus;
    }

    public void setLastQuestionStatus(int lastQuestionStatus) {
        this.lastQuestionStatus = lastQuestionStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EmMessages getEmMessages() {
        return emMessages;
    }

    public void setEmMessages(EmMessages emMessages) {
        this.emMessages = emMessages;
    }

    public List<EmMessages> getList() {
        return list;
    }

    public void setList(List<EmMessages> list) {
        this.list = list;
    }
}
