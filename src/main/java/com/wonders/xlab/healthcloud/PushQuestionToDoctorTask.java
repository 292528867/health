package com.wonders.xlab.healthcloud;

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




    /**
     * 每隔30秒推新问题给医生
     */
    @Scheduled(cron = "0 0/60 * * * ? ")
    private void push(){


    }

}
