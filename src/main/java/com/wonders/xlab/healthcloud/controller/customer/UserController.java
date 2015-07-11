package com.wonders.xlab.healthcloud.controller.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.IdenCode;
import com.wonders.xlab.healthcloud.dto.ThirdLoginToken;
import com.wonders.xlab.healthcloud.dto.customer.UserDto;
import com.wonders.xlab.healthcloud.dto.emchat.ChatGroupsRequestBody;
import com.wonders.xlab.healthcloud.dto.emchat.ChatGroupsResponseBody;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.ThirdBaseInfo;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.customer.UserThird;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import com.wonders.xlab.healthcloud.repository.customer.UserThirdRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageRepository;
import com.wonders.xlab.healthcloud.service.cache.HCCache;
import com.wonders.xlab.healthcloud.service.cache.HCCacheProxy;
import com.wonders.xlab.healthcloud.service.hcpackage.UserPackageOrderService;
import com.wonders.xlab.healthcloud.utils.BeanUtils;
import com.wonders.xlab.healthcloud.utils.EMUtils;
import com.wonders.xlab.healthcloud.utils.QiniuUploadUtils;
import com.wonders.xlab.healthcloud.utils.ValidateUtils;
import net.sf.ehcache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.HashSet;


/**
 * Created by Jeffrey on 15/7/2.
 */

@RestController
@RequestMapping("user")
public class UserController extends AbstractBaseController<User, Long> {

    private HCCache<String, String> hcCache;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserThirdRepository userThirdRepository;

    @Autowired
    private HcPackageRepository hcPackageRepository;

    @Autowired
    @Qualifier(value = "idenCodeCache")
    private Cache idenCodeCache;

    @Autowired
    private EMUtils emUtils;

    @Autowired
    private UserPackageOrderService userPackageOrderService;

    @PostConstruct
    private void init() {
        hcCache = new HCCacheProxy<>(idenCodeCache);
    }

