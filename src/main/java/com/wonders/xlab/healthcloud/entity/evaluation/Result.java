package com.wonders.xlab.healthcloud.entity.evaluation;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.*;

/**
 * 结果
 * Created by mars on 15/7/4.
 */
@Entity
@Table(name = "hc_result")
public class Result extends AbstractBaseEntity<Long> {

    /** 测评 */
    @ManyToOne(fetch = FetchType.LAZY)
    private Evaluation question;

    /** 结果标题 */
    private String title;

    /** 内容 */
    @Lob
    private String content;

    public Evaluation getQuestion() {
        return question;
    }

    public void setQuestion(Evaluation question) {
        this.question = question;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
