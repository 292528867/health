package com.wonders.xlab.healthcloud.service.hcpackage;

import org.junit.Test;
import org.springframework.util.Assert;

import java.util.Calendar;

/**
 * Created by Jeffrey on 15/7/9.
 */

public class HcpackageServiceTest {

    private HcpackageService hcpackageService = new HcpackageService();

    @Test
    public void testCalculateClickCount() throws Exception {
        Calendar calendar = Calendar.getInstance();
//        calendar.set(2015, 7, 6);

        System.out.println(Math.pow(1.02, 10));
        int clickCount = hcpackageService.calculateClickCount(0, 7, calendar.getTime());
        System.out.println("clickCount = " + clickCount);
//        Assert.isTrue(clickCount == );
    }
}