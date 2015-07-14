package com.wonders.xlab.healthcloud.repository.doctor;

import com.wonders.xlab.framework.Application;
import com.wonders.xlab.healthcloud.entity.doctor.Doctor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Jeffrey on 15/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void testFindByChecked() throws Exception {
        List<Doctor> list = doctorRepository.findByChecked(Doctor.Checked.fail);
        Assert.isTrue(list.size() == 3);
    }
}