package com.wonders.xlab.healthcloud.controller;

import com.wonders.xlab.healthcloud.dto.hcpackage.DailyPackageDto;
import com.wonders.xlab.healthcloud.dto.hcpackage.ProgressDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.banner.Banner;
import com.wonders.xlab.healthcloud.entity.banner.BannerType;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import com.wonders.xlab.healthcloud.repository.banner.BannnerRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageDetailRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageCompleteRepository;
import org.apache.commons.lang3.time.DateFormatUtils;
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

    @Autowired
    private BannnerRepository bannnerRepository;

    @RequestMapping("listHomePage/{userId}")
    public Object listHomePage(@PathVariable Long userId) {
        try {
            Map<String, Object> map = new HashMap<>();
            // 查询所有的标语
            List<Banner> banners = this.bannnerRepository.findByEnabled(true);
            List<Banner> topBanners = new ArrayList<>();
            List<Banner> bottomBanners = new ArrayList<>();
            for (Banner banner : banners) {
                if (banner.getBannerType() == BannerType.Top.ordinal())
                    topBanners.add(banner);
                if (banner.getBannerType() == BannerType.Bottom.ordinal())
                    bottomBanners.add(banner);
            }
            Map<String, Object> bannerMap = new HashMap<>();
            bannerMap.put("topBanners", topBanners);
            bannerMap.put("bottomBanners", bottomBanners);

            map.put("banner", bannerMap);

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
                List<HcPackageDetail> hcPackageDetails = this.hcPackageDetailRepository.findByHcPackageIdsOrderByRecommendTimeFrom(packageIds, 1);

                for (HcPackageDetail hcPackageDetail : hcPackageDetails) {
                    if (hcPackageDetail.isFullDay()) {
                        if (packageDetailIds.contains(hcPackageDetail.getId())) {
                            dayTask.add(new DailyPackageDto(
                                    hcPackageDetail.getId(),
                                    hcPackageDetail.getRecommendTimeFrom(),
                                    hcPackageDetail.getTaskName(),
                                    true,
                                    hcPackageDetail.getClickAmount()
                                    )
                            );
                        } else {
                            dayTask.add(new DailyPackageDto(
                                    hcPackageDetail.getId(),
                                    hcPackageDetail.getRecommendTimeFrom(),
                                    hcPackageDetail.getTaskName(),
                                    false,
                                    hcPackageDetail.getClickAmount()
                                    )
                            );
                        }
                    } else {
                        if (packageDetailIds.contains(hcPackageDetail.getId())) {
                            hourTask.add(new DailyPackageDto(
                                    hcPackageDetail.getId(),
                                    hcPackageDetail.getRecommendTimeFrom(),
                                    hcPackageDetail.getTaskName(),
                                    true,
                                    hcPackageDetail.getClickAmount()
                                    )
                            );
                        } else {
                            hourTask.add(new DailyPackageDto(
                                    hcPackageDetail.getId(),
                                    hcPackageDetail.getRecommendTimeFrom(),
                                    hcPackageDetail.getTaskName(),
                                    false,
                                    hcPackageDetail.getClickAmount()
                                    )
                            );
                        }
                    }
                }
            }

            Map<String, Object> taskMap = new HashMap<>();
            taskMap.put("currentDay", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
            taskMap.put("hourTask", hourTask);
            taskMap.put("dayTask", dayTask);

            map.put("task", taskMap);

            // 查看完成度
            List<ProgressDto> progressDtos = new ArrayList<>();
            // 用户正在完成的包 userPackageCompletes
            for (UserPackageOrder upo : userPackageCompletes) {
                // 计划开始时间
                int startTime = Integer.parseInt(DateFormatUtils.format(upo.getCreatedDate(), "yyyyMMdd"));
                // 当前时间
                int currentTime = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));
                // 持续时间
                int duration = upo.getHcPackage().getDuration();

                int progress = (currentTime - startTime - duration * upo.getHcPackage().getCycleIndex()) * 100 / duration ;


                progressDtos.add(
                        new ProgressDto(
                                upo.getHcPackage().getTitle(),
                                upo.getHcPackage().getDescription(),
                                upo.getHcPackage().getIcon(),
                                progress
                        )
                );
            }
            map.put("progress", progressDtos);

            return new ControllerResult<Map<String, Object>>().setRet_code(0).setRet_values(map).setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values("失败啦").setMessage("失败");
        }

    }

}
