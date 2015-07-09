package com.wonders.xlab.healthcloud.controller;

import com.wonders.xlab.healthcloud.dto.hcpackage.DailyPackageDto;
import com.wonders.xlab.healthcloud.dto.hcpackage.ProgressDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.HomePageTips;
import com.wonders.xlab.healthcloud.entity.banner.Banner;
import com.wonders.xlab.healthcloud.entity.banner.BannerType;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import com.wonders.xlab.healthcloud.repository.TipsRepository;
import com.wonders.xlab.healthcloud.repository.banner.BannnerRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageDetailRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageCompleteRepository;
import com.wonders.xlab.healthcloud.utils.DateUtils;
import org.apache.commons.lang3.RandomUtils;
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

    @Autowired
    private TipsRepository tipsRepository;

    /**
     * 首页
     * @param userId
     * @return
     */
    @RequestMapping("listHomePage/{userId}")
    public Object listHomePage(@PathVariable long userId) {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            // 查询所有的标语
            List<Banner> topBanners = this.bannnerRepository.findByBannerTypeAndEnabled(BannerType.Top, true);
            List<Banner> bottomBanners = this.bannnerRepository.findByBannerTypeAndEnabled(BannerType.Bottom, true);

            Map<String, List<Banner>> bannerMap = new HashMap<>();
            bannerMap.put("topBanners", topBanners);
            bannerMap.put("bottomBanners", bottomBanners);

            resultMap.put("banner", bannerMap);

            // 查找没有完成的健康包
            List<UserPackageOrder> userPackageOrders = this.userPackageCompleteRepository.findByUserIdAndPackageComplete(userId, false);

            List<HcPackageDetail> allDetailList = new ArrayList<>();
            // 完成计划的Id
            List<Long> packageDetailIds = new ArrayList<>();

            // 查看完成度
            List<ProgressDto> progressDtos = new ArrayList<>();

            for (UserPackageOrder upo : userPackageOrders) {
                if (upo.getHcPackageDetailIds() != null) {
                    String[] strdetails = upo.getHcPackageDetailIds().split(",");
                    Long[] longDetails = new Long[strdetails.length];
                    for (int i = 0; i < strdetails.length; i++)
                        longDetails[i] = Long.parseLong(strdetails[i]);
                    packageDetailIds.addAll(Arrays.asList(longDetails));
                }
                // 持续时间
                int duration = upo.getHcPackage().getDuration();
                // 每个任务的时间 - 循环过的时间
                int day = DateUtils.calculatePeiorDaysOfTwoDate(upo.getCreatedDate(), new Date()) - duration * upo.getCurrentCycleIndex() + 1;
                int progress = 1;
                if (day != 1) {
                    progress = day * 100 / duration;
                }

                progressDtos.add(
                        new ProgressDto(
                                upo.getHcPackage().getHealthCategory().getTitle(),
                                upo.getHcPackage().getTitle(),
                                upo.getHcPackage().getSmaillIcon(),
                                progress
                        )
                );

                List<HcPackageDetail> hcPackageDetails = this.hcPackageDetailRepository.findByHcPackageIdOrderbyRecommendTimeFrom(upo.getHcPackage().getId(), day);
                allDetailList.addAll(hcPackageDetails);
            }
            // 添加进度
            resultMap.put("progress", progressDtos);

            List<DailyPackageDto> hourTasks = new ArrayList<>();
            List<DailyPackageDto> dayTasks = new ArrayList<>();


            // 默认完成 1 完成 0 未完成
            int hourComplete = 1;
            int dayComplete = 1;
            for (HcPackageDetail detail : allDetailList) {
                if (detail.isFullDay()) {
                    dayTasks.add(new DailyPackageDto(
                                    detail.getId(),
                                    detail.getRecommendTimeFrom(),
                                    detail.getTaskName(),
                                    packageDetailIds.contains(detail.getId()),
                                    detail.getClickAmount()
                            )
                    );
                    // 如果没有包含id，说明没有完成
                    if (packageDetailIds.contains(detail.getId()))
                        dayComplete = 0;
                } else {
                    hourTasks.add(new DailyPackageDto(
                                    detail.getId(),
                                    detail.getRecommendTimeFrom(),
                                    detail.getTaskName(),
                                    packageDetailIds.contains(detail.getId()),
                                    detail.getClickAmount()
                            )
                    );
                    if (packageDetailIds.contains(detail.getId()))
                        hourComplete = 0;
                }
            }
            List<HomePageTips> tips = this.tipsRepository.findAll();
            Map<String, Object> taskMap = new HashMap<>();
           /* for (int i = 0; i < 2; i ++){
                int index = (int) System.currentTimeMillis() % tips.size();
                taskMap.put("dayTips", tips.get(index).getTips());
                if (taskMap.get("dayTips") != null) {
                    taskMap.put("hourTips", tips.get(index).getTips());
                }
            }*/
            taskMap.put("currentDay", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
            taskMap.put("hourTask", hourTasks);
            taskMap.put("dayTask", dayTasks);
            taskMap.put("dayComplete", dayComplete);
            taskMap.put("hourComplete", hourComplete);
            taskMap.put("dayTips", tips.get(RandomUtils.nextInt(0, tips.size())).getTips());
            taskMap.put("hourTips", tips.get(RandomUtils.nextInt(0, tips.size())).getTips());

            resultMap.put("task", taskMap);

            return new ControllerResult<Map<String, Object>>().setRet_code(0).setRet_values(resultMap).setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values("失败啦").setMessage("失败");
        }

    }

}
