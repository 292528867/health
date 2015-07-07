package com.wonders.xlab.healthcloud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.emchat.*;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.EmMessages;
import com.wonders.xlab.healthcloud.repository.EmMessagesRepository;
import com.wonders.xlab.healthcloud.service.WordAnalyzerService;
import com.wonders.xlab.healthcloud.utils.EMUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * @param body
     * @return
     */
    @RequestMapping(value = "replyMessage/{id}", method = RequestMethod.POST)
    public ControllerResult replyMessage(@PathVariable("id") long id, @RequestBody TexMessagesRequestBody body) throws IOException {
        String messagesJson = objectMapper.writeValueAsString(body);
        //扩展属性
        Map<String, String> extendAttr = wordAnalyzerService.analyzeText(body.getMsg().getMsg());

       // body.setExt(objectMapper.writeValueAsString(extendAttr));
        //发送信息
        ResponseEntity<String> responseEntity = (ResponseEntity<String>) emUtils.requestEMChart(HttpMethod.POST, messagesJson, "messages", String.class);
        //保存医生回复消息
        EmMessages emMessages = new EmMessages(
                body.getFrom(),
                body.getTarget().get(0),
                body.getMsg().getMsg(),
                body.getMsg().getType(),
                body.getTargetType(),
             //   body.getExt(),
                true,
                false
        );
        emMessagesRepository.save(emMessages);
        //修改app发送信息状态为已回复
        EmMessages oldEm = emMessagesRepository.findOne(id);
        oldEm.setIsReplied(true);
        emMessagesRepository.save(oldEm);

        return new ControllerResult().setRet_code(0).setRet_values(responseEntity.getBody()).setMessage("消息发送成功");

    }

    /**
     * (接受app)文本消息发送
     */

    @RequestMapping(value = "sendTxtMessage", method = RequestMethod.POST)
        public ControllerResult sendTxtMessage(@RequestBody TexMessagesRequestBody body) throws IOException {
        String messagesJson = objectMapper.writeValueAsString(body);
        //发送信息
        emUtils.requestEMChart(HttpMethod.POST, messagesJson, "messages", String.class);
        //保存消息
        EmMessages emMessages = new EmMessages(
                body.getFrom(),
                body.getTarget().get(0),
                body.getMsg().getMsg(),
                body.getMsg().getType(),
                body.getTargetType(),
                false,
                true
        );

        emMessagesRepository.save(emMessages);

        return new ControllerResult().setRet_code(0).setRet_values("").setMessage("文本消息发送成功");

    }

    /**
     * 发送图片,语音信息
     *
     * @param body
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "sendFileMessage", method = RequestMethod.POST)
    public ControllerResult sendFileMessage(@RequestBody FileMessagesRequestBody body) throws JsonProcessingException {

        String messagesJson = objectMapper.writeValueAsString(body);
        //发送信息
        emUtils.requestEMChart(HttpMethod.POST, messagesJson, "messages", String.class);
        //保存消息
        EmMessages emMessages = new EmMessages(
                body.getFrom(),
                body.getTarget().get(0),
                body.getMsg().getFilename(),
                body.getMsg().getType(),
                body.getTargetType(),
                false,
                true
        );
        emMessagesRepository.save(emMessages);

        return new ControllerResult().setRet_code(0).setRet_values("").setMessage("图片信息发送成功");
    }

    /**
     * 上传环信图片消息
     */
    @RequestMapping(value = "chatfiles", method = RequestMethod.POST)
    public ChatFilesResponseBody sendFiles(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>() {{
            add(MediaType.APPLICATION_JSON);
        }};

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(acceptableMediaTypes);
        headers.add("restrict-access", "true");
        headers.add("Authorization", "Bearer YWMtEJuECCJLEeWN-d-uaORhJQAAAU-OGpHmVNOp0Va6o2OEAUzNiA1O9UB_oFw");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HashMap<String, Object> map = new HashMap<>();
        map.put("file", multipartFile.getBytes());

        MultiValueMap multiValueMap = new LinkedMultiValueMap();
        multiValueMap.setAll(map);

        ResponseEntity<ChatFilesResponseBody> responseEntity = (ResponseEntity<ChatFilesResponseBody>) emUtils.requestEMChart(headers, HttpMethod.POST, multiValueMap, "chatfiles", ChatFilesResponseBody.class);

        return responseEntity.getBody();

    }

    /**
     * 注册IM用户(授权注册)
     *
     * @param username
     * @param password
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "users", method = RequestMethod.POST)
    public int registerUsers(String username, String password) throws JsonProcessingException {

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("username", username);
        bodyMap.put("password", password);

        String body = objectMapper.writeValueAsString(bodyMap);

        try {
            emUtils.requestEMChart(HttpMethod.POST, body, "users", String.class);
        } catch (HttpClientErrorException e) {
            return -1;
        }
        return 0;

    }


    /**
     * 修改昵称
     * @param username
     * @param nickname
     * @return
     */
    @RequestMapping(value = "modifNickname", method = RequestMethod.POST)
    public int modifNickname(String username, String nickname) throws JsonProcessingException {

        Map<String, String> bodyMap = new HashMap<>();

        bodyMap.put("nickname", nickname);

        String body = objectMapper.writeValueAsString(bodyMap);

        try {

            ResponseEntity<ChatGroupsResponseBody> responseEntity = (ResponseEntity<ChatGroupsResponseBody>) emUtils.requestEMChart(HttpMethod.PUT, body, "users/" + username, ChatGroupsResponseBody.class);

            responseEntity.getBody();

        } catch (HttpClientErrorException e) {
            return -1;
        }
        return 0;

    }

    /**
     * 新增群组
     */
    @RequestMapping(value = "newChatgroups", method = RequestMethod.POST)
    public String newChatgroups(@RequestBody TexMessagesRequestBody body) throws JsonProcessingException {

        ChatGroupsRequestBody groupsBody = new ChatGroupsRequestBody(body.getFrom(), "万达健康云", true, 300, false, body.getFrom());

        String requestBody = objectMapper.writeValueAsString(groupsBody);

        ResponseEntity<ChatGroupsResponseBody> responseEntity;

        String newRequestBody = StringUtils.replace(requestBody, "_public", "public");

        try {

            responseEntity = (ResponseEntity<ChatGroupsResponseBody>) emUtils.requestEMChart(HttpMethod.POST, newRequestBody, "chatgroups", ChatGroupsResponseBody.class);

        } catch (HttpClientErrorException e) {
            return "-1";
        }

        return responseEntity.getBody().getData().get("groupid");
    }

    @RequestMapping(value = "getTop5Messages" ,method = RequestMethod.POST)
    public List<EmMessages> getTop5Messages(String fromUser,String toUser){

        return emMessagesRepository.findTop5ByFromUserOrToUserOrderByCreatedDateAsc(fromUser, toUser);

    }


}
