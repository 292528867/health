package com.wonders.xlab.healthcloud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.EmDoctorNumber;
import com.wonders.xlab.healthcloud.dto.emchat.*;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.EmMessages;
import com.wonders.xlab.healthcloud.entity.doctor.Doctor;
import com.wonders.xlab.healthcloud.repository.EmMessagesRepository;
import com.wonders.xlab.healthcloud.repository.doctor.DoctorRepository;
import com.wonders.xlab.healthcloud.service.WordAnalyzerService;
import com.wonders.xlab.healthcloud.utils.EMUtils;
import com.wonders.xlab.healthcloud.utils.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.*;
import java.util.logging.SimpleFormatter;

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
    private DoctorRepository doctorRepository;

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
    @RequestMapping(value = "replyMessage/{id}/{username}", method = RequestMethod.POST)
    public ControllerResult replyMessage(@PathVariable("id") long id, @PathVariable("username") String username, @RequestBody TexMessagesRequestBody body) throws IOException {
        String messagesJson = objectMapper.writeValueAsString(body);
        //扩展属性
        //   Map<String, String> extendAttr = wordAnalyzerService.analyzeText(body.getMsg().getMsg());

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
        EmMessages newMessage = emMessagesRepository.save(emMessages);
        //回复后发送信息给用户
        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("tel_equal", body.getFrom());
        Doctor doctor = doctorRepository.find(filterMap);
        //TODO 暂时注释
      //  SmsUtils.sendEmReplyInfo(username, doctor.getNickName());
        //修改app发送信息状态为已回复
        EmMessages oldEm = emMessagesRepository.findOne(id);
        oldEm.setIsReplied(true);
        emMessagesRepository.save(oldEm);



        //回复信息耗时 TODO 耗时建议用信鸽推app端
        Period period = new Period(new DateTime(newMessage.getCreatedDate()), new DateTime(oldEm.getCreatedDate()), PeriodType.minutes());
        int time = period.getSeconds();

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
     *
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
    public ControllerResult<ChatGroupsResponseBody> newChatgroups(String username) throws Exception {

        ChatGroupsRequestBody groupsBody = new ChatGroupsRequestBody(username, "万达健康云_" + username, true, 1, false, username);

        String requestBody = objectMapper.writeValueAsString(groupsBody);

        ResponseEntity<String> responseEntity;

        String newRequestBody = StringUtils.replace(requestBody, "_public", "public");

        try {
             responseEntity = (ResponseEntity<String>) emUtils.requestEMChart(HttpMethod.POST, newRequestBody, "chatgroups", String.class);

        } catch (HttpClientErrorException e) {
            throw new RuntimeException(e);
        }

        return new ControllerResult<ChatGroupsResponseBody>().setRet_code(0)
                .setRet_values(objectMapper.readValue(responseEntity.getBody().toString(), ChatGroupsResponseBody.class))
                .setMessage("");
    }

    @RequestMapping(value = "getTop5Messages", method = RequestMethod.POST)
    public List<EmMessages> getTop5Messages(String fromUser, String toUser) {

        return emMessagesRepository.findTop5ByFromUserOrToUserOrderByCreatedDateAsc(fromUser, toUser);

    }

    /**
     * 根据当前时间确定返回的医生的数量
     *
     * @return
     */
    @RequestMapping(value = "emRules", method = RequestMethod.GET)
    public ControllerResult emRules() {
       /* String greetings = "欢迎提问，我们将有专业的医生解决您的问题";
        String questionSample = "最近3天感到省体无力，经常性腹泻xxxxx";
        int doctorNumber = emUtils.getDoctorNumber();*/
        EmDoctorNumber emDoctorNumber = new EmDoctorNumber(
                emUtils.getDoctorNumber(),
                "欢迎提问，我们将有专业的医生解决您的问题",
                "最近3天感到省体无力，经常性腹泻xxxxx"
        );

        return new ControllerResult<EmDoctorNumber>().setRet_code(0).setRet_values(emDoctorNumber).setMessage("");
    }

    /**
     * 查询历史纪录
     * @param groupId
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/queryRecords",method = RequestMethod.GET)
    public ControllerResult<Page<EmMessages>> queryHistoryRecords(String groupId ,Pageable pageable) {
        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("toUser_equal", groupId);
        Page<EmMessages> list =  emMessagesRepository.findAll(filterMap, pageable);
        return new ControllerResult<Page<EmMessages>>().setRet_code(0).setRet_values(list).setMessage("");
    }
}
