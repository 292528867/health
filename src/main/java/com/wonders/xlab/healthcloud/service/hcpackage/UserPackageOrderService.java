package com.wonders.xlab.healthcloud.service.hcpackage;

import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageOrderRepository;
import com.wonders.xlab.healthcloud.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jeffrey on 15/7/9.
 */
@Service
public class UserPackageOrderService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserPackageOrderRepository userPackageOrderRepository;

    void scheduleCalculateIsPackageFinished() {
        //获取未完成任务的包
        List<UserPackageOrder> userP = userPackageOrderRepository.findFetchPackageByPackageCompleteFalse();

        List<UserPackageOrder> userPackageOrdersUnloops = userPackageOrderRepository.findByPackageCompleteAndPackageLoops(false, false);
        List<UserPackageOrder> userPackageOrdersloops = userPackageOrderRepository.findByPackageCompleteAndPackageLoops(false, true);

        List<UserPackageOrder> userPackageOrdersCompleted = new ArrayList<>();
        for (UserPackageOrder userPackageOrder : userPackageOrdersUnloops) {
            //健康包设定持续时间小于或者等于健康包持续到当前时间的天数
            if (userPackageOrder.getHcPackage().getDuration() <= DateUtils.calculatePeiorDaysOfTwoDate(new Date(), userPackageOrder.getCreatedDate())) {
                userPackageOrdersCompleted.add(userPackageOrder);
            }
        }
        try {
            userPackageOrderRepository.save(userPackageOrdersCompleted);
        } catch (Exception e) {
            logger.info("scheduleCalculateIsPackageFinished Tasks update UserPackageOrderCompleted of size={} throw e={}",userP.size(),e.getLocalizedMessage());
        }
    }
}
