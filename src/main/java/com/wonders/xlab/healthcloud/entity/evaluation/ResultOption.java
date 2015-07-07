package com.wonders.xlab.healthcloud.entity.evaluation;

import com.wonders.xlab.healthcloud.entity.AbstractBaseEntity;

import javax.persistence.*;

/**
 * 结果选项
 * Created by mars on 15/7/6.
 */
@Entity
@Table(name = "hc_result_option")
public class ResultOption extends AbstractBaseEntity<Long> {

    /** 结果 */
    @ManyToOne(fetch = FetchType.LAZY)
    private Result result;

    /** 标题 */
    private String titile;

    /** 内容 */
    @Lob
    private String content;

    /** 最低分 */
    private double lowestScore;

    /** 最高分 */
    private double highestScore;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getLowestScore() {
        return lowestScore;
    }

    public void setLowestScore(double lowestScore) {
        this.lowestScore = lowestScore;
    }

    public double getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(double highestScore) {
        this.highestScore = highestScore;
    }
}
