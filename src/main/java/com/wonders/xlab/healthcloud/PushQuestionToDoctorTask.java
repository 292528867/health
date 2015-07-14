package com.wonders.xlab.healthcloud;

import com.wonders.xlab.healthcloud.service.emchat.QuestionOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by yk on 15/7/14.
 */
@Component
@Configuration
@EnableScheduling
public class PushQuestionToDoctorTask {


    @Autowired
    private QuestionOrderService questionOrderService;

    /**
     * 每隔60秒推新问题给医生
     */
    @Scheduled(cron = "0/20 * * * * ? ")
    private void push() {
        try {
            questionOrderService.sendQuestionToDoctors("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
