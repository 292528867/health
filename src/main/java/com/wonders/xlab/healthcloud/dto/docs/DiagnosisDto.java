package com.wonders.xlab.healthcloud.dto.docs;

/**
 * Created by mars on 15/7/9.
 */
public class DiagnosisDto {

    /** 时间 */
    private String time;

    /** 地区 */
    private String area;

    /** 就诊方式 */
    private String treatmentMethod;

    /** 病例 */
    private String disease;

    public DiagnosisDto() {
    }

    public DiagnosisDto(String time, String area, String treatmentMethod, String disease) {
        this.time = time;
        this.area = area;
        this.treatmentMethod = treatmentMethod;
        this.disease = disease;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTreatmentMethod() {
        return treatmentMethod;
    }

    public void setTreatmentMethod(String treatmentMethod) {
        this.treatmentMethod = treatmentMethod;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }
}
