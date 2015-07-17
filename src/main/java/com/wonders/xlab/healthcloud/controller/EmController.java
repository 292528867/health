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
import com.wonders.xlab.healthcloud.service.cache.HCCacheProxy;
import com.wonders.xlab.healthcloud.service.emchat.QuestionOrderService;
import com.wonders.xlab.healthcloud.utils.Constant;
import com.wonders.xlab.healthcloud.utils.EMUtils;
import com.wonders.xlab.healthcloud.utils.SmsUtils;
import net.sf.ehcache.Cache;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
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
    private UserRepository userRepository;

    @Autowired
    private QuestionOrderRepository questionOrderRepository;

    @Autowired
    @Qualifier("userQuestionCache")
    private Cache userQuestionCache;

    @Autowired
    @Qualifier("questionOrderCache")
    private Cache questionOrderCache;

    private HCCacheProxy<String, String> questionCache;

    private HCCacheProxy<String, String> orderCache;

    @Autowired
    private QuestionOrderService questionOrderService;

    @Override
    protected MyRepository<EmMessages, Long> getRepository() {
        return emMessagesRepository;
    }

    @PostConstruct
    private void init() {
        questionCache = new HCCacheProxy(userQuestionCache);
        orderCache = new HCCacheProxy<>(questionOrderCache);
    }

    /**
     * 医生回复消息
     *
     * @param body
     * @return
     */
    @RequestMapping(value = "replyMessage/{id}/{userTel}", method = RequestMethod.POST)
    public ControllerResult replyMessage(@PathVariable("id") long id, @PathVariable("userTel") String userTel, @RequestBody TexMessagesRequestBody body) throws IOException {
        EmMessages oldEm = emMessagesRepository.findOne(id);
        if (oldEm == null) {
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values("")
                    .setMessage("回复失败");
        }
        QuestionOrder questionOrder = questionOrderRepository.find(Collections.singletonMap("messages.id_equal", oldEm.getId()));
        if(QuestionOrder.QuestionStatus.done == questionOrder.getQuestionStatus()){
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values("")
                    .setMessage("该问题已回复");
        }
        if (body.getExt() == null) {
            Map<String, Object> map = new HashMap<>();
            body.setExt(map);
        }

        String messagesJson = objectMapper.writeValueAsString(body);
        //扩展属性
        //   Map<String, String> extendAttr = wordAnalyzerService.analyzeText(body.getMsg().getMsg());

        // body.setExt(objectMapper.writeValueAsString(extendAttr));
        //推送给环信
        ResponseEntity<String> responseEntity = (ResponseEntity<String>) emUtils.requestEMChat(messagesJson, "POST", "messages", String.class);
        //保存医生或者运营回复消息

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
        //回复后发送信息给用户
        Doctor doctor = doctorRepository.find(Collections.singletonMap("tel_equal", body.getFrom()));
        SmsUtils.sendEmReplyInfo(userTel);

        //修改app发送信息状态为已回复
        oldEm.setIsReplied(true);
        emMessagesRepository.save(oldEm);

        questionOrder.setQuestionStatus(QuestionOrder.QuestionStatus.done);
        questionOrderRepository.save(questionOrder);

        User user = userRepository.findByTel(userTel);
        //从缓存里面移除该问题
        questionCache.removeFromCache(user.getId() + "_RESPONDENT");
        questionCache.removeFromCache(user.getId() + "_ASK_TIME");
        questionCache.removeFromCache(user.getId() + "_RESPONDENT_TYPE");

        orderCache.removeFromCache(user.getId().toString());

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
    @Transactional
    public ControllerResult sendTxtMessage(@RequestBody TexMessagesRequestBody body) throws IOException {
        //判断该用户的消息是否已有人在处理
        long askTime = System.currentTimeMillis();
        User user = userRepository.findByTel(body.getFrom());
        if (user == null) {
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values("")
                    .setMessage("用户不存在");
        }
        String userAskTime = user.getId() + "_ASK_TIME";
        String askTimeStr = questionCache.putIfAbsent(userAskTime, String.valueOf(askTime));
        if (!String.valueOf(askTime).equals(askTimeStr)) {
            //已有其他线程处理，禁止重复提交
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values("")
                    .setMessage("发送失败");
        } else {
              /*  String messagesJson = objectMapper.writeValueAsString(body);
                //发送信息给环信
                emUtils.requestEMChat(messagesJson, "POST", "messages", String.class);*/
        }
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
        //从缓存中查询该用户是否有正在提问的问题
        if (StringUtils.isNotEmpty(orderCache.getFromCache(user.getId().toString()))) {
            //缓存中存在开放问题，此次发送消息不是新问题，直接发送
            String respondentKey = user.getId() + "_RESPONDENT_TYPE";
            String respondentType = questionCache.getFromCache(respondentKey);
            if (RespondentType.none.toString().equals(respondentType)) {
                //TODO 没有人在处理
            } else if (RespondentType.auto.toString().equals(respondentType)) {
                //TODO 上一条为系统自动答复

            } else if (RespondentType.doctor.toString().equals(respondentType)) {
                //TODO 上一条为医生答复，直接推送给医生即可

            } else if (RespondentType.service.toString().equals(respondentType)) {
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
        }

        return new ControllerResult<>().setRet_code(0).setRet_values(Collections.singletonMap("waiting", Constant.INTERROGATION_WAIT_CONTENT)).setMessage("文本消息发送成功");

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

        return new ControllerResult<>().setRet_code(0).setRet_values("").setMessage("图片信息发送成功");
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
                headers, Collections.singletonMap("file", multipartFile.getBytes()),
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
     * 添加或删除医生从用户群组
     *
     * @param groupId
     * @param doctorName
     * @param type       post(添加) delete(删除)
     * @return
     */
    private boolean joinOrDeleteDoctorToGroup(String groupId, String doctorName, String type) {
        HttpHeaders headers = new HttpHeaders();
        //TODO 从缓存中获取环信token
        headers.add("Authorization", "Bearer YWMtEJuECCJLEeWN-d-uaORhJQAAAU-OGpHmVNOp0Va6o2OEAUzNiA1O9UB_oFw");
        headers.setContentType(MediaType.TEXT_PLAIN);
        try {
            emUtils.requestEMChat(headers, type, "chatgroups/" + groupId + "/users/" + doctorName, String.class);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 查询历史纪录前5条
     *
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
    @RequestMapping(value = "toInterrogation/{tel}", method = RequestMethod.GET)
    public ControllerResult toInterrogation(@PathVariable("tel") String tel) {
        // 当天23:59:59
        DateTime tmpTime = new DateTime(new Date());
        Date endTime = new DateTime(
                tmpTime.getYear(),
                tmpTime.getMonthOfYear(),
                tmpTime.getDayOfMonth(),
                23, 59, 59
        ).toDate();

        String groupId = userRepository.findByTel(tel).getGroupId();

        EmDoctorNumber emDoctorNumber = new EmDoctorNumber();
        emDoctorNumber.setGroupId(groupId);//ios端老是拿不到goupid

        EmMessages newMessages = new EmMessages();
        newMessages.setCreatedDate(new Date());

        //最新问题是否被回复
        EmMessages lastMessage = emMessagesRepository.findTopByToUserAndIsShowForDoctorOrderByCreatedDateDesc(groupId, 0);
        if (lastMessage == null) {//没有任何数据时 第一次访问
            newMessages.setMsg(String.format(Constant.INTERROGATION_GRETTINGS, EMUtils.countDoctors()));
            newMessages.setToUser(groupId);
            newMessages.setIsShowForDoctor(1); //不让医生端看到
            newMessages.setFromUser(tel);

            List<EmMessages> list = new ArrayList<>();
            list.add( emMessagesRepository.save(newMessages));
            emDoctorNumber.setLastQuestionStatus(0);
            emDoctorNumber.setContent(Constant.INTERROGATION_QUESTION_SAMPLE);
            emDoctorNumber.setList(list);
            return new ControllerResult<EmDoctorNumber>().setRet_code(0).setRet_values(emDoctorNumber).setMessage("");
        }

        //判断当天是否有医生数量提示
        EmMessages emMessages = emMessagesRepository.findByToUserAndIsShowForDoctorAndCreatedDateBetween(groupId, 1,
                com.wonders.xlab.healthcloud.utils.DateUtils.covertToYYYYMMDD(new Date()), endTime);


        // LastQuestionStatus 0进入提问页面 1进入等待页面
        if (emMessages == null) {//没有插入医生数量提示
            newMessages.setMsg(String.format(Constant.INTERROGATION_GRETTINGS, EMUtils.countDoctors()));
            newMessages.setToUser(groupId);
            newMessages.setIsShowForDoctor(1); //不让医生端看到
            newMessages.setFromUser(tel);
            emMessagesRepository.save(newMessages);

            List<EmMessages> list = new ArrayList<>();
            list.add(emMessages);
            emDoctorNumber.setLastQuestionStatus(0);
            emDoctorNumber.setContent(Constant.INTERROGATION_QUESTION_SAMPLE);
            emDoctorNumber.setList(list);
            return new ControllerResult<EmDoctorNumber>().setRet_code(0).setRet_values(emDoctorNumber).setMessage("");
        }



        if (lastMessage.getIsReplied()) { //用户已回复

            newMessages.setMsg(String.format(Constant.INTERROGATION_GRETTINGS, EMUtils.countDoctors()));

            List<EmMessages> emMessagesList = null;

            try {
                emMessagesList = emMessagesRepository.findByToUserAndCreatedDateBetweenOrderByCreatedDateDesc(groupId,
                        com.wonders.xlab.healthcloud.utils.DateUtils.covertToYYYYMMDD(new Date()), endTime);
            } catch (Exception e) {
                e.printStackTrace();
            }

/*
            if (emMessagesList != null && emMessagesList.size() > 0) {
                if (emMessagesList.get(0).getIsReplied()) {//回复的去掉
                    emMessagesList.remove(0);
                }
            }*/

            emDoctorNumber.setList(emMessagesList);

            emDoctorNumber.setLastQuestionStatus(0);
            emDoctorNumber.setContent(Constant.INTERROGATION_QUESTION_SAMPLE);
            //  emDoctorNumber.setEmMessages(newMessages);
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
     * @param type     医生查询还是用户查询
     * @return
     */
    @RequestMapping(value = "/queryRecords", method = RequestMethod.GET)
    public ControllerResult<List<EmMessages>> queryHistoryRecords(String groupId, Pageable pageable, String type) {
 /*       Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("toUser_equal", groupId);*/
        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        Pageable pageable1 = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
        List<EmMessages> list = null;
        if (type.equals("user")) {
            Map<String, Object> map = new HashMap<>();
            map.put("toUser_equal", groupId);
            map.put("createdDate_lessThan", com.wonders.xlab.healthcloud.utils.DateUtils.covertToYYYYMMDD(new Date())); //过滤掉今天的数据
            list = emMessagesRepository.findAll(map, pageable1).getContent();
            //如果今天的最早一条记录是回复状态 把这条记录拉到历史记录
        /*    DateTime tmpTime = new DateTime(new Date());
            Date end = new DateTime(
                    tmpTime.getYear(),
                    tmpTime.getMonthOfYear(),
                    tmpTime.getDayOfMonth(),
                    23, 59, 59
            ).toDate();
            List<EmMessages> todayList = emMessagesRepository.findByToUserAndIsShowForDoctorAndCreatedDateBetweenOrderByCreatedDateDesc(groupId, 0,
                    com.wonders.xlab.healthcloud.utils.DateUtils.covertToYYYYMMDD(new Date()), end);
            if (todayList != null && todayList.size() > 0) {
                if (todayList.get(0).getIsReplied()) { //已回复
                    list.add(todayList.get(0));
                }
            }*/

        }
        if (type.equals("doctor")) {
            Map<String, Object> filterMap = new HashMap<>();
            filterMap.put("toUser_equal", groupId);
            filterMap.put("isShowForDoctor_equal", 0);
            list = emMessagesRepository.findAll(filterMap, pageable1).getContent();
        }
        return new ControllerResult<List<EmMessages>>().setRet_code(0).setRet_values(list).setMessage("");
    }

    /**
     * 抢单
     *
     * @param userTel   用户电话
     * @param doctorTel 医生电话
     * @return 成功返回userId和环信groupId
     */
    @RequestMapping(value = "rushOrder/{userTel}/{doctorTel}")
    public ControllerResult rushOrder(@PathVariable final String userTel, @PathVariable final String doctorTel) {
        try {
            final User user = userRepository.findByTel(userTel);
            final Doctor doctor = doctorRepository.findByTel(doctorTel);
            String key = user.getId() + "_RESPONDENT";
            //缓存中用户抢单医生是否匹配 匹配成功则抢单成功
            if (doctor.getId().toString().equals(questionCache.putIfAbsent(key, doctor.getId().toString()))) {
                questionCache.addToCache(key, RespondentType.doctor.toString());

                //查询用户未处理的问题
                List<QuestionOrder> questionOrderList = questionOrderRepository.findAll(
                        new HashMap<String, Object>() {{
                            put("user.id_equal", user.getId());
                            put("questionStatus_equal", QuestionOrder.QuestionStatus.newQuestion);
                        }}
                );
                //抢单成功更新用户问题订单表信息为处理中并管理到抢单医生
                if (CollectionUtils.isNotEmpty(questionOrderList)) {
                    QuestionOrder questionOrder = questionOrderList.get(0);
                    questionOrder.setDoctor(doctor);
                    questionOrder.setQuestionStatus(QuestionOrder.QuestionStatus.processing);
                    questionOrderRepository.save(questionOrder);
                }

                return new ControllerResult<>()
                        .setRet_code(0)
                        .setRet_values(
                                new HashMap<String, Object>() {{
                                    put("userId", user.getId());
                                    put("groupId", user.getGroupId());
                                    put("userTel", userTel);
                                }}
                        )
                        .setMessage("抢单成功！");
            } else {
                questionOrderService.sendQuestionToDoctors(doctor.getTel());
                return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("抢单失败！");
            }
        } catch (Exception e) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("抢单失败！");
        }

    }


    /**
     * 医生App查询抢单纪录
     *
     * @param doctorId 医生的id
     * @return
     */
    @RequestMapping(value = "/doctorOrders/{doctorId}", method = RequestMethod.GET)
    public ControllerResult findQuestionOrders(@PathVariable long doctorId, Pageable pageable) {

        Map<String, QuestionOrder.QuestionStatus> statusMap = new HashMap<>();
        statusMap.put("processing", QuestionOrder.QuestionStatus.processing);
        statusMap.put("done", QuestionOrder.QuestionStatus.done);
        QuestionOrder.QuestionStatus[] statuses = new QuestionOrder.QuestionStatus[]{
                QuestionOrder.QuestionStatus.done,
                QuestionOrder.QuestionStatus.processing
        };
        Map<String, Object> paraMap = new HashMap();
        paraMap.put("doctor.id_equal", doctorId);
        paraMap.put("questionStatus_in", statuses);
//        List<QuestionOrder> orders = questionOrderRepository.findAll(paraMap, pageable).getContent();

        List<QuestionOrder> orders = questionOrderRepository.findQuestionOrdersByDoctorID(doctorId, statuses, pageable);

        QuestionOrder newQuestion = null;
        if(pageable.getPageNumber() == 0){
            newQuestion = questionOrderService.findOneNewQuestion();
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("orders", orders);
        resultMap.put("newQuestion", newQuestion);

        return new ControllerResult<>()
                .setRet_code(0)
                .setRet_values(resultMap)
                .setMessage("success");
    }

    /**
     * 给医生App返回一个新的问题
     *
     * @return
     */
    @RequestMapping(value = "/getNewQuestion", method = RequestMethod.GET)
    public ControllerResult findOneQuestionRandom() {
        QuestionOrder newQuestion = questionOrderService.findOneNewQuestion();
        return new ControllerResult<>()
                .setRet_code(0)
                .setRet_values(newQuestion)
                .setMessage("success");
    }

}
