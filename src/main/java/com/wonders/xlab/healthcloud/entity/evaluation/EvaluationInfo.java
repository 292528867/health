package com.wonders.xlab.healthcloud.entity.evaluation;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * 测评问题
 * Created by mars on 15/7/4.
 */
@Entity
@Table(name = "hc_evaluation_info")
public class EvaluationInfo extends AbstractBaseEntity<Long> {

    /** 测评 */
    @ManyToOne(fetch = FetchType.LAZY)
    private Evaluation evaluation;

    /** 标题 */
    private String title;

    /** 内容 */
    @Lob
    private String content;

    /** 图片地址 */
    private String picUrl;

    /** 题号 */
    private String qiNumber;

    /** 测评选项 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "evaluationInfo", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @OrderBy(value = "id asc")
    private Set<EvaluationOption> evaluationOptions;

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getQiNumber() {
        return qiNumber;
    }

    public void setQiNumber(String qiNumber) {
        this.qiNumber = qiNumber;
    }

    public Set<EvaluationOption> getEvaluationOptions() {
        return evaluationOptions;
    }

    public void setEvaluationOptions(Set<EvaluationOption> evaluationOptions) {
        this.evaluationOptions = evaluationOptions;
    }
}
