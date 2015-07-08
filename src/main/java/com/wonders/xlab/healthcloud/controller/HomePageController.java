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

    /**
     * 首页
     * @param userId
     * @return
     */
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

            // 当前时间
            int currentTime = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));

            List<HcPackageDetail> allDetailList = new ArrayList<>();
            // 完成计划的Id
            List<Long> packageDetailIds = new ArrayList<>();

            // 查看完成度
            List<ProgressDto> progressDtos = new ArrayList<>();

            for (UserPackageOrder upo : userPackageCompletes) {
                String[] strdetails = upo.getHcPackageDetailIds().split(",");
                Long[] longDetails = new Long[strdetails.length];
                for (int i = 0; i < strdetails.length; i++)
                    longDetails[i] = Long.parseLong(strdetails[i]);
                packageDetailIds.addAll(Arrays.asList(longDetails));
                // 计划开始时间
                int startTime = Integer.parseInt(DateFormatUtils.format(upo.getCreatedDate(), "yyyyMMdd"));
                // 持续时间
                int duration = upo.getHcPackage().getDuration();
                // 每个任务的时间
                int day = currentTime - startTime - duration * upo.getHcPackage().getCycleLimit() + 1;

                int progress = day * 100 / duration ;

                progressDtos.add(
                        new ProgressDto(
                                upo.getHcPackage().getTitle(),
                                upo.getHcPackage().getDescription(),
                                upo.getHcPackage().getSmaillIcon(),
                                progress
                        )
                );


                List<HcPackageDetail> hcPackageDetails = this.hcPackageDetailRepository.findByHcPackageIdOrderbyRecommendTimeFrom(upo.getHcPackage().getId(), day);
                allDetailList.addAll(hcPackageDetails);
            }
            // 添加进度
            map.put("progress", progressDtos);

            List<DailyPackageDto> hourTask = new ArrayList<>();
            List<DailyPackageDto> dayTask = new ArrayList<>();

            for (HcPackageDetail detail : allDetailList) {
                if (detail.isFullDay()) {
                    if (packageDetailIds.contains(detail.getId())) {
                        dayTask.add(new DailyPackageDto(
                                        detail.getId(),
                                        detail.getRecommendTimeFrom(),
                                        detail.getTaskName(),
                                        true,
                                        detail.getClickAmount()
                                )
                        );
                    } else {
                        dayTask.add(new DailyPackageDto(
                                        detail.getId(),
                                        detail.getRecommendTimeFrom(),
                                        detail.getTaskName(),
                                        false,
                                        detail.getClickAmount()
                                )
                        );
                    }
                } else {
                    if (packageDetailIds.contains(detail.getId())) {
                        hourTask.add(new DailyPackageDto(
                                        detail.getId(),
                                        detail.getRecommendTimeFrom(),
                                        detail.getTaskName(),
                                        true,
                                        detail.getClickAmount()
                                )
                        );
                    } else {
                        hourTask.add(new DailyPackageDto(
                                        detail.getId(),
                                        detail.getRecommendTimeFrom(),
                                        detail.getTaskName(),
                                        false,
                                        detail.getClickAmount()
                                )
                        );
                    }
                }
            }

            Map<String, Object> taskMap = new HashMap<>();
            taskMap.put("currentDay", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
            taskMap.put("hourTask", hourTask);
            taskMap.put("dayTask", dayTask);

            map.put("task", taskMap);

            return new ControllerResult<Map<String, Object>>().setRet_code(0).setRet_values(map).setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values("失败啦").setMessage("失败");
        }

    }

}
