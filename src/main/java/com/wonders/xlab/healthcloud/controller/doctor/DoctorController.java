package com.wonders.xlab.healthcloud.controller.doctor;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.doctor.Doctor;
import com.wonders.xlab.healthcloud.repository.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mars on 15/7/2.
 */
@RestController
@RequestMapping("doctor")
public class DoctorController extends AbstractBaseController<Doctor, Long> {



    @Autowired
    private DoctorRepository doctorRepository;
    @Override
    protected MyRepository<Doctor, Long> getRepository() {
        return doctorRepository;
    }


}
