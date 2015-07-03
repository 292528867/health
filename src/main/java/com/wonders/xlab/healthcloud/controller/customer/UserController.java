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
import com.wonders.xlab.healthcloud.service.cache.HCCache;
import com.wonders.xlab.healthcloud.service.cache.HCCacheProxy;
import com.wonders.xlab.healthcloud.utils.QiniuUploadUtils;
import com.wonders.xlab.healthcloud.utils.ValidateUtils;
import net.sf.ehcache.Cache;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

    private HCCache<String, String> hcCache;

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
                logger.info("三方登陆时电话为空,thirdId={}", token.getThirdId());
                UserThird userThird = userThirdRepository.findByThirdIdAndThirdType(
                        token.getThirdId(),
                        ThirdBaseInfo.ThirdType.values()[Integer.parseInt(token.getThirdType())]
                );
                return null == userThird ?
                        new ControllerResult<>().setRet_code(1).setRet_values("").setMessage("用户不存在!") :
                        new ControllerResult<>().setRet_code(0).setRet_values(userThird.getUser()).setMessage("获取用户成功!");
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
            user = new User();
            user.setTel(token.getTel());
        }
        UserThird userThird = new UserThird(
                token.getThirdId(),
                ThirdBaseInfo.ThirdType.values()[Integer.valueOf(token.getThirdType())],
                user
        );
        userThird = userThirdRepository.save(userThird);
        logger.info("三方登陆新增绑定,thirdId={},userId={}", token.getThirdId(), userThird.getUser().getId());
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
                    return null == user ? addUserBeforeLogin(idenCode) :
                            new ControllerResult<>().setRet_code(0).setRet_values(user).setMessage("获取用户成功!");
                }
            }
        } catch (Exception e) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage(e.getLocalizedMessage());
        }
    }

    private ControllerResult<?> addUserBeforeLogin(IdenCode idenCode) {
        User user = new User();
        user.setTel(idenCode.getTel());
        user = userRepository.save(user);
        return new ControllerResult<>().setRet_code(0).setRet_values(user).setMessage("获取用户成功!");
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

    @RequestMapping(
            method = {RequestMethod.PUT}
    )
    @Override
    public User modify(@RequestBody User entity) {
        entity.setValid(User.Valid.valid);
        User user = userRepository.findOne(entity.getId());
        user = copyNotNullProperty(user, entity);
        return super.modify(user);
    }

    private static User copyNotNullProperty(User dest, User orig) {
        Field[] destFields = dest.getClass().getDeclaredFields();
        for (Field destField : destFields) {
            destField.setAccessible(true);
            Field origField = null;
            try {
                origField = orig.getClass().getDeclaredField(destField.getName());
                origField.setAccessible(true);
                Object propertyValue = origField.get(orig);
                if (null != propertyValue)
                    destField.set(dest, propertyValue);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }finally {
                origField.setAccessible(false);
                destField.setAccessible(false);
            }
        }
        return dest;
    }

    @Override
    protected MyRepository<User, Long> getRepository() {
        return userRepository;
    }
}
