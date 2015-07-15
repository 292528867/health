package com.wonders.xlab.healthcloud.service.hcpackage;

/**
 * Created by Jeffrey on 15/7/11.
 */
public interface UserPackageOrderService {

    /**
     * 每日执行用户健康包完成状态
     *
     * @param aliquotNumber 以除3取余分线程执行任务调度
     */
    void scheduleCalculateIsPackageFinished(int aliquotNumber);


    /**
     * 用户加入健康包计划
     * @param userId
     * @param packageId
     * @return 500 用户已选择两个包，400 用户健康计划包已存在，200 加入成功
     */
    int joinHealthPlan(Long userId, Long packageId);
}
