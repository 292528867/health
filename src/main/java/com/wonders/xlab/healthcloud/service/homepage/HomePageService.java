package com.wonders.xlab.healthcloud.service.homepage;

import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mars on 15/7/16.
 */
public interface HomePageService {

    /**
     * 获取banner
     * @return
     */
    Map<String, Object> retrieveBannerNode();

    /**
     * 获取任务以及提示
     * @param prePackageDetailList
     * @param now
     * @return
     */
    Map<String, Object> retrieveTasksAndTipsByAllDetailList(List<HcPackageDetail> prePackageDetailList, Date now);
}
