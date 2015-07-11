package com.wonders.xlab.healthcloud.service.hcpackage;

import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageOrderRepository;
import com.wonders.xlab.healthcloud.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jeffrey on 15/7/9.
 */
@Service("userPackageOrderService")
public class UserPackageOrderServiceImpl implements UserPackageOrderService{

    private static Logger logger = LoggerFactory.getLogger(UserPackageOrderServiceImpl.class);

    @Autowired
    private UserPackageOrderRepository userPackageOrderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HcPackageRepository hcPackageRepository;

    @Override
    @Transactional
    public void scheduleCalculateIsPackageFinished(int aliquotNumber) {
        /**
         * 以除3取余aliquotNumber分线程执行调度任务
         */
        //获取未完成不轮训任务的包
        List<UserPackageOrder> userPackageOrdersUnloops = userPackageOrderRepository
                .findByPackageCompleteAndPackageLoopsRemainder(false, false, aliquotNumber);
        //获取未完成轮训任务的包
        List<UserPackageOrder> userPackageOrdersloops = userPackageOrderRepository
                .findByPackageCompleteAndPackageLoopsRemainder(false, true, aliquotNumber);

        List<UserPackageOrder> userPackageOrdersCompleted = new ArrayList<>();
        //不循环包
        for (UserPackageOrder userPackageOrder : userPackageOrdersUnloops) {
            //健康包设定持续时间小于或者等于健康包持续到当前时间的天数
            if (userPackageOrder.getHcPackage().getDuration() < DateUtils.calculateDaysOfTwoDateIgnoreHours(
                    new Date(), userPackageOrder.getCreatedDate())) {
                //设置为包完成
                userPackageOrder.setPackageComplete(true);
                userPackageOrdersCompleted.add(userPackageOrder);
            }
        }
        //循环包
        for (UserPackageOrder userPackageOrdersloop : userPackageOrdersloops) {
            //健康包设定持续时间小于或者等于健康包持续到当前时间的天数
            if (userPackageOrdersloop.getHcPackage().getDuration() < DateUtils.calculateDaysOfTwoDateIgnoreHours(
                    new Date(), userPackageOrdersloop.getCreatedDate())) {
                //设置为包完成
                userPackageOrdersloop.setPackageComplete(true);
                userPackageOrdersCompleted.add(userPackageOrdersloop);
                //包当前循环次数小于设定循环次数或者小于4次，创建包新计划，设置循环次数为循环包当前＋1
                if (userPackageOrdersloop.getCurrentCycleIndex() < userPackageOrdersloop.getCycleIndex() &&
                        userPackageOrdersloop.getCurrentCycleIndex() < 4) {
                    UserPackageOrder userPackageOrder = new UserPackageOrder();
                    userPackageOrder.setHcPackage(userPackageOrdersloop.getHcPackage());
                    userPackageOrder.setUser(userPackageOrdersloop.getUser());
                    userPackageOrder.setCurrentCycleIndex(userPackageOrdersloop.getCurrentCycleIndex() + 1);
                    userPackageOrdersCompleted.add(userPackageOrder);
                }
            }
        }
        try {
            if (userPackageOrdersCompleted.size() != 0) {
                userPackageOrderRepository.save(userPackageOrdersCompleted);
            }
        } catch (Exception e) {
            logger.info("scheduleCalculateIsPackageFinished Tasks update UserPackageOrderCompleted of size={} throw e={}", userPackageOrdersCompleted.size(), e.getLocalizedMessage());
        }
    }

    @Override
    public Object joinPlan(Long userId, Long packageId) {

        List<UserPackageOrder> userPackageOrders = userPackageOrderRepository.findFetchPackageByUserIdAndPackageCompleteFalse(userId);

        //订购包大于2
        if (userPackageOrders != null && userPackageOrders.size() >= 2) {
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values("")
                    .setMessage("最多选择两个健康包！");
        }
        //未完成健康包已包含传入包
        for (UserPackageOrder userPackageOrder : userPackageOrders) {
            if (packageId == userPackageOrder.getHcPackage().getId()) {
                return new ControllerResult<>()
                        .setRet_code(-1)
                        .setRet_values("")
                        .setMessage("健康包已加入！");
            }
        }

        try {
            User user = userRepository.findOne(userId);
            HcPackage hcPackage = hcPackageRepository.findOne(packageId);
            UserPackageOrder userPackageOrder = new UserPackageOrder();
            userPackageOrder.setUser(user);
            userPackageOrder.setHcPackage(hcPackage);
            userPackageOrder.setCycleIndex(hcPackage.getDuration());
            userPackageOrderRepository.save(userPackageOrder);
            return new ControllerResult<>()
                    .setRet_code(0)
                    .setRet_values("")
                    .setMessage("加入成功！");
        } catch (Exception e) {
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values("")
                    .setMessage("加入失败！");
        }

    }

}
