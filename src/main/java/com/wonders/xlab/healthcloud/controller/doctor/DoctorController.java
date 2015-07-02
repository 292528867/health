package com.wonders.xlab.healthcloud.controller.doctor;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.IdenCode;
import com.wonders.xlab.healthcloud.dto.ThirdLoginToken;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.ThirdBaseInfo;
import com.wonders.xlab.healthcloud.entity.doctor.Doctor;
import com.wonders.xlab.healthcloud.entity.doctor.DoctorThird;
import com.wonders.xlab.healthcloud.repository.doctor.DoctorRepository;
import com.wonders.xlab.healthcloud.repository.doctor.DoctorThirdRepository;
import com.wonders.xlab.healthcloud.utils.ValidateUtils;
import net.sf.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by mars on 15/7/2.
 */
@RestController
@RequestMapping("doctor")
public class DoctorController extends AbstractBaseController<Doctor, Long> {

    @Autowired
    @Qualifier("idenCodeCache")
    private Cache idenCodeCache;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorThirdRepository doctorThirdRepository;

    @Override
    protected MyRepository<Doctor, Long> getRepository() {
        return doctorRepository;
    }


    /**
     * 手机登陆
     *
     * @param iden
     * @param result
     * @return
     */
    @RequestMapping("mlogin")
    private Object mlogin(@Valid IdenCode iden, BindingResult result) {

        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
                return builder.toString();
            }
        }
        try {
            // 获取指定手机号的验证编码缓存并，比较是否相同
            IdenCode iden_cached = (IdenCode) idenCodeCache.get(iden.getPhone()).getObjectValue();

            if (iden_cached == null) {
                // cache失效罗
                return new ControllerResult<String>().setRet_code(-1).setRet_values("验证码失效！");
            } else {
                if (!iden_cached.equals(iden)) {
                    // 前台输错验证码罗
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("验证码输入错误！");
                } else {
                    Doctor doctor = this.doctorRepository.findByTel(iden_cached.getPhone());
                    if (doctor == null) { // 如果是新用户，插入记录
                        doctor = new Doctor();
                        doctor.setTel(iden.getPhone());
                        doctor = this.doctorRepository.save(doctor);
                        return new ControllerResult<Doctor>().setRet_code(0).setRet_values(doctor);
                    } else {
                        return new ControllerResult<Doctor>().setRet_code(0).setRet_values(doctor);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values(e.getLocalizedMessage());
        }
    }

    /**
     * 第三方登陆
     * @param token
     * @param result
     * @return
     */
    @RequestMapping("thirdLogin")
    public Object thirdLogin(@Valid ThirdLoginToken token, BindingResult result) {

        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString());
        }
        try {
            // 没有手机号登陆
            if (token.getTel() != null) {

                DoctorThird third = this.doctorThirdRepository.findByThirdIdAndThirdType(token.getThirdId(), ThirdBaseInfo.ThirdType.values()[token.getThirdType()]);

                // 找不到第三方账号，第一次用第三方登陆
                if (third == null) {
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("用户不存在");
                } else {
                    // 有第三方账号，返回医师id
                    return new ControllerResult<Long>().setRet_code(0).setRet_values(third.getDoctor().getId());
                }
            } else {
                // 带有手机登陆，创建第三方账号，查看医师是否用手机注册，有就绑定
                if (!ValidateUtils.validateTel(token.getTel())) {
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("关联的手机号格式不正确！");
                }

                Doctor doctor = this.doctorRepository.findByTel(token.getTel());

                // 医师手机注册，创建手机注册
                if (doctor == null) {
                    doctor = new Doctor();
                    doctor.setTel(token.getTel());
                    doctor.setCreatedDate(token.getCreateTime());
                    doctor = this.doctorRepository.save(doctor);
                }

                DoctorThird dThird = new DoctorThird();
                dThird.setDoctor(doctor);
                dThird.setThirdId(token.getThirdId());
                dThird.setThirdType(ThirdBaseInfo.ThirdType.values()[token.getThirdType()]);
                dThird.setCreatedDate(token.getCreateTime());
                dThird = this.doctorThirdRepository.save(dThird);
                return new ControllerResult<Long>().setRet_code(0).setRet_values(doctor.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values(e.getLocalizedMessage());
        }
    }

}
