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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping(value = "listHomePage/{userId}", method = RequestMethod.GET)
    public Object listHomePage(@PathVariable long userId,
                               @PageableDefault(page = 0, size = 1//, sort = "recommendTimeFrom", direction = Sort.Direction.DESC
                               )
                               Pageable pageable) {
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
            List<Long> completeDetailIds = new ArrayList<>();
            // 查看完成度
            List<ProgressDto> progressDtos = new ArrayList<>();

            List<Long> currentDetailIds = new ArrayList<>();

            Date now = new Date();

            // 查找用户完成的任务
            for (UserPackageOrder upo : userPackageOrders) {
                if (upo.getHcPackageDetailIds() != null) {
                    String[] strdetails = upo.getHcPackageDetailIds().split(",");
                    Long[] longDetails = new Long[strdetails.length];
                    for (int i = 0; i < strdetails.length; i++)
                        longDetails[i] = Long.parseLong(strdetails[i]);
                    completeDetailIds.addAll(Arrays.asList(longDetails));
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
                                upo.getHcPackage().getHealthCategory().getClassification().getTitle(),
                                upo.getHcPackage().getTitle(),
                                upo.getHcPackage().getSmallIcon(),
                                progress
                        )
                );
//                // 查询当前包的需要完成当天任务
//                List<HcPackageDetail> hcPackageDetails;
//                if (completeDetailIds.size() > 0) {
//                    hcPackageDetails = this.hcPackageDetailRepository.findByHcPackageIdOrderbyRecommendTimeFrom(upo.getHcPackage().getId(), day, completeDetailIds);
//                } else {
//                    hcPackageDetails = this.hcPackageDetailRepository.findByHcPackageIdOrderbyRecommendTimeFrom(upo.getHcPackage().getId(), day);
//                }

                // 查询当天离现在最近的任务
                Page<HcPackageDetail> topDetailFrom;
                Page<HcPackageDetail> bottomDetailFrom;

                if (completeDetailIds.size() > 0) {
                    // 存在已完成的任务id
                    topDetailFrom = this.hcPackageDetailRepository.findByPackageIdAndIsFullDayOrderByTimeFromAndPageable(upo.getHcPackage().getId(), false, day, now, completeDetailIds , pageable);
                    bottomDetailFrom = this.hcPackageDetailRepository.findByPackageIdAndIsFullDayOrderByTimeFromAsc(upo.getHcPackage().getId(), false, day, now, completeDetailIds, pageable);


                } else {
                    topDetailFrom = this.hcPackageDetailRepository.findByPackageIdAndIsFullDayOrderByTimeFromAndPageable(upo.getHcPackage().getId(), false, day, now, pageable);
                    bottomDetailFrom = this.hcPackageDetailRepository.findByPackageIdAndIsFullDayOrderByTimeFromAsc(upo.getHcPackage().getId(), false, day, now, pageable);
                }
                // 获取第一个
                if (topDetailFrom.hasContent()) {
                    currentDetailIds.add(topDetailFrom.getContent().get(0).getId());
                    allDetailList.add(topDetailFrom.getContent().get(0));
                }
                if (bottomDetailFrom.hasContent()) {
                    if (!currentDetailIds.contains(topDetailFrom.getContent().get(0).getId()))
                        allDetailList.add(bottomDetailFrom.getContent().get(0));
                }

//                allDetailList.addAll(hcPackageDetails);
            }

            // 添加进度
            resultMap.put("progress", progressDtos);

            List<DailyPackageDto> tasks = new ArrayList<>();
            for (HcPackageDetail detail : allDetailList) {
                DailyPackageDto dto = new DailyPackageDto(
                        detail.getId(),
                        detail.getRecommendTimeFrom(),
                        detail.getTaskName(),
                        detail.getClickAmount()
                );
                tasks.add(dto);
            }
//            List<DailyPackageDto> dayTasks = new ArrayList<>();

            // 小时任务栏，全天任务栏 默认完成 1 完成 0 未完成
//            int hourComplete = 1;
//            int dayComplete = 1;

//            for (HcPackageDetail detail : allDetailList) {
//                DailyPackageDto dto = new DailyPackageDto(
//                        detail.getId(),
//                        detail.getRecommendTimeFrom(),
//                        detail.getTaskName(),
//                        detail.getClickAmount()
//                );
//                // 存在完成任务的id，说明已完成
//                if (completeDetailIds.contains(detail.getId()))
//                    dto.setIsCompleted(1);
//                // 如果不包含任务id，说明还有任务没有完成
//                if (completeDetailIds.size() > 0 && !completeDetailIds.contains(detail.getId()))
//                    dayComplete = 0;
//                // 判断当前是否是现在数据
//                if (currentDetailIds.contains(detail.getId()))
//                    dto.setCurrent(1);
//                if (detail.isFullDay()) {
//                    dayTasks.add(dto);
//                } else {
//                    tasks.add(dto);
//                }
//            }
            Map<String, Object> taskMap = new HashMap<>();
            List<HomePageTips> tips = this.tipsRepository.findAll();

           /* for (int i = 0; i < 2; i ++){
                int index = (int) System.currentTimeMillis() % tips.size();
                taskMap.put("dayTips", tips.get(index).getTips());
                if (taskMap.get("dayTips") != null) {
                    taskMap.put("hourTips", tips.get(index).getTips());
                }
            }*/
            List<String> allTips = new ArrayList<>();

            if (tasks.size() == 1) {
                allTips.add(tips.get(RandomUtils.nextInt(0, tips.size())).getTips());
            } else if (tasks.size() == 0) {
                allTips.add(tips.get(RandomUtils.nextInt(0, tips.size())).getTips());
                allTips.add(tips.get(RandomUtils.nextInt(0, tips.size())).getTips());
            }
            taskMap.put("currentDay", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
//            taskMap.put("hourTask", tasks);
//            taskMap.put("task", tasks);
//            taskMap.put("dayComplete", dayComplete);
//            taskMap.put("hourComplete", hourComplete);
//            taskMap.put("dayTips", tips.get(RandomUtils.nextInt(0, tips.size())).getTips());
//            taskMap.put("hourTips", tips.get(RandomUtils.nextInt(0, tips.size())).getTips());
            resultMap.put("tips", allTips);


            resultMap.put("task", tasks);

            return new ControllerResult<Map<String, Object>>().setRet_code(0).setRet_values(resultMap).setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values("失败啦").setMessage("失败");
        }

    }


}
