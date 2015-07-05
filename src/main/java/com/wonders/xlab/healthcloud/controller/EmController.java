package com.wonders.xlab.healthcloud.controller;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.emchat.MessagesBody;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.EmMessages;
import com.wonders.xlab.healthcloud.repository.EmMessagesRepository;
import com.wonders.xlab.healthcloud.service.WordAnalyzerService;
import com.wonders.xlab.healthcloud.utils.EMUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private WordAnalyzerService wordAnalyzerService;

    @Override
    protected MyRepository<EmMessages, Long> getRepository() {
        return emMessagesRepository;
    }

    /**
     * 医生回复消息
     *
     * @param body
     * @return
     */
    @RequestMapping(value = "replyMessage", method = RequestMethod.POST)
    public ControllerResult replyMessage(@RequestBody MessagesBody body) throws IOException {

        String messagesJson = objectMapper.writeValueAsString(body);
        //扩展属性
        Map<String, String> extendAttr = wordAnalyzerService.analyzeText(body.getMsg().getMsg());

        body.setExt(objectMapper.writeValueAsString(extendAttr));
        //发送信息
        ResponseEntity<String> responseEntity = (ResponseEntity<String>) emUtils.requestEMChart(HttpMethod.POST, messagesJson, "messages", String.class);
        //保存消息
        EmMessages emMessages = new EmMessages(
                body.getFrom(),
                body.getTarget(),
                body.getMsg().getMsg(),
                body.getMsg().getType(),
                body.getTargetType(),
                body.getExt(),
                false,
                true
        );
        emMessagesRepository.save(emMessages);

        return new ControllerResult().setRet_code(0).setRet_values(responseEntity.getBody()).setMessage("消息发送成功");

    }

    /**
     * 用户提问消息
     */

    @RequestMapping(value = "sendMessage", method = RequestMethod.POST)
    public ControllerResult sendMessage(@RequestBody MessagesBody body) throws IOException {

        String messagesJson = objectMapper.writeValueAsString(body);
        //发送信息
        emUtils.requestEMChart(HttpMethod.POST, messagesJson, "messages", String.class);
        //保存消息
        EmMessages emMessages = new EmMessages(
                body.getFrom(),
                body.getTarget(),
                body.getMsg().getMsg(),
                body.getMsg().getType(),
                body.getTargetType(),
                false,
                true
        );

        emMessagesRepository.save(emMessages);

        return new ControllerResult().setRet_code(0).setRet_values("").setMessage("消息发送成功");

    }

    /**
     * 发送图片消息
     */
    @RequestMapping (value = "chatfiles" ,method = RequestMethod.POST)
    public ControllerResult sendFiles(String name){


        HttpHeaders headers = new HttpHeaders();

        headers.add("restrict-access" , "true");
        headers.add("Authorization", "Bearer YWMtEJuECCJLEeWN-d-uaORhJQAAAU-OGpHmVNOp0Va6o2OEAUzNiA1O9UB_oFw");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        File file = new File("/Users/lixuanwu/Downloads/psb.jpeg");

        HashMap <String ,Object> map = new HashMap<>();
        map.put("file",file);

        MultiValueMap multiValueMap = new LinkedMultiValueMap();

        multiValueMap.setAll(map);

        emUtils.requestEMChart(headers, HttpMethod.POST, multiValueMap,"chatfiles", String.class);

        return new ControllerResult().setRet_code(0).setRet_values("").setMessage("消息发送成功");

    }


}
