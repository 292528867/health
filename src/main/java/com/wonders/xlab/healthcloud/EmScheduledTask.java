package com.wonders.xlab.healthcloud;

import com.wonders.xlab.healthcloud.entity.EmMessages;
import com.wonders.xlab.healthcloud.repository.EmMessagesRepository;
import com.wonders.xlab.healthcloud.utils.EMUtils;
import com.wonders.xlab.healthcloud.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yk on 15/7/8.
 */
@Component
@Configuration
@EnableScheduling
public class EmScheduledTask {

    @Autowired
    private EmMessagesRepository emMessagesRepository;

    //每隔半个小时扫一次
 /*   @Scheduled(cron = "0 0/30 * * * ? ")
    public void isOvertime() {
        //查询没有被回复的信息
        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("doctorFlag_equal",1);
        filterMap.put("isReplied", 0);
        List<EmMessages> list = emMessagesRepository.findAll(filterMap);

        int overtime = EMUtils.getOvertime(); //回复超时时间
        Calendar c = Calendar.getInstance();
         for (EmMessages mm : list) {
             Calendar c1 = Calendar.getInstance();
             c1.setTime(mm.getCreatedDate());
             if (c.getTimeInMillis() - c1.getTimeInMillis() > overtime) {
                 //TODO 超时处理 发短信和推送 健康豆
                 SmsUtils.sendEmReplyInfo(mm.getFromUser(), "");
             }

         }
    }
*/

}
