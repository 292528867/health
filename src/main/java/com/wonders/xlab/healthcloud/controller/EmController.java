package com.wonders.xlab.healthcloud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.EmDoctorNumber;
import com.wonders.xlab.healthcloud.dto.emchat.*;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.EmMessages;
import com.wonders.xlab.healthcloud.entity.QuestionOrder;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.doctor.Doctor;
import com.wonders.xlab.healthcloud.enums.RespondentType;
import com.wonders.xlab.healthcloud.repository.EmMessagesRepository;
import com.wonders.xlab.healthcloud.repository.QuestionOrderRepository;
import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import com.wonders.xlab.healthcloud.repository.doctor.DoctorRepository;
import com.wonders.xlab.healthcloud.service.WordAnalyzerService;
import com.wonders.xlab.healthcloud.service.cache.HCCacheProxy;
import com.wonders.xlab.healthcloud.utils.Constant;
import com.wonders.xlab.healthcloud.utils.EMUtils;
import com.wonders.xlab.healthcloud.utils.SmsUtils;
import net.sf.ehcache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionOrderRepository questionOrderRepository;

    @Autowired
    @Qualifier("userQuestionCache")
    private Cache userQuestionCache;

    @Autowired
    @Qualifier("questionOrderCache")
    private Cache questionOrderCache;


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
        ResponseEntity<String> responseEntity = (ResponseEntity<String>) emUtils.requestEMChat(messagesJson,"POST",  "messages", String.class);
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
       /* Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("tel_equal", body.getFrom());
        Doctor doctor = doctorRepository.find(filterMap);*/
        Doctor doctor = doctorRepository.find(Collections.singletonMap("tel_equal", body.getFrom()));
        //TODO 发短信注释
         SmsUtils.sendEmReplyInfo(username);
        //修改app发送信息状态为已回复
        EmMessages oldEm = emMessagesRepository.findOne(id);
        oldEm.setIsReplied(true);
        emMessagesRepository.save(oldEm);


        //回复信息耗时 TODO 推送给app 暂时注释
     /*   Period period = new Period(new DateTime(newMessage.getCreatedDate()), new DateTime(oldEm.getCreatedDate()), PeriodType.minutes());
        int time =period.getSeconds()/60;*/
     /*   Minutes minutes = Minutes.minutesBetween(new DateTime(newMessage.getCreatedDate()), new DateTime(oldEm.getCreatedDate()));
        User user = userRepository.find(Collections.singletonMap("tel_equal", oldEm.getFromUser()));
        if (user.getAppPlatform().equals("Ios")) {
            XingeApp.pushAccountIos(Constant.XINGE_IOS_ACCESS_ID, Constant.XINGE_IOS_SECRET_KEY,
                    "",String.format(Constant.INTERROGATION_REPLY_PUSH,doctor.getNickName(),minutes.getMinutes()),XingeApp.IOSENV_DEV);
        }
        if (user.getAppPlatform().equals("Android")) {
            XingeApp.pushAccountAndroid(Constant.XINGE_ANDROID_ACCESS_ID, Constant.XINGE_ANDROID_SECRET_KEY,
                    "",String.format(Constant.INTERROGATION_REPLY_PUSH,doctor.getNickName(),minutes.getMinutes()),user.getTel());
        }*/

        return new ControllerResult().setRet_code(0).setRet_values(responseEntity.getBody()).setMessage("消息发送成功");

    }

    /**
     * (接受app)文本消息发送
     */

    @RequestMapping(value = "sendTxtMessage", method = RequestMethod.POST)
    public ControllerResult sendTxtMessage(@RequestBody TexMessagesRequestBody body) throws IOException {
        //判断该用户的消息是否已有人在处理
        long askTime = System.currentTimeMillis();
        User user = userRepository.findByTel(body.getFrom());
        HCCacheProxy<String, String> questionCache = new HCCacheProxy(userQuestionCache);
        HCCacheProxy<String, String> orderCache = new HCCacheProxy<>(questionOrderCache);

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

        emMessages = emMessagesRepository.save(emMessages);

        if(StringUtils.isNotEmpty(orderCache.getFromCache(user.getId().toString()))){
            //缓存中存在开放问题，此次发送消息不是新问题，直接发送
            String respondentKey = user.getId() + "_RESPONDENT_TYPE";
            String respondentType = questionCache.getFromCache(respondentKey);
            if(RespondentType.none.toString().equals(respondentType)){
                //TODO 没有人在处理
            } else if(RespondentType.auto.toString().equals(respondentType)){
                //TODO 上一条为系统自动答复

            } else if(RespondentType.doctor.toString().equals(respondentType)){
                //TODO 上一条为医生答复，直接推送给医生即可

            } else if(RespondentType.service.toString().equals(respondentType)){
                //TODO 上一条为运营人员答复

            }
        } else {
            //本次提问是新问题，往QuestionOrder中插入一条新的记录
            QuestionOrder questionOrder = new QuestionOrder();
            questionOrder.setUser(user);
            questionOrder.setMessages(emMessages);
            questionOrder.setQuestionStatus(QuestionOrder.QuestionStatus.newQuestion);
            questionOrder = questionOrderRepository.save(questionOrder);
            orderCache.putIfAbsent(user.getId().toString(), questionOrder.getId().toString());
            String userAskTime = user.getId() + "_ASK_TIME";
            String askTimeStr = questionCache.putIfAbsent(userAskTime, String.valueOf(askTime));
            if(String.valueOf(askTime).equals(askTimeStr)){
                //TODO 首先自动回复一条消息

                //TODO 发给医生抢答
                //TODO 遍历医生逐个发送 or 发送到一个医生组里


            } else {
                //已有其他线程处理，禁止重复提交
                return new ControllerResult()
                        .setRet_code(-1)
                        .setRet_values("")
                        .setMessage("发送失败");
            }

        }

        String messagesJson = objectMapper.writeValueAsString(body);
        //发送信息
        emUtils.requestEMChat(messagesJson,"POST", "messages", String.class);

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
        emUtils.requestEMChat("POST", messagesJson, "messages", String.class);
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
/*
        HashMap<String, Object> map = new HashMap<>();
        map.put("file", multipartFile.getBytes());

        MultiValueMap multiValueMap = new LinkedMultiValueMap();
        multiValueMap.setAll(map);*/

        ResponseEntity<ChatFilesResponseBody> responseEntity = (ResponseEntity<ChatFilesResponseBody>) emUtils.requestEMChat(
                headers, Collections.singletonMap("file",multipartFile.getBytes()),
                "POST", "chatfiles", ChatFilesResponseBody.class
        );

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
            emUtils.requestEMChat("POST", body, "users", String.class);
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
        String body = objectMapper.writeValueAsString(Collections.singletonMap("nickname", nickname));

        try {

           emUtils.requestEMChat(HttpMethod.PUT, body, "users/" + username, ChatGroupsResponseBody.class);

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

             responseEntity = (ResponseEntity<String>) emUtils.requestEMChat("POST", newRequestBody, "chatgroups", String.class);

        } catch (HttpClientErrorException e) {
            throw new RuntimeException(e);
        }

        return new ControllerResult<ChatGroupsResponseBody>().setRet_code(0)
                .setRet_values(objectMapper.readValue(responseEntity.getBody().toString(), ChatGroupsResponseBody.class))
                .setMessage("");
    }

    /**
     * 查询历史纪录前5条
     * @param groupId
     * @return
     */
    @RequestMapping(value = "getTop5Messages", method = RequestMethod.GET)
    public List<EmMessages> getTop5Messages(String groupId) {

        return emMessagesRepository.findTop5ByToUserOrderByCreatedDateAsc(groupId);

    }

    /**
     * 根据当前时间确定返回的医生的数量
     *
     * @return
     */
    @RequestMapping(value = "toInterrogation/{tel}/{flag}", method = RequestMethod.GET)
    public ControllerResult toInterrogation(@PathVariable("tel") String tel ,@PathVariable("flag") int flag) {
       /* String greetings = "欢迎提问，我们将有专业的医生解决您的问题";
        String questionSample = "最近3天感到省体无力，经常性腹泻xxxxx";
        int doctorNumber = emUtils.getDoctorNumber();*/
        EmMessages emMessages = emMessagesRepository.findTop1ByFromUserOrderByCreatedDateDesc(tel);
        /*String greetings = "你并没有发烧但感到头疼脑热？你并没有心脏病，但胸闷气短？你有一些小症状但不知是什么情况？不用出门，轻问诊" + EMUtils.countDoctors()+ "位专家帮你了解自己的身体现状。";
        String questionSample = "我40岁，糖尿病7年，血糖一直偏高，空腹血糖一直在9左右，餐后血糖13左右。一直在吃阿卡波糖片，前段时间换了药，不但血糖没有降低，反而出现了心慌、胸闷、气短的症状。现在不知道要怎么办，需要打胰岛素吗？";
        String waitContent = "此刻我们十分理解您的担忧与焦虑，我们已布下天罗地网缉拿专家为您解答困惑。稍后专家将亲自奉上本月全勤奖金XX健康豆，别客气，拿着！";
        String overTimeContent = "此刻我们十分理解您的担忧与焦虑，我们已布下天罗地网缉拿专家为您解答困惑。稍后专家将亲自奉上本月全勤奖金XX健康豆，别客气，拿着！";*/
        String groupId = userRepository.findByTel(tel).getGroupId();
        EmDoctorNumber emDoctorNumber = new EmDoctorNumber();
        EmMessages newMessages = new EmMessages();
        newMessages.setCreatedDate(new Date());
        newMessages.setGroupId(groupId);

        if (emMessages == null) {
            newMessages.setMsg(String.format(Constant.INTERROGATION_GRETTINGS, EMUtils.countDoctors()));
            if(flag == 1) {
                newMessages.setToUser(groupId);
                emMessagesRepository.save(newMessages);
            }
            emDoctorNumber.setLastQuestionStatus(0);
            emDoctorNumber.setContent(Constant.INTERROGATION_QUESTION_SAMPLE);
            emDoctorNumber.setEmMessages(newMessages);
            return new ControllerResult<EmDoctorNumber>().setRet_code(0).setRet_values(emDoctorNumber).setMessage("");
        }
        if (emMessages.getIsReplied()) { //用户已回复
            newMessages.setMsg(String.format(Constant.INTERROGATION_GRETTINGS,EMUtils.countDoctors()));
            if(flag == 1) {
                newMessages.setToUser(groupId);
                  emMessagesRepository.save(newMessages);
            }
            emDoctorNumber.setLastQuestionStatus(0);
            emDoctorNumber.setContent(Constant.INTERROGATION_QUESTION_SAMPLE);
            emDoctorNumber.setEmMessages(newMessages);
            return new ControllerResult<EmDoctorNumber>().setRet_code(0).setRet_values(emDoctorNumber).setMessage("");
        }
        //用户没有回复
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(emMessages.getCreatedDate());
        if (calendar.getTimeInMillis() - calendar1.getTimeInMillis() >= EMUtils.getOvertime() * 60 * 1000) {  // 超时
            emDoctorNumber.setLastQuestionStatus(1);
            emDoctorNumber.setContent(Constant.INTERROGATION_OVERTIME_CONTENT);
            return new ControllerResult<EmDoctorNumber>().setRet_code(0).setRet_values(emDoctorNumber).setMessage("");
        } else {
            emDoctorNumber.setLastQuestionStatus(1);
            emDoctorNumber.setContent(Constant.INTERROGATION_WAIT_CONTENT);
            return new ControllerResult<EmDoctorNumber>().setRet_code(0).setRet_values(emDoctorNumber).setMessage("");
        }


    }

    /**
     * 查询历史纪录
     *
     * @param groupId
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/queryRecords", method = RequestMethod.GET)
    public ControllerResult<Page<EmMessages>> queryHistoryRecords(String groupId, Pageable pageable) {
        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("toUser_equal", groupId);
        Page<EmMessages> list = emMessagesRepository.findAll(filterMap, pageable);
        return new ControllerResult<Page<EmMessages>>().setRet_code(0).setRet_values(list).setMessage("");
    }

}
