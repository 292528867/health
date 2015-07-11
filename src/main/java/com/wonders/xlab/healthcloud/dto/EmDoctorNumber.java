package com.wonders.xlab.healthcloud.dto;

/**
 * Created by yk on 15/7/11.
 */
public class EmDoctorNumber {

    //一生数量
    private int doctorNumber;
   //问候语
    private String greetings;
   //提问例子
    private String questionSample;

    public EmDoctorNumber(int doctorNumber, String greetings, String questionSample) {
        this.doctorNumber = doctorNumber;
        this.greetings = greetings;
        this.questionSample = questionSample;
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
}
