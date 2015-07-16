package com.wonders.xlab.healthcloud.service.homepage.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wonders.xlab.healthcloud.entity.banner.Banner;
import com.wonders.xlab.healthcloud.entity.banner.BannerTag;
import com.wonders.xlab.healthcloud.entity.banner.BannerType;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;
import com.wonders.xlab.healthcloud.repository.banner.BannerRepository;
import com.wonders.xlab.healthcloud.service.homepage.HomePageService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by mars on 15/7/16.
 */
@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public ObjectNode retrieveBannerNode(ObjectNode node) {
        List<Banner> topBanners = new ArrayList<>();
        List<Banner> bottomBanners = new ArrayList<>();

        // 发现的标语
        List<Banner> discoveryBanners = bannerRepository.findByBannerTagAndEnabled(BannerTag.Discovery, true);
        // 发现之外的标语
        List<Banner> otherBanners = bannerRepository.findByBannerTagNotAndEnabled(BannerTag.Discovery, true);
        // 完成计划的Id

        // 添加发现之外的banner
        for (Banner basicBanner : otherBanners) {
            if (basicBanner.getBannerType() == BannerType.Top.ordinal()) {
                topBanners.add(basicBanner);
            } else {
                bottomBanners.add(basicBanner);
            }
        }
        // 随机一个发现的banner
        if (!discoveryBanners.isEmpty()) {
            Banner disBanner = discoveryBanners.get(RandomUtils.nextInt(0, discoveryBanners.size()));
            if (disBanner.getBannerType() == BannerType.Top.ordinal()) {
                topBanners.add(disBanner);
            } else {
                bottomBanners.add(disBanner);
            }
        }
        Collections.sort(topBanners, new Comparator<Banner>() {
            @Override
            public int compare(Banner o1, Banner o2) {
                return o1.getPosition() - o2.getPosition();
            }
        });
        Collections.sort(bottomBanners, new Comparator<Banner>() {
            @Override
            public int compare(Banner o1, Banner o2) {
                return o1.getPosition() - o2.getPosition();
            }
        });
        node.putPOJO("topBanners", topBanners);
        node.putPOJO("bottomBanners", bottomBanners);

        return node;
    }

    @Override
    public List<HcPackageDetail> retrievePackageDetailList(List<HcPackageDetail> prePackageDetailList, Date now) {

        List<HcPackageDetail> finialDetailList = new ArrayList<>();

        // 升序
        Collections.sort(prePackageDetailList, new Comparator<HcPackageDetail>() {
            @Override
            public int compare(HcPackageDetail o1, HcPackageDetail o2) {
                return o1.getRecommendTimeFrom().compareTo(o2.getRecommendTimeFrom());
            }
        });

        List<HcPackageDetail> beforeDetail = new ArrayList<>();
        List<HcPackageDetail> afterDetail = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        Calendar cStart = Calendar.getInstance();
        c.setTime(now);

        for (HcPackageDetail detail : prePackageDetailList) {
            cStart.setTime(detail.getRecommendTimeFrom());

            c.set(Calendar.HOUR_OF_DAY, cStart.get(Calendar.HOUR_OF_DAY));
            c.set(Calendar.MINUTE, cStart.get(Calendar.MINUTE));
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
                return o2.getRecommendTimeFrom().compareTo(o1.getRecommendTimeFrom());
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
                return o1.getRecommendTimeFrom().compareTo(o2.getRecommendTimeFrom());
            }
        });

        return finialDetailList;
    }
}
