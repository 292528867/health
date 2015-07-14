package com.wonders.xlab.healthcloud.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    public Object listHomePage(@PathVariable long userId) {
        try {
            JsonNodeFactory factory = JsonNodeFactory.instance;
            ObjectNode resultNode = factory.objectNode();
            ObjectNode bannerNode = factory.objectNode();

            // 查询所有的标语
            List<Banner> topBanners = this.bannnerRepository.findByBannerTypeAndEnabled(BannerType.Top, true);
            List<Banner> bottomBanners = this.bannnerRepository.findByBannerTypeAndEnabled(BannerType.Bottom, true);

            bannerNode.putPOJO("topBanners", topBanners);
            bannerNode.putPOJO("bottomBanners", bottomBanners);

            resultNode.putPOJO("banner", bannerNode);
            // 查找没有完成的健康包
            List<UserPackageOrder> userPackageOrders = this.userPackageCompleteRepository.findByUserIdAndPackageComplete(userId, false);

            List<HcPackageDetail> allDetailList = new ArrayList<>();
            List<HcPackageDetail> finialDetailList = new ArrayList<>();
            // 完成计划的Id
            List<Long> completeDetailIds = new ArrayList<>();
            // 查看完成度
            List<ProgressDto> progressDtos = new ArrayList<>();
            Date now = new Date();
            // 查找用户完成的任务
            // TODO: 时间还需优化，按照天算
            Calendar cfrom = Calendar.getInstance();
            Calendar cto = Calendar.getInstance();
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

                cfrom.setTime(upo.getCreatedDate());
                cto.setTime(now);
                cfrom.set(Calendar.HOUR_OF_DAY, 0);
                cfrom.set(Calendar.MINUTE, 0);
                cfrom.set(Calendar.SECOND, 0);
                cfrom.set(Calendar.MILLISECOND, 0);

                cto.set(Calendar.HOUR_OF_DAY, 0);
                cto.set(Calendar.MINUTE, 0);
                cto.set(Calendar.SECOND, 0);
                cto.set(Calendar.MILLISECOND, 0);

                int day = DateUtils.calculatePeiorDaysOfTwoDate(cfrom.getTime(), cto.getTime()) - duration * upo.getCurrentCycleIndex();
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
                System.out.println(upo.getHcPackage().getId());
                if (completeDetailIds.isEmpty()) {
                    tempDetailFrom = hcPackageDetailRepository.findByPackageIdAndDayOrderByTimeFromAsc(upo.getHcPackage().getId(), day);
                } else {
                    tempDetailFrom = hcPackageDetailRepository.findByPackageIdAndDayOrderByTimeFromAsc(upo.getHcPackage().getId(), day, completeDetailIds);
                }
                allDetailList.addAll(tempDetailFrom);
            }
            // 升序
            Collections.sort(allDetailList, new Comparator<HcPackageDetail>() {
                @Override
                public int compare(HcPackageDetail o1, HcPackageDetail o2) {
                    HcPackageDetail hpd1 = o1;
                    HcPackageDetail hpd2 = o2;
                    return hpd1.getRecommendTimeFrom().compareTo(hpd2.getRecommendTimeFrom());
                }
            });

            List<HcPackageDetail> beforeDetail = new ArrayList<>();
            List<HcPackageDetail> afterDetail = new ArrayList<>();
            Calendar c = Calendar.getInstance();
            Calendar cFrom = Calendar.getInstance();
            c.setTime(now);

            for (HcPackageDetail detail : allDetailList) {
                cFrom.setTime(detail.getRecommendTimeFrom());

                c.set(Calendar.HOUR_OF_DAY, cFrom.get(Calendar.HOUR_OF_DAY));
                c.set(Calendar.MINUTE, cFrom.get(Calendar.MINUTE));
                System.out.println(c.getTime());
                if ((c.getTime().getTime() - now.getTime()) > 0) {
                    afterDetail.add(detail);
                } else {
                    beforeDetail.add(detail);
                }
            }
            // 当前时间钱的list倒叙，选取最接近的时间
            Collections.sort(beforeDetail, new Comparator<HcPackageDetail>() {
                @Override
                public int compare(HcPackageDetail o1, HcPackageDetail o2) {
                    HcPackageDetail hpd1 = o1;
                    HcPackageDetail hpd2 = o2;
                    return hpd2.getRecommendTimeFrom().compareTo(hpd1.getRecommendTimeFrom());
                }
            });
            // 选取任务
            if (!afterDetail.isEmpty()) {
                finialDetailList.add(afterDetail.get(0));
                if (!beforeDetail.isEmpty()) {
                    finialDetailList.add(beforeDetail.get(0));
                } else if (afterDetail.size() > 1){
                    finialDetailList.add(afterDetail.get(1));
                }
            } else {
                if (!beforeDetail.isEmpty()) {
                    finialDetailList.add(beforeDetail.get(0));
                }
                if (beforeDetail.size() > 1) {
                    finialDetailList.add(beforeDetail.get(1));
                }
            }

            // 最终list升序
            Collections.sort(finialDetailList, new Comparator<HcPackageDetail>() {
                @Override
                public int compare(HcPackageDetail o1, HcPackageDetail o2) {
                    HcPackageDetail hpd1 = o1;
                    HcPackageDetail hpd2 = o2;
                    return hpd1.getRecommendTimeFrom().compareTo(hpd2.getRecommendTimeFrom());
                }
            });
            // 添加进度
            resultNode.putPOJO("progress", progressDtos);

            List<DailyPackageDto> tasks = new ArrayList<>();
            for (HcPackageDetail detail : finialDetailList) {
                DailyPackageDto dto = new DailyPackageDto(
                        detail.getId(),
                        detail.getRecommendTimeFrom(),
                        detail.getTaskName(),
                        detail.getHcPackage().getClickAmount()
                );
                tasks.add(dto);
            }
            List<HomePageTips> tips = this.tipsRepository.findAll();

            List<String> allTips = new ArrayList<>();

            if (tasks.isEmpty()) {
                HomePageTips tipsOne = tips.get(RandomUtils.nextInt(0, tips.size()));
                tips.remove(tipsOne);
                allTips.add(tipsOne.getTips());
                allTips.add(tips.get(RandomUtils.nextInt(0, tips.size())).getTips());
            } else if (tasks.size() == 1) {
                allTips.add(tips.get(RandomUtils.nextInt(0, tips.size())).getTips());
            }
            resultNode.put("currentDay", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
            resultNode.putPOJO("tips", allTips);
            resultNode.putPOJO("task", tasks);

            return new ControllerResult<>().setRet_code(0).setRet_values(resultNode).setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values("失败啦").setMessage("失败");
        }

    }
}
