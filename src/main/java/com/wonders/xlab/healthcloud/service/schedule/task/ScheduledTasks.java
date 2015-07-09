package com.wonders.xlab.healthcloud.service.schedule.task;

import com.wonders.xlab.healthcloud.service.hcpackage.UserPackageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Jeffrey on 15/7/8.
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    private UserPackageOrderService userPackageOrderService;

    @Scheduled(cron = "0 1 0 * * ? ")
    public void scheduleCalculateIsPackageFinished1() {
        userPackageOrderService.scheduleCalculateIsPackageFinished(0);
    }

    @Scheduled(cron = "0 9 0 * * ? ")
    public void scheduleCalculateIsPackageFinished2() {
        userPackageOrderService.scheduleCalculateIsPackageFinished(1);
    }

    @Scheduled(cron = "0 16 0 * * ? ")
    public void scheduleCalculateIsPackageFinished3() {
        userPackageOrderService.scheduleCalculateIsPackageFinished(2);
    }
}
