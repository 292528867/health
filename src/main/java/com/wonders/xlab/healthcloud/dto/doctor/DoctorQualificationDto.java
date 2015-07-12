package com.wonders.xlab.healthcloud.dto.doctor;

/**
 * Created by Jeffrey on 15/7/12.
 */
public class DoctorQualificationDto {
    /**
     * 资质
     */
    private String recordUrl;
    /**
     * 职称证
     */
    private String qualification;

    /**
     * 执行认证
     */
    private String permit;

    public DoctorQualificationDto() {
    }

    public DoctorQualificationDto(String recordUrl, String qualification, String permit) {
        this.recordUrl = recordUrl;
        this.qualification = qualification;
        this.permit = permit;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPermit() {
        return permit;
    }

    public void setPermit(String permit) {
        this.permit = permit;
    }
}