    /**
     * 第三方登录
     *
     * @param token  第三方用户标识对象
     * @param result
     * @return ControllerResult
     */
    @RequestMapping(value = "otherlogin", method = RequestMethod.POST)
    public Object otherLogin(@RequestBody @Valid ThirdLoginToken token, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors())
                builder.append(error.getDefaultMessage());
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage(builder.toString());
        }
        try {
            //登陆不带手机号
            if (StringUtils.isEmpty(token.getTel())) {
                logger.info("三方登陆时电话为空,thirdId={},AppPlatform={}", token.getThirdId(), token.getAppPlatform());
                UserThird userThird = userThirdRepository.findByThirdIdAndThirdType(
                        token.getThirdId(),
                        ThirdBaseInfo.ThirdType.values()[Integer.parseInt(token.getThirdType())]
                );
//                return null == userThird ?
//                        new ControllerResult<>().setRet_code(1).setRet_values("").setMessage("用户不存在!") :
//                        new ControllerResult<>().setRet_code(0).setRet_values(userThird.getUser()).setMessage("获取用户成功!");
                if (null == userThird) {
                    return new ControllerResult<>().setRet_code(1).setRet_values("").setMessage("用户不存在!");
                } else {
                    //判断数据库平台是否与登陆一致，不一致更新数据库
                    if (token.getAppPlatform().equals(userThird.getUser().getAppPlatform())) {
                        return new ControllerResult<>().setRet_code(0).setRet_values(userThird.getUser()).setMessage("获取用户成功!");
                    } else {

                        User user = userThird.getUser();
                        user.setAppPlatform(token.getAppPlatform());
                        user = userRepository.save(user);
                        return new ControllerResult<>().setRet_code(0).setRet_values(user).setMessage("获取用户成功!");
                    }
                }
            } else {
                //电话号码格式验证不通过
                if (!ValidateUtils.validateTel(token.getTel())) {
                    return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("关联的手机号格式不正确！");
                }

                // 获取指定手机号的验证编码缓存并，比较是否相同
                String cascheValue = hcCache.getFromCache(token.getTel());
                // 验证码失效 ？返回失效 ：（ 验证码匹配不正确 ？验证不通过 ：绑定和返回用户 ）
                return StringUtils.isEmpty(cascheValue) ?
                        new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码失效！") :
                        !token.getCode().equals(cascheValue) ?
                                new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码输入错误！") :
                                bindingThirdparty(token);
            }
        } catch (Exception exp) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage(exp.getLocalizedMessage());
        }
    }

    /**
     * 绑定合并或者创建用户三方
     *
     * @param token 三方登陆dto
     * @return
     */
    private ControllerResult<?> bindingThirdparty(ThirdLoginToken token) {
        //通过电话获取用户
        User user = userRepository.findByTel(token.getTel());
        //用户为空创建用户和第三方关联
        if (null == user) {
            //创建环信账号
            boolean result = registerEmUser(token.getTel(), token.getTel());
            if (!result) {
                return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("注册失败!");
            }
            user = new User();
            user.setTel(token.getTel());
            user.setAppPlatform(token.getAppPlatform());
        }
        UserThird userThird = new UserThird(
                token.getThirdId(),
                ThirdBaseInfo.ThirdType.values()[Integer.valueOf(token.getThirdType())],
                user
        );
        userThird = userThirdRepository.save(userThird);
        logger.info("三方登陆新增绑定,thirdId={},userId={},AppPlatform={}", token.getThirdId(), userThird.getUser().getId(), token.getAppPlatform());
        return new ControllerResult<>().setRet_code(0).setRet_values(userThird.getUser()).setMessage("获取用户成功!");
    }

    /**
     * 用户登陆
     *
     * @param idenCode {@link IdenCode} 用户标识对象
     * @param result
     * @return ControllerResult
     */
    @RequestMapping(value = "hclogin", method = RequestMethod.POST)
    public Object hcLogin(@RequestBody @Valid IdenCode idenCode, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors())
                builder.append(error.getDefaultMessage());
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage(builder.toString());
        }
        try {
            // 获取指定手机号的验证编码缓存并，比较是否相同
            String cascheValue = hcCache.getFromCache(idenCode.getTel());
            if (StringUtils.isEmpty(cascheValue)) {
                return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码失效！");
            } else {
                if (!idenCode.getCode().equals(cascheValue)) {
                    // 前台输错验证码
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码输入错误！");
                } else {
                    User user = userRepository.findByTel(idenCode.getTel());
                    //用户不存在 ？创建并返回用户 ：直接返回用户
//                    return null == user ? addUserBeforeLogin(idenCode) :
//                            new ControllerResult<>().setRet_code(0).setRet_values(user).setMessage("获取用户成功!");
                    if (null == user) {
                        //创建环信账号并创建一个群组
                        return addUserBeforeLogin(idenCode);
                    } else {
                        //判断数据库平台是否与登陆一致，不一致更新数据库
                        if (idenCode.getAppPlatform().equals(user.getAppPlatform())) {
                            return new ControllerResult<>().setRet_code(0).setRet_values(user).setMessage("获取用户成功!");
                        } else {
                            user.setAppPlatform(idenCode.getAppPlatform());
                            return new ControllerResult<>().setRet_code(0).setRet_values(userRepository.save(user)).setMessage("获取用户成功!");
                        }
                    }
                }
            }
        } catch (Exception e) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage(e.getLocalizedMessage());
        }
    }

    private ControllerResult<?> addUserBeforeLogin(IdenCode idenCode) throws Exception {

        //创建环信账号
        boolean result = registerEmUser(idenCode.getTel(), idenCode.getTel());
        if (!result) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("注册失败!");
        }

        //创建一个群组
        ChatGroupsRequestBody groupsBody = new ChatGroupsRequestBody(idenCode.getTel(), "万达健康云_" + idenCode.getTel(), true, 1, false, idenCode.getTel());

        String requestBody = objectMapper.writeValueAsString(groupsBody);

        ResponseEntity<String> responseEntity;

        String newRequestBody = StringUtils.replace(requestBody, "_public", "public");

        try {
            responseEntity = (ResponseEntity<String>) emUtils.requestEMChat(newRequestBody, "POST", "chatgroups", String.class);

        } catch (HttpClientErrorException e) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("创建群组失败");
        }

        User user = new User();
        user.setGroupId(objectMapper.readValue(responseEntity.getBody().toString(), ChatGroupsResponseBody.class).getData().getGroupid());
        user.setTel(idenCode.getTel());
        user.setAppPlatform(idenCode.getAppPlatform());
        user = userRepository.save(user);
        return new ControllerResult<>().setRet_code(0).setRet_values(user).setMessage("注册用户成功，并成功创建群组");
    }

    /**
     * 用户上传图片
     *
     * @param id   用户id
     * @param file 用户图像
     * @return
     * @throws Exception
     */
    @Transactional
    @RequestMapping(value = "uploadPic/{id}", method = RequestMethod.POST)
    public Object uploadPic(@PathVariable long id, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                User user = userRepository.findOne(id);
                if (null == user)
                    return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("用户不存在!");
                String filename = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");
                String url = QiniuUploadUtils.upload(file.getBytes(), filename);
                user.setIconUrl(url);
                user = userRepository.save(user);
                return new ControllerResult<>().setRet_code(0).setRet_values(user).setMessage("图片上传成功!");
            } catch (Exception e) {
                e.printStackTrace();
                return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage(e.getLocalizedMessage());
            }
        }
        return null;
    }

    @RequestMapping(value = "modify/{userId}", method = RequestMethod.POST)
    @Transactional
    public Object modify(@PathVariable long userId, @RequestBody @Valid UserDto userDto, BindingResult result) {

        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors())
                builder.append(error.getDefaultMessage());
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage(builder.toString());
        }
        userDto.setValid(User.Valid.valid);
        User user = userRepository.findOne(userId);
        try {
            BeanUtils.copyNotNullProperties(userDto, user, "hcPackageId");
            final HcPackage hcPackage = hcPackageRepository.findOne(userDto.getHcPackageId());
            if (user.getHcPackages() == null) {
                user.setHcPackages(new HashSet<HcPackage>() {{
                    add(hcPackage);
                }});
            } else {
                user.getHcPackages().add(hcPackage);
            }
            user = modify(user);
            user.setHcPackages(null);
            ControllerResult controllerResult = (ControllerResult) userPackageOrderService.joinPlan(userId, userDto.getHcPackageId());
            if (controllerResult.getRet_code() != 0) {
                return controllerResult;
            } else {
                return new ControllerResult<>().setRet_code(0).setRet_values(user).setMessage("用户更新成功!");
            }
        } catch (Exception exp) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("更新失败!");
        }

    }

    @Override
    protected MyRepository<User, Long> getRepository() {
        return userRepository;
    }

    private boolean registerEmUser(final String username, final String password) {
        try {
            emUtils.requestEMChat(new ObjectMapper().writeValueAsString(
                    new HashMap<String, String>() {{
                        put("username", username);
                        put("password", password);
                    }}
            ), "POST", "users", String.class);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}
