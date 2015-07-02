package com.wonders.xlab.healthcloud.controller.customer;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.IdenCode;
import com.wonders.xlab.healthcloud.dto.ThirdLoginToken;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.ThirdBaseInfo;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.customer.UserThird;
import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import com.wonders.xlab.healthcloud.repository.customer.UserThirdRepository;
import com.wonders.xlab.healthcloud.utils.QiniuUploadUtils;
import com.wonders.xlab.healthcloud.utils.ValidateUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URLDecoder;


/**
 * Created by Jeffrey on 15/7/2.
 */

@RestController
@RequestMapping("user")
public class UserController extends AbstractBaseController<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserThirdRepository userThirdRepository;

    @Autowired
    @Qualifier(value = "idenCodeCache")
    private Cache idenCodeCache;

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
                logger.info("三方登陆时电话为空,thirdId={}",token.getThirdId());
                UserThird userThird = userThirdRepository.findByThirdIdAndThirdType(token.getThirdId(), ThirdBaseInfo.ThirdType.values()[Integer.parseInt(token.getThirdType())]);
                //找不到指定类型第三方，该第三方第一次登陆
                if (null == userThird) {
                    return new ControllerResult<>().setRet_code(1).setRet_values("").setMessage("用户不存在!");
                } else {
                    logger.info("三方登陆获取到用户信息,userId={}",userThird.getUser().getId());
                    //通过第三方登陆返回第三方关联用户信息
                    return new ControllerResult<>().setRet_code(0).setRet_values(userThird.getUser()).setMessage("获取用户成功!");
                }
            } else {
                //电话号码格式验证不通过
                if (!ValidateUtils.validateTel(token.getTel())) {
                    return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("关联的手机号格式不正确！");
                }

                // 获取指定手机号的验证编码缓存并，比较是否相同
                Element element = idenCodeCache.get(token.getTel());

                if (null == element || null == element.getObjectValue()) {
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码失效！");
                } else {
                    logger.info("tel={},code={},cascode={}", token.getTel(),token.getCode(),element.getObjectValue());
                    if (!token.getCode().equals(element.getObjectValue())) {
                        // 前台输错验证码
                        return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码输入错误！");
                    } else {
                        //通过电话获取用户
                        User user = userRepository.findByTel(token.getTel());

                        //用户为空创建用户和第三方关联
                        if (null == user) {
                            user = new User();
                            user.setTel(token.getTel());
                        }

                        UserThird userThird = new UserThird();
                        userThird.setUser(user);
                        userThird.setThirdId(token.getThirdId());
                        userThird.setThirdType(ThirdBaseInfo.ThirdType.values()[Integer.valueOf(token.getThirdType())]);
                        userThird = userThirdRepository.save(userThird);
                        logger.info("三方登陆新增绑定,thirdId={},userId={}",token.getThirdId(),userThird.getUser().getId());
                        return new ControllerResult<>().setRet_code(0).setRet_values(userThird.getUser()).setMessage("获取用户成功!");
                    }
                }
            }
        } catch (Exception exp) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("exp.getLocalizedMessage()");
        }
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
            Element element = idenCodeCache.get(idenCode.getTel());

            if (null == element || null == element.getObjectValue()) {
                return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码失效！");
            } else {
                if (!idenCode.getCode().equals(element.getObjectValue())) {
                    // 前台输错验证码
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码输入错误！");
                } else {
                    User user = userRepository.findByTel(idenCode.getTel());
                    // 如果未找到用户则进行注册登陆
                    if (null == user) {
//                        logger.info("user is null");
                        user = new User();
                        user.setTel(idenCode.getTel());
                        user = userRepository.save(user);
                        return new ControllerResult<>().setRet_code(0).setRet_values(user).setMessage("获取用户成功!");
                    } else
                        return new ControllerResult<>().setRet_code(0).setRet_values(user).setMessage("获取用户成功!");
                }
            }
        } catch (Exception e) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("exp.getLocalizedMessage()");
        }
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
                String filename = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");
                String url = QiniuUploadUtils.upload(file.getBytes(), filename);
                user.setIconUrl(url);
                userRepository.save(user);
                return new ControllerResult<>().setRet_code(0).setRet_values(url);
            } catch (Exception e) {
                e.printStackTrace();
                return new ControllerResult<>().setRet_code(-1).setRet_values(e.getLocalizedMessage());
            }
        }
        return null;
    }

    @RequestMapping(value = "test/{tel}")
    public Object sendValid(@PathVariable String tel) {
        return tel;
    }

    @Override
    protected MyRepository<User, Long> getRepository() {
        return userRepository;
    }
}
