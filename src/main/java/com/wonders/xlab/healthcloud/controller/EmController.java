package com.wonders.xlab.healthcloud.controller;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.EmMessages;
import com.wonders.xlab.healthcloud.repository.EmMessagesRepository;
import com.wonders.xlab.healthcloud.utils.EMUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lixuanwu on 15/7/4.
 */
@RestController
@RequestMapping(value = "em")
public class EmController extends AbstractBaseController<EmMessages, Long> {

    @Autowired
    private EMUtils emUtils;

    @Autowired
    private EmMessagesRepository emMessagesRepository;

    @Override
    protected MyRepository<EmMessages, Long> getRepository() {
        return emMessagesRepository;
    }

    /**
     * 发送文本消息
     body = "{\n" +
     "    \"target_type\" : \"users\",\n" +
     "    \"target\" : [\"lixuanwu\", \"qiuqiu\", \"chaochao\"], \n" +
     "    \"msg\" : {\n" +
     "        \"type\" : \"txt\",\n" +
     "        \"msg\" : \"hello from rest\" \n" +
     "        },\n" +
     "    \"from\" : \"lixuanwu\"\n" +
     "}";
     * @param body
     * @return
     */
    @RequestMapping(value = "sendMessage", method = RequestMethod.POST)
    public ControllerResult sendMessage(@RequestBody String body) {

        ResponseEntity<String> responseEntity = (ResponseEntity<String>) emUtils.requestEMChart(HttpMethod.POST, body, "messages", String.class);


        return new ControllerResult().setRet_code(0).setRet_values(responseEntity.getBody()).setMessage("消息发送成功");

    }



}
