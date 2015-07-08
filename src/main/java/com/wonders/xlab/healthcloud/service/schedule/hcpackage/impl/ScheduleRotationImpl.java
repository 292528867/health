package com.wonders.xlab.healthcloud.service.schedule.hcpackage.impl;

import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageOrderRepository;
import com.wonders.xlab.healthcloud.service.schedule.hcpackage.IScheduleRotation;
import com.wonders.xlab.healthcloud.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jeffrey on 15/7/8.
 */
@Service
public class ScheduleRotationImpl implements IScheduleRotation {

    @Autowired
    private UserPackageOrderRepository userPackageOrderRepository;

    void packageTaskRotation() {

        List<UserPackageOrder> userPackageOrders = userPackageOrderRepository.findFetchPackageByPackageCompleteFalse();

        List<UserPackageOrder> userPackageOrdersCompleted = new ArrayList<>();

        for (UserPackageOrder userPackageOrder : userPackageOrders) {
            if (userPackageOrder.getHcPackage().getDuration() < DateUtils.calculatePeiorDaysOfTwoDate(new Date(), userPackageOrder.getCreatedDate())) {
                userPackageOrdersCompleted.add(userPackageOrder);
            }
        }

        userPackageOrderRepository.save(userPackageOrdersCompleted);
    }
}
