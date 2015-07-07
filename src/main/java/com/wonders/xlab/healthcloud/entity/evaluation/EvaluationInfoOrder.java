package com.wonders.xlab.healthcloud.entity.evaluation;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 测评顺序
 * Created by mars on 15/7/5.
 */
@Entity
@Table(name = "hc_evaluation_info_order")
public class EvaluationInfoOrder extends AbstractBaseEntity<Long> {

    /** 测评 */
    @ManyToOne(fetch = FetchType.LAZY)
    private Evaluation evaluation;

    /** 问题 */
    private long questionId;

    /** 本题选项结果 */
    private long optionId;

    /** 下一题 */
    private long nextQuestionId;

    /** 结果id */
    private long resultId;

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public long getNextQuestionId() {
        return nextQuestionId;
    }

    public void setNextQuestionId(long nextQuestionId) {
        this.nextQuestionId = nextQuestionId;
    }

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }
}
