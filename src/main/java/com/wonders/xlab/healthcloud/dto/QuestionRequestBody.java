package com.wonders.xlab.healthcloud.dto;

import java.util.List;

/**
 * 发送问题的请求body
 * Created by wukai on 15/7/6.
 */
public class QuestionRequestBody {

    /**
     * 问题内容
     */
    private String question;

    /**
     * 需要发送的 app list
     */
    private List<String> appName;

    /**
     * app类型
     */
    private String appType;

    /**
     * 科室
     */
    private String department;

    /**
     * 提问用户账号 用于获取个人信息
     */
    private String fromUser;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAppName() {
        return appName;
    }

    public void setAppName(List<String> appName) {
        this.appName = appName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }
}
