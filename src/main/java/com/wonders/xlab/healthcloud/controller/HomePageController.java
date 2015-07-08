package com.wonders.xlab.healthcloud.controller;

import com.wonders.xlab.healthcloud.dto.hcpackage.DailyPackageDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageDetailRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageCompleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by mars on 15/7/8.
 */
@RestController
@RequestMapping("home")
public class HomePageController {

    @Autowired
    private UserPackageCompleteRepository userPackageCompleteRepository;

    @Autowired
    private HcPackageDetailRepository hcPackageDetailRepository;

    @RequestMapping("listHomePage/{userId}")
    public Object listHomePage(@PathVariable Long userId) {

        try {
// 查找没有完成的健康包
            List<UserPackageOrder> userPackageCompletes = this.userPackageCompleteRepository.findByUserIdAndPackageComplete(userId, false);

            // 包id
            List<Long> packageIds = new ArrayList<>();
            // 完成计划的Id
            List<Long> packageDetailIds = new ArrayList<>();
            for (UserPackageOrder upc : userPackageCompletes) {
                packageIds.add(upc.getHcPackage().getId());
                String[] strdetails = upc.getHcPackageDetailIds().split(",");
                Long[] longDetails = new Long[strdetails.length];
                for (int i = 0; i < strdetails.length; i++)
                    longDetails[i] = Long.parseLong(strdetails[i]);
                packageDetailIds.addAll(Arrays.asList(longDetails));
            }
            List<DailyPackageDto> hourTask = new ArrayList<>();
            List<DailyPackageDto> dayTask = new ArrayList<>();
            // 存在包id，说明有在完成的健康包
            if (packageIds.size() > 0) {
                // 查询所有任务，按照时间升序
                List<HcPackageDetail> hcPackageDetails = this.hcPackageDetailRepository.findByHcPackageIdsOrderByRecommendTimeFrom(packageIds);

                for (HcPackageDetail hcPackageDetail : hcPackageDetails) {
                    if (hcPackageDetail.isFullDay()) {
                        if (packageDetailIds.contains(hcPackageDetail.getId())) {
                            dayTask.add(new DailyPackageDto(
                                    hcPackageDetail.getId(),
                                    hcPackageDetail.getRecommendTimeFrom(),
                                    hcPackageDetail.getTaskName(),
                                    true));
                        } else {
                            dayTask.add(new DailyPackageDto(
                                    hcPackageDetail.getId(),
                                    hcPackageDetail.getRecommendTimeFrom(),
                                    hcPackageDetail.getTaskName(),
                                    false));
                        }
                    } else {
                        if (packageDetailIds.contains(hcPackageDetail.getId())) {
                            hourTask.add(new DailyPackageDto(
                                    hcPackageDetail.getId(),
                                    hcPackageDetail.getRecommendTimeFrom(),
                                    hcPackageDetail.getTaskName(),
                                    true));
                        } else {
                            hourTask.add(new DailyPackageDto(
                                    hcPackageDetail.getId(),
                                    hcPackageDetail.getRecommendTimeFrom(),
                                    hcPackageDetail.getTaskName(),
                                    false));
                        }
                    }
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("hourTask", hourTask);
            map.put("dayTask", dayTask);

            return new ControllerResult<Map<String, Object>>().setRet_code(0).setRet_values(map).setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values("失败啦").setMessage("失败");
        }

    }

}
