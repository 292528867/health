package com.wonders.xlab.healthcloud.service.emchat;

import com.wonders.xlab.healthcloud.entity.QuestionOrder;

/**
 * Created by wukai on 15/7/14.
 */
public interface QuestionOrderService {
    /**
     * 给医生推送问题
     * 如果不指定医生账号，则给所有的医生推送消息
     * 问题列表根据提问时间和推送次数升序排列，推送列表中第一条消息给医生。如果该问题已被处理，则跳过，推送下一条
     * @param doctorTel
     * @throws Exception
     */
    void sendQuestionToDoctors(String doctorTel) throws Exception;

    /**
     * 查找一条可以推送的问题
     * @return
     */
    QuestionOrder findOneNewQuestion();
}
