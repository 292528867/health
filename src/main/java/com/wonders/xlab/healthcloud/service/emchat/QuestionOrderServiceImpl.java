package com.wonders.xlab.healthcloud.service.emchat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.xlab.healthcloud.dto.emchat.RequestTexMsg;
import com.wonders.xlab.healthcloud.dto.emchat.TexMessagesRequestBody;
import com.wonders.xlab.healthcloud.entity.EmMessages;
import com.wonders.xlab.healthcloud.entity.QuestionOrder;
import com.wonders.xlab.healthcloud.repository.QuestionOrderRepository;
import com.wonders.xlab.healthcloud.repository.doctor.DoctorRepository;
import com.wonders.xlab.healthcloud.service.cache.HCCacheProxy;
import com.wonders.xlab.healthcloud.utils.EMUtils;
import net.sf.ehcache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.Object;
import java.util.*;

/**
 * Created by wukai on 15/7/14.
 */
@Service
public class QuestionOrderServiceImpl implements QuestionOrderService {

    @Autowired
    private QuestionOrderRepository orderRepository;

    @Autowired
    @Qualifier("userQuestionCache")
    private Cache userQuestionCache;

    @Autowired
    @Qualifier("questionOrderCache")
    private Cache questionOrderCache;

    @Autowired
    private EMUtils emUtils;

    @Autowired
    protected ObjectMapper objectMapper;

    private HCCacheProxy<String, String> questionCache;
    private HCCacheProxy<String, String> orderCache;

    @PostConstruct
    private void init(){
        questionCache = new HCCacheProxy<>(userQuestionCache);
        orderCache = new HCCacheProxy<>(questionOrderCache);
    }

    /**
     * 给医生推送问题
     * 如果不指定医生账号，则给所有的医生推送消息
     * 问题列表根据提问时间和推送次数升序排列，推送列表中第一条消息给医生。如果该问题已被处理，则跳过，推送下一条
     * @param doctorTel
     * @throws Exception
     */
    @Override
    public void sendQuestionToDoctors(String doctorTel) throws Exception{
        //获取问题列表
        List<QuestionOrder> orderList = orderRepository.findAllNewQuestionsOrderByPushCountAndId(QuestionOrder.QuestionStatus.newQuestion);

        //遍历，判断是否已推送
        for(QuestionOrder order : orderList){
            String uidStr = order.getUser().getId().toString();
            String respondentKey = uidStr.concat("_RESPONDENT");
            //从缓存中判断该信息是否已有医生回复
            if(StringUtils.isNotEmpty(orderCache.getFromCache(order.getUser().getId().toString()))){
                String timeKey = uidStr.concat("_ASK_TIME");
                String askTimeStr = questionCache.getFromCache(timeKey);
                if(StringUtils.isNotEmpty(askTimeStr)){
                    long askTime = Long.parseLong(askTimeStr);
                    //如果距离提问时间超过15分钟，不再发送给医生
                    if(System.currentTimeMillis() - askTime > 15 * 60 * 1000){
                        continue;
                    }
                }
                if(StringUtils.isNotEmpty(questionCache.getFromCache(respondentKey))){
                    continue;
                } else {
                    TexMessagesRequestBody requestBody = new TexMessagesRequestBody();
                    EmMessages message = order.getMessages();
                    requestBody.setFrom(message.getFromUser());
                    requestBody.setMsg(new RequestTexMsg(message.getMessageType(), message.getMsg()));
                    Map<String, Object> extMap = Collections.singletonMap("messageId", (Object) message.getId().toString());
                    requestBody.setExt(extMap);

                    if(StringUtils.isNotEmpty(doctorTel)){
                        List<String> targetList = new ArrayList<>();
                        //doctor的环信account前缀为doctor
                        targetList.add("doctor"+doctorTel);
                        //发送单条任务给医生
                        requestBody.setTarget(targetList);
                        requestBody.setTargetType(message.getTargetType());
                    } else {
                        //群发所有医生
                        requestBody.setTarget(new ArrayList<String>(){{
                            //TODO 获取医生群组，发给医生
                            add("82830104253694376");
                        }});
                        requestBody.setTargetType(message.getTargetType());
                    }

                    String messagesJson = objectMapper.writeValueAsString(requestBody);
                    //发送信息
                    ResponseEntity result = emUtils.requestEMChat(messagesJson, "POST", "messages", String.class);
                    if(HttpStatus.OK == result.getStatusCode()){
                        order.setPushCount(order.getPushCount() + 1);
                        orderRepository.save(order);
                        //每次只群推一条消息，发送成功以后就退出循环
                        break;
                    }
                }
            } else {
              continue;
            }
        }

    }
}
