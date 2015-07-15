package com.wonders.xlab.healthcloud.entity.evaluation;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.*;

/**
 * 测评
 * Created by mars on 15/7/4.
 */
@Entity
@Table(name = "hc_evaluation")
public class Evaluation extends AbstractBaseEntity<Long> {

    /** 题目类型 */
    @ManyToOne(fetch = FetchType.LAZY)
    private EvaluationType evaluationType;

    /** 标题 */
    private String title;

    /** 指导语 */
    private String instruction;

    /** 简述 */
    @Lob
    private String description;

    /** 推荐值 优先级*/
    private int recommendedValue;

    /** 小提示 */
    private String tips;

    /** 测评状态 0 线上 1 线下 */
    @Enumerated
    private Status status;

    public enum Status {
        Online, Offline
    }

    public Evaluation() {
    }

    public Evaluation(String title, String instruction, String description, int recommendedValue, Status status) {
        this.title = title;
        this.instruction = instruction;
        this.description = description;
        this.recommendedValue = recommendedValue;
        this.status = status;
    }

    public EvaluationType getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(EvaluationType evaluationType) {
        this.evaluationType = evaluationType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRecommendedValue() {
        return recommendedValue;
    }

    public void setRecommendedValue(int recommendedValue) {
        this.recommendedValue = recommendedValue;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
