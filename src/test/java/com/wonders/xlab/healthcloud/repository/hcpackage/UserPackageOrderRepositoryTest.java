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
public class UserPackageOrderRepositoryTest {

    @Autowired
    private UserPackageOrderRepository userPackageOrderRepository;

    @Test
    public void testFindFetchPackageByPackageCompleteFalse() throws Exception {

    }

    @Test
    public void testFindByUserId() throws Exception {

    }

    @Test
    public void testFindByUserIdAndPackageCompleteFalse() throws Exception {
        System.out.println("-----------------------------------------------------------");
        System.out.println(userPackageOrderRepository.findByUserIdAndPackageCompleteFalse(47L));
        System.out.println("=================================================");
    }

    @Test
    public void testFindByUserAndHcPackageAndPackageComplete() throws Exception {

    }

    @Test
    public void testFindByUserIdAndHcPackageIdAndPackageComplete() throws Exception {

    }
}