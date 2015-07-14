package com.wonders.xlab.healthcloud.repository.customer;

import com.wonders.xlab.framework.Application;
import com.wonders.xlab.healthcloud.entity.customer.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Jeffrey on 15/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindOne() throws Exception {

        User user = userRepository.findOne(47l);
        System.out.println("user.getHcPackages() = " + user.getHcPackages());
    }
}