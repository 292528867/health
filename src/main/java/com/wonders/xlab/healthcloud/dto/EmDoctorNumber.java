package com.wonders.xlab.healthcloud.dto;

/**
 * Created by yk on 15/7/11.
 */
public class EmDoctorNumber {

    //医生数量
    private int doctorNumber;
   //问候语
    private String greetings;
   //提问例子
    private String questionSample;
    //用户最后一个问题的状态
    private Boolean lastQuestionState;
    //用户等待时的描述
    private String waitContent;
    //用户超时的描述
    private String overTimeContent;

    public String getOverTimeContent() {
        return overTimeContent;
    }

    public void setOverTimeContent(String overTimeContent) {
        this.overTimeContent = overTimeContent;
    }

    public String getWaitContent() {
        return waitContent;
    }

    public void setWaitContent(String waitContent) {
        this.waitContent = waitContent;
    }

    public int getDoctorNumber() {
        return doctorNumber;
    }

    public void setDoctorNumber(int doctorNumber) {
        this.doctorNumber = doctorNumber;
    }

    public String getGreetings() {
        return greetings;
    }

    public void setGreetings(String greetings) {
        this.greetings = greetings;
    }

    public String getQuestionSample() {
        return questionSample;
    }

    public void setQuestionSample(String questionSample) {
        this.questionSample = questionSample;
    }

    public Boolean getLastQuestionState() {
        return lastQuestionState;
    }

    public void setLastQuestionState(Boolean lastQuestionState) {
        this.lastQuestionState = lastQuestionState;
    }
}
