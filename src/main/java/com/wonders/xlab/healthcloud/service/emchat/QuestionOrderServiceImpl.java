package com.wonders.xlab.healthcloud.service.emchat;

import com.wonders.xlab.healthcloud.dto.emchat.RequestTexMsg;
import com.wonders.xlab.healthcloud.dto.emchat.TexMessagesRequestBody;
import com.wonders.xlab.healthcloud.entity.EmMessages;
import com.wonders.xlab.healthcloud.entity.QuestionOrder;
import com.wonders.xlab.healthcloud.repository.QuestionOrderRepository;
import com.wonders.xlab.healthcloud.repository.doctor.DoctorRepository;
import com.wonders.xlab.healthcloud.service.cache.HCCacheProxy;
import net.sf.ehcache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wukai on 15/7/14.
 */
@Service
public class QuestionOrderServiceImpl implements QuestionOrderService {

    @Autowired
    private QuestionOrderRepository orderRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    @Qualifier("userQuestionCache")
    private Cache userQuestionCache;

    @Autowired
    @Qualifier("questionOrderCache")
    private Cache questionOrderCache;

    private HCCacheProxy<String, String> questionCache = new HCCacheProxy(userQuestionCache);
    private HCCacheProxy<String, String> orderCache = new HCCacheProxy<>(questionOrderCache);

    @Override
    public void sendQuestionToDoctors(String doctorId) {
        //获取问题列表
        List<QuestionOrder> orderList = orderRepository.findAllNewQuestionsOrderByPushCountAndId(QuestionOrder.QuestionStatus.newQuestion);

        //遍历，判断是否已推送
        for(QuestionOrder order : orderList){
            String cachedKey = order.getUser().getId().toString().concat("_RESPONDENT");
            //从缓存中判断该信息是否已有医生回复
            if(StringUtils.isNotEmpty(orderCache.getFromCache(order.getUser().getId().toString()))){
                if(StringUtils.isNotEmpty(questionCache.getFromCache(cachedKey))){
                    continue;
                } else {
                    TexMessagesRequestBody requestBody = new TexMessagesRequestBody();
                    EmMessages message = order.getMessages();
                    requestBody.setFrom(message.getFromUser());
                    requestBody.setMsg(new RequestTexMsg("txt", message.getMsg()));
                    requestBody.setTarget(new ArrayList<String>(){{
                        //TODO 获取医生群组，发给医生
                        add("82830104253694376");
                    }});
                    requestBody.setTargetType("");
                    //TODO 发送消息
                    if(StringUtils.isNotEmpty(doctorId)){
                        //发送单条任务给医生

                    } else {
                        //群发所有医生

                    }

                }
            } else {
              continue;
            }

        }

    }
}
