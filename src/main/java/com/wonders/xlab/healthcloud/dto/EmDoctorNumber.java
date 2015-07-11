package com.wonders.xlab.healthcloud.dto;

import com.wonders.xlab.healthcloud.entity.EmMessages;

/**
 * Created by yk on 15/7/11.
 */
public class EmDoctorNumber {

   private String content;

   private EmMessages emMessages;

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
