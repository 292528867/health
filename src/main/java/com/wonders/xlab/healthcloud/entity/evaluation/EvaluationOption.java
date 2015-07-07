package com.wonders.xlab.healthcloud.entity.evaluation;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 测评问题选项
 * Created by mars on 15/7/4.
 */
@Entity
@Table(name = "hc_evaluation_option")
public class EvaluationOption extends AbstractBaseEntity<Long> {

    /** 问题 */
    @ManyToOne(fetch = FetchType.LAZY)
    private EvaluationInfo evaluationInfo;

    /** 选项 */
    private String qoNumber;

    /** 图片地址 */
    private String picPath;

    /** 内容 */
    private String content;

    /** 分数 */
    private int score;


    public EvaluationInfo getEvaluationInfo() {
        return evaluationInfo;
    }

    public void setEvaluationInfo(EvaluationInfo evaluationInfo) {
        this.evaluationInfo = evaluationInfo;
    }

    public String getQoNumber() {
        return qoNumber;
    }

    public void setQoNumber(String qoNumber) {
        this.qoNumber = qoNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
