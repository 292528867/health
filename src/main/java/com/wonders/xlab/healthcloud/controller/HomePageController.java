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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

//            JsonNodeFactory factory = JsonNodeFactory.instance;
//            ObjectNode resultNode = factory.objectNode();

            // 查询所有的标语
            List<Banner> topBanners = this.bannnerRepository.findByBannerTypeAndEnabled(BannerType.Top, true);
            List<Banner> bottomBanners = this.bannnerRepository.findByBannerTypeAndEnabled(BannerType.Bottom, true);

            Map<String, List<Banner>> bannerMap = new HashMap<>();
            bannerMap.put("topBanners", topBanners);
            bannerMap.put("bottomBanners", bottomBanners);

//            resultNode.putPOJO("topBanners", topBanners);
//            resultNode.putPOJO("bottomBanners", bottomBanners);

            resultMap.put("banner", bannerMap);
//            resultNode.putPOJO("banner", bannerMap);



            // 查找没有完成的健康包
            List<UserPackageOrder> userPackageOrders = this.userPackageCompleteRepository.findByUserIdAndPackageComplete(userId, false);

            List<HcPackageDetail> allDetailList = new ArrayList<>();
            List<HcPackageDetail> trueDetailList = new ArrayList<>();
            // 完成计划的Id
            List<Long> completeDetailIds = new ArrayList<>();
            // 查看完成度
            List<ProgressDto> progressDtos = new ArrayList<>();
            Date now = new Date();
            // 查找用户完成的任务
            for (UserPackageOrder upo : userPackageOrders) {

                String[] strdetails = StringUtils.split(upo.getHcPackageDetailIds(), ',');
                if (strdetails != null) {
                    for (int i = 0; i < strdetails.length; i++) {
                        completeDetailIds.add(NumberUtils.toLong(strdetails[i]));
                    }
                }

                // 持续时间
                int duration = upo.getHcPackage().getDuration();
                // 每个任务的时间 - 循环过的时间
                int day = DateUtils.calculatePeiorDaysOfTwoDate(upo.getCreatedDate(), now) - duration * upo.getCurrentCycleIndex();
                int progress = 1;
                if (day != 0) {
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
                // 查询当天离现在最近的任务
                List<HcPackageDetail> tempDetailFrom;

                day += 1;
                if (completeDetailIds.isEmpty()) {
                    tempDetailFrom = hcPackageDetailRepository.findByPackageIdAndDayOrderByTimeFromAsc(upo.getHcPackage().getId(), day);
                } else {
                    tempDetailFrom = hcPackageDetailRepository.findByPackageIdAndDayOrderByTimeFromAsc(upo.getHcPackage().getId(), day, completeDetailIds);
                }
                allDetailList.addAll(tempDetailFrom);
            }
            Collections.sort(allDetailList, new PackageDetailComparatorAsc());

            List<HcPackageDetail> topDetail = new ArrayList<>();
            List<HcPackageDetail> bottomDetail = new ArrayList<>();
            Calendar c = Calendar.getInstance();
            c.setTime(now);

            for (HcPackageDetail detail : allDetailList) {
                c.set(Calendar.HOUR_OF_DAY, detail.getRecommendTimeFrom().getHours());
                c.set(Calendar.MINUTE, detail.getRecommendTimeFrom().getMinutes());
                System.out.println(c.getTime());
                if ((c.getTime().getTime() - now.getTime()) > 0) {
                    bottomDetail.add(detail);
                } else {
                    topDetail.add(detail);
                }
            }
            Collections.sort(topDetail, new PackageDetailComparatorDesc());
            if (!bottomDetail.isEmpty()) {
                trueDetailList.add(bottomDetail.get(0));
                if (!topDetail.isEmpty()) {

                    trueDetailList.add(topDetail.get(0));
                } else if (bottomDetail.size() > 1){
                    trueDetailList.add(bottomDetail.get(1));
                }
            } else {
                if (!topDetail.isEmpty()) {
                    trueDetailList.add(topDetail.get(0));
                }
                if (topDetail.size() > 1) {
                    trueDetailList.add(topDetail.get(1));
                }
            }

            Collections.sort(trueDetailList, new PackageDetailComparatorAsc());
            // 添加进度
            resultMap.put("progress", progressDtos);

            List<DailyPackageDto> tasks = new ArrayList<>();
            for (HcPackageDetail detail : trueDetailList) {
                DailyPackageDto dto = new DailyPackageDto(
                        detail.getId(),
                        detail.getRecommendTimeFrom(),
                        detail.getTaskName(),
                        detail.getClickAmount()
                );
                tasks.add(dto);
            }
            List<HomePageTips> tips = this.tipsRepository.findAll();

            List<String> allTips = new ArrayList<>();

            if (tasks.size() == 1) {
                allTips.add(tips.get(RandomUtils.nextInt(0, tips.size())).getTips());
            } else if (tasks.isEmpty()) {
                allTips.add(tips.get(RandomUtils.nextInt(0, tips.size())).getTips());
                allTips.add(tips.get(RandomUtils.nextInt(0, tips.size())).getTips());
            }
            resultMap.put("currentDay", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
            resultMap.put("tips", allTips);


            resultMap.put("task", tasks);

            return new ControllerResult<>().setRet_code(0).setRet_values(resultMap).setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values("失败啦").setMessage("失败");
        }

    }

    class PackageDetailComparatorAsc implements Comparator {
        public int compare(Object o1, Object o2) {
            HcPackageDetail hpd1 = (HcPackageDetail) o1;
            HcPackageDetail hpd2 = (HcPackageDetail) o2;
            return hpd1.getRecommendTimeFrom().compareTo(hpd2.getRecommendTimeFrom());
        }
    }
    class PackageDetailComparatorDesc implements Comparator {
        public int compare(Object o1, Object o2) {
            HcPackageDetail hpd1 = (HcPackageDetail) o1;
            HcPackageDetail hpd2 = (HcPackageDetail) o2;
            return hpd2.getRecommendTimeFrom().compareTo(hpd1.getRecommendTimeFrom());
        }
    }

}
