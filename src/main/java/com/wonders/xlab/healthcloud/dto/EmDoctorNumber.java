package com.wonders.xlab.healthcloud.dto;

import com.wonders.xlab.healthcloud.entity.EmMessages;

/**
 * Created by yk on 15/7/11.
 */
public class EmDoctorNumber {

    private boolean lastQuestionStatus;

    private String content;

    private EmMessages emMessages;

    public boolean isLastQuestionStatus() {
        return lastQuestionStatus;
    }

    public void setLastQuestionStatus(boolean lastQuestionStatus) {
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
