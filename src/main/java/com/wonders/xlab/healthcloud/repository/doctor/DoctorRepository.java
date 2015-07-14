package com.wonders.xlab.healthcloud.repository.doctor;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.doctor.Doctor;

import java.util.List;

/**
 * Created by mars on 15/7/2.
 */
public interface DoctorRepository extends MyRepository<Doctor, Long> {

    Doctor findByTel(String tel);

    List<Doctor> findByChecked(Doctor.Checked checked);

}
