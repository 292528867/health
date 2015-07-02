package com.wonders.xlab.healthcloud.entity.doctor;

import com.wonders.xlab.healthcloud.entity.ThirdBaseInfo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by mars on 15/7/2.
 */
@Entity
@Table(name = "hc_doctor_third")
public class DoctorThird extends ThirdBaseInfo<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    private Doctor doctor;

    public DoctorThird() {
        super();
    }

    public DoctorThird(String thirdId, ThirdType thirdType) {
        super(thirdId, thirdType);
    }

    public DoctorThird(String thirdId, ThirdType thirdType, Doctor doctor) {
        super(thirdId, thirdType);
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
