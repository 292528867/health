package com.wonders.xlab.healthcloud.service.hcpackage;

import com.wonders.xlab.framework.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import java.util.Calendar;

/**
 * Created by Jeffrey on 15/7/9.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class HcpackageServiceTest {

    @Autowired
    private HcpackageService hcpackageService;

    @Test
    public void testCalculateClickCount() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 7, 6);

        System.out.println(Math.pow(1.02,10));
        int clickCount = hcpackageService.calculateClickCount(5, calendar.getTime());
        Assert.isTrue(clickCount == 7);
    }
}