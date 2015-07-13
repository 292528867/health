package com.wonders.xlab.healthcloud.service.hcpackage;

import com.wonders.xlab.healthcloud.utils.DateUtils;

import java.util.Date;

/**
 * Created by Jeffrey on 15/7/9.
 */
public class HcpackageService {

    /**
     * 点击数计算(A * (1 + x) n次方 ＋ B(真实点击数))
     * @param clickCount 真实点击数
     * @param createdDate 健康包创建时间
     * @return
     */
    public static int calculateClickCount(long clickCount, Date createdDate) {
        int dateCount = DateUtils.calculatePeiorDaysOfTwoDate(new Date(), null == createdDate ? new Date() : createdDate);
        return (int)(clickCount + 40 * Math.pow((1 + 0.02), dateCount));
    }
}
