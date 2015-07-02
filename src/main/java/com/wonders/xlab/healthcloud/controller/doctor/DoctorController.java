package com.wonders.xlab.healthcloud.controller.doctor;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.IdenCode;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.doctor.Doctor;
import com.wonders.xlab.healthcloud.repository.doctor.DoctorRepository;
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

    @Override
    protected MyRepository<Doctor, Long> getRepository() {
        return doctorRepository;
    }

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
                    Doctor doctor = this.doctorRepository.findByPhone(iden_cached.getPhone());
                    if (doctor == null) { // 如果是新用户，插入记录
                        doctor = new Doctor();
                        doctor.setPhone(iden.getPhone());
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


}
