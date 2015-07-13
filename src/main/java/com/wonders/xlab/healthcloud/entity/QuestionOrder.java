package com.wonders.xlab.healthcloud.entity;

import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.doctor.Doctor;

import javax.persistence.*;

/**
 * 用户发起的问题订单
 * Created by wukai on 15/7/12.
 */
@Entity
@Table(name = "HC_QUESTION_ORDERS")
public class QuestionOrder extends AbstractBaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private EmMessages messages;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Doctor doctor;

    @Enumerated
    private QuestionStatus questionStatus = QuestionStatus.newQuestion;

    public enum QuestionStatus {
        newQuestion, processing, done
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EmMessages getMessages() {
        return messages;
    }

    public void setMessages(EmMessages messages) {
        this.messages = messages;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public QuestionStatus getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(QuestionStatus questionStatus) {
        this.questionStatus = questionStatus;
    }
}
