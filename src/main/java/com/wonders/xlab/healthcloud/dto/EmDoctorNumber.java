package com.wonders.xlab.healthcloud.dto;

import com.wonders.xlab.healthcloud.entity.EmMessages;

/**
 * Created by yk on 15/7/11.
 */
public class EmDoctorNumber {

    //0表示进入问答页面    1代表等待页面
    private int lastQuestionStatus;

    private String content;

    private EmMessages emMessages;

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
}
