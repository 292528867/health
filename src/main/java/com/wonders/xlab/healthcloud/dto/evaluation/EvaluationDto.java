package com.wonders.xlab.healthcloud.dto.evaluation;

import com.wonders.xlab.healthcloud.entity.evaluation.Evaluation;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by mars on 15/7/4.
 */

public class EvaluationDto {

    /** 标题 */
    @NotNull(message = "标题不能为空")
    private String title;

    /** 指导语 */
    @NotNull(message = "指导语不能为空")
    private String instruction;

    /** 简述 */
    @NotNull(message = "简述不能为空")
    private String description;

    @NotNull(message = "推荐值不能为空")
    private String recommendedValue;

    @NotNull(message = "提示不能为空")
    private String tips;

    @Enumerated
    @NotNull(message = "状态不能为空")
    @Pattern(regexp = "^0|1$", message = "状态位必须位0 线上 1 线下")
    private String status;

    public Evaluation toNewEvalution() {
        Evaluation question = new Evaluation(
                title,
                instruction,
                description,
                Integer.parseInt(recommendedValue),
                Evaluation.Status.values()[Integer.parseInt(status)]
        );
        return question;
    }

    public Evaluation updateEvalution(Evaluation evaluation) {
        evaluation.setTitle(title);
        evaluation.setInstruction(instruction);
        evaluation.setDescription(description);
        evaluation.setRecommendedValue(Integer.parseInt(recommendedValue));
        evaluation.setStatus(Evaluation.Status.values()[Integer.parseInt(status)]);
        return evaluation;
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

    public String getRecommendedValue() {
        return recommendedValue;
    }

    public void setRecommendedValue(String recommendedValue) {
        this.recommendedValue = recommendedValue;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
