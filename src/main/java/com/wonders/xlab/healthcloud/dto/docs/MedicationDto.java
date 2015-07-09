package com.wonders.xlab.healthcloud.dto.docs;

/**
 * Created by mars on 15/7/9.
 */
public class MedicationDto {

    /** 时间 */
    private String time;

    /** 药名 */
    private String medicineName;

    /** 服用方式 */
    private String takeMethod;

    public MedicationDto() {
    }

    public MedicationDto(String time, String medicineName, String takeMethod) {
        this.time = time;
        this.medicineName = medicineName;
        this.takeMethod = takeMethod;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getTakeMethod() {
        return takeMethod;
    }

    public void setTakeMethod(String takeMethod) {
        this.takeMethod = takeMethod;
    }
}
