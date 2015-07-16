package com.wonders.xlab.healthcloud.service.homepage;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;

import java.util.Date;
import java.util.List;

/**
 * Created by mars on 15/7/16.
 */
public interface HomePageService {


    ObjectNode retrieveBannerNode(ObjectNode node);

    ObjectNode retrieveTasksAndTipsByAllDetailList(ObjectNode resultNode, List<HcPackageDetail> prePackageDetailList, Date now);
}
