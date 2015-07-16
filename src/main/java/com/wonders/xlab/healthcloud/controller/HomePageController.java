package com.wonders.xlab.healthcloud.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wonders.xlab.healthcloud.dto.hcpackage.DailyPackageDto;
import com.wonders.xlab.healthcloud.dto.hcpackage.ProgressDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.HomePageTips;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import com.wonders.xlab.healthcloud.repository.TipsRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageDetailRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageCompleteRepository;
import com.wonders.xlab.healthcloud.service.homepage.HomePageService;
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
    private TipsRepository tipsRepository;

    @Autowired
    private HomePageService homePageService;

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
            resultNode.putPOJO("banner", homePageService.retrieveBannerNode(bannerNode));

            // 完成计划的Id
            List<Long> completeDetailIds = new ArrayList<>();

            // 查找没有完成的健康包
            List<UserPackageOrder> userPackageOrders = this.userPackageCompleteRepository.findByUserIdAndPackageComplete(userId, false);

            List<HcPackageDetail> allDetailList = new ArrayList<>();
            // 查看完成度
            List<ProgressDto> progressDtos = new ArrayList<>();
            Date now = new Date();
            // 查找用户完成的任务
            // TODO: 时间还需优化，按照天算
            for (UserPackageOrder upo : userPackageOrders) {
                completeDetailIds.clear();
                String[] strdetails = StringUtils.split(upo.getHcPackageDetailIds(), ',');
                if (strdetails != null) {
                    for (int i = 0; i < strdetails.length; i++) {
                        completeDetailIds.add(NumberUtils.toLong(strdetails[i]));
                    }
                }
                // 持续时间
                int duration = upo.getHcPackage().getDuration();
                // 每个任务的时间 - 循环过的时间

                int day = calculatePeiorDaysOfTwoDateWith24(upo.getCreatedDate(), now) - duration * upo.getCurrentCycleIndex();
                // 第一天默认进度 1
                int progress = 1;
                if (day != 0) {
                    progress = day * 100 / duration;
                }
                String title = "";
                if (upo.getHcPackage().getHealthCategory() != null
                        && upo.getHcPackage().getHealthCategory().getClassification() != null
                        && upo.getHcPackage().getHealthCategory().getClassification().getTitle() != null) {
                    title = upo.getHcPackage().getHealthCategory().getClassification().getTitle();
                }
                progressDtos.add(
                        new ProgressDto(
                                title,
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
            // 获取最终list
            List<HcPackageDetail> finialDetailList = homePageService.retrievePackageDetailList(allDetailList, now);

            // 添加进度
            resultNode.putPOJO("progress", progressDtos);

            List<DailyPackageDto> tasks = new ArrayList<>();
            for (HcPackageDetail detail : finialDetailList) {
                DailyPackageDto dto = new DailyPackageDto();
                dto.setPackageDetailId(detail.getId());
                dto.setRecommendTimeFrom(detail.getRecommendTimeFrom());
                dto.setTaskName(detail.getTaskName());
                dto.setClickAmount(detail.getHcPackage().getClickAmount());
                dto.setCoefficient(detail.getHcPackage().getCoefficient());
                dto.setCreatedDate(detail.getHcPackage().getCreatedDate());
                tasks.add(dto);
            }
            List<HomePageTips> tips = tipsRepository.findAll();
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

    // 按照24小时计算天数
    // TODO：继续优化方法
    public int calculatePeiorDaysOfTwoDateWith24 (Date from, Date to) {

        Calendar cfrom = Calendar.getInstance();
        Calendar cto = Calendar.getInstance();
        cfrom.setTime(from);
        cto.setTime(to);
        cfrom.set(Calendar.HOUR_OF_DAY, 0);
        cfrom.set(Calendar.MINUTE, 0);
        cfrom.set(Calendar.SECOND, 0);
        cfrom.set(Calendar.MILLISECOND, 0);

        cto.set(Calendar.HOUR_OF_DAY, 0);
        cto.set(Calendar.MINUTE, 0);
        cto.set(Calendar.SECOND, 0);
        cto.set(Calendar.MILLISECOND, 0);

        return DateUtils.calculatePeiorDaysOfTwoDate(cfrom.getTime(), cto.getTime());
    }
}
