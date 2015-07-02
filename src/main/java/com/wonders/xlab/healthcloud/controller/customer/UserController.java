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
import com.wonders.xlab.healthcloud.utils.ValidateUtils;
import net.sf.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * Created by Jeffrey on 15/7/2.
 */

@RestController
@RequestMapping("user")
public class UserController extends AbstractBaseController<User, Long> {

//    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserThirdRepository userThirdRepository;

    @Autowired
    @Qualifier(value = "idenCodeCache")
    private Cache idenCodeCache;

    /**
     *  第三方登录
     * @param token 第三方用户标识对象
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
                UserThird userThird = userThirdRepository.findByThirdIdAndThirdType(token.getThirdId(), ThirdBaseInfo.ThirdType.values()[token.getThirdType()]);
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

                User user = userRepository.findByTel(token.getTel());

                if (null == user) {
                    user = new User();
                    user.setTel(token.getTel());
                }

                UserThird userThird = new UserThird();
                userThird.setUser(user);
                userThird.setThirdId(token.getThirdId());
                userThird.setThirdType(ThirdBaseInfo.ThirdType.values()[token.getThirdType()]);
                userThird = userThirdRepository.save(userThird);
                return new ControllerResult<>().setRet_code(0).setRet_values(userThird.getUser().getId());
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
            IdenCode idenCoded = (IdenCode) idenCodeCache.get(idenCode.getTel()).getObjectValue();
            if (null == idenCoded) {
                return new ControllerResult<String>().setRet_code(-1).setRet_values("验证码失效！");
            } else {
                if (!idenCoded.equals(idenCode)) {
                    // 前台输错验证码
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("验证码输入错误！");
                } else {
                    User user = userRepository.findByTel(idenCode.getTel());
                    // 如果未找到用户则进行注册登陆
                    if (null == user) {
                        user = new User();
                        user.setTel(idenCode.getTel());
                        user = userRepository.save(user);
                        return new ControllerResult<>().setRet_code(0).setRet_values(user.getId());
                    } else
                        return new ControllerResult<>().setRet_code(0).setRet_values(user.getId());
                }
            }
        } catch (Exception e) {
            return new ControllerResult<>().setRet_code(-1).setRet_values(e.getLocalizedMessage());
        }
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
