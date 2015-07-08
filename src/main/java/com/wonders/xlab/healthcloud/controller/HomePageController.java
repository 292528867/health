package com.wonders.xlab.healthcloud.controller;

import com.wonders.xlab.healthcloud.dto.hcpackage.DailyPackageDto;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageComplete;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageDetailRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageCompleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        // 查找没有完成的健康包
        List<UserPackageComplete> userPackageCompletes = this.userPackageCompleteRepository.findByUserIdAndPackageComplete(userId, false);

        List<Long> packageIds = new ArrayList<>();
        for (UserPackageComplete upc : userPackageCompletes) {
            packageIds.add(upc.getHcPackage().getId());
        }
        if (packageIds.size() > 0) {
            List<HcPackageDetail> hcPackageDetails = this.hcPackageDetailRepository.findByHcPackageIds(packageIds);

            List<DailyPackageDto> hourTask = new ArrayList<>();
            List<DailyPackageDto> dayTask = new ArrayList<>();

            Date now = new Date();



            for (HcPackageDetail hcPackageDetail : hcPackageDetails) {

            }


        }




        return null;
    }

}
