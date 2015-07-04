package com.wonders.xlab.healthcloud.controller;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.EmMessages;
import com.wonders.xlab.healthcloud.repository.EmMessagesRePository;
import com.wonders.xlab.healthcloud.utils.EMUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixuanwu on 15/7/4.
 */
@RestController
@RequestMapping(value = "em")
public class EmController extends AbstractBaseController<EmMessages, Long> {

    @Autowired
    private EMUtils emUtils;

    private EmMessagesRePository emMessagesRePository;

    @Override
    protected MyRepository<EmMessages, Long> getRepository() {
        return emMessagesRePository;
    }

    /**
     * 发送文本消息
     *
     * @param body
     * @return
     */
    @RequestMapping(value = "sendmessage", method = RequestMethod.POST)
    public ControllerResult sendmessage(@RequestBody String body) {

        List<MediaType> mediaTypes = new ArrayList<MediaType>() {{
            add(MediaType.APPLICATION_JSON);
        }};
        HttpHeaders header = new HttpHeaders();
        //添加请求头
        header.setAccept(mediaTypes);
        //配接获取环信token请求体
        body = "{\n" +
                "    \"target_type\" : \"users\",\n" +
                "    \"target\" : [\"lixuanwu\", \"qiuqiu\", \"chaochao\"], \n" +
                "    \"msg\" : {\n" +
                "        \"type\" : \"txt\",\n" +
                "        \"msg\" : \"hello from rest\" \n" +
                "        },\n" +
                "    \"from\" : \"lixuanwu\"\n" +
                "}";
        ResponseEntity<String> responseEntity = (ResponseEntity<String>) emUtils.requstEMChart(header, HttpMethod.POST, body, "messages", String.class);

        return new ControllerResult().setRet_code(0).setRet_values(responseEntity).setMessage("");

    }



}
