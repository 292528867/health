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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public Object otherLogin(@Valid ThirdLoginToken token, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors())
                builder.append(error.getDefaultMessage());
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString());
        }
        try {
            //登陆不带手机号
            if (null == token.getTel()) {
                UserThird userThird = userThirdRepository.findByThirdIdAndThirdType(token.getThirdId(), ThirdBaseInfo.ThirdType.values()[Integer.parseInt(token.getThirdType())]);
                //找不到指定类型第三方，该第三方第一次登陆
                if (null == userThird) {
                    return new ControllerResult<>().setRet_code(-1).setRet_values("用户不存在!");
                } else {
                    //通过第三方登陆返回第三方关联用户信息
                    return new ControllerResult<>().setRet_code(0).setRet_values(userThird.getUser());
                }
            } else {
                //电话号码格式验证不通过
                if (!ValidateUtils.validateTel(token.getTel())) {
                    return new ControllerResult<>().setRet_code(-1).setRet_values("关联的手机号格式不正确！");
                }

                // 获取指定手机号的验证编码缓存并，比较是否相同
                Element element = idenCodeCache.get(token.getTel());

                if (null == element || null == (String) element.getObjectValue() ) {
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("验证码失效！");
                } else {
                    if (!token.getCode().equals((String) element.getObjectValue())) {
                        // 前台输错验证码
                        return new ControllerResult<String>().setRet_code(-1).setRet_values("验证码输入错误！");
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
                        return new ControllerResult<>().setRet_code(0).setRet_values(userThird.getUser());
                    }
                }
            }
        } catch (Exception exp) {
            return new ControllerResult<>().setRet_code(-1).setRet_values(exp.getLocalizedMessage());
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
    public Object hcLogin(@Valid IdenCode idenCode, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors())
                builder.append(error.getDefaultMessage());
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString());
        }
        try {
            // 获取指定手机号的验证编码缓存并，比较是否相同
            Element element = idenCodeCache.get(idenCode.getTel());

            if (null == element || null == (String) element.getObjectValue()) {
                return new ControllerResult<String>().setRet_code(-1).setRet_values("验证码失效！");
            } else {
                if (!idenCode.getCode().equals((String) element.getObjectValue())) {
                    // 前台输错验证码
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("验证码输入错误！");
                } else {
                    User user = userRepository.findByTel(idenCode.getTel());
                    // 如果未找到用户则进行注册登陆
                    if (null == user) {
                        logger.info("user is null");
                        user = new User();
                        user.setTel(idenCode.getTel());
                        user = userRepository.save(user);
                        return new ControllerResult<>().setRet_code(0).setRet_values(user);
                    } else
                        return new ControllerResult<>().setRet_code(0).setRet_values(user);
                }
            }
        } catch (Exception e) {
            return new ControllerResult<>().setRet_code(-1).setRet_values(e.getLocalizedMessage());
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
    @RequestMapping(value = "uploadPic/{id}", method = RequestMethod.POST)
    public String uploadPic(@PathVariable long id, @RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            User user = userRepository.findOne(id);
            String filename = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");
            String url = QiniuUploadUtils.upload(file.getBytes(), filename);
            user.setIconUrl(url);
            userRepository.save(user);
            return url;
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
