package com.wonders.xlab.healthcloud.service.homepage;

import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mars on 15/7/16.
 */
public interface HomePageService {


    Map<String, Object> retrieveBannerNode();

    Map<String, Object> retrieveTasksAndTipsByAllDetailList(List<HcPackageDetail> prePackageDetailList, Date now);
}
