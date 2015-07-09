package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by Jeffrey on 15/7/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class HcPackageRepositoryTest {

    @Autowired
    private HcPackageRepository hcPackageRepository;

    @Test
    public void testFindAllOrderByCreateDate() throws Exception {

    }

    @Test
    public void testFindByHealthCategoryId() throws Exception {

    }

    @Test
    public void testFindByClassificationId() throws Exception {
        System.out.println("-------------------------------------");
        System.out.println(hcPackageRepository.findByClassificationId(4l).size());
        System.out.println("=====================================");
    }
}