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
import com.wonders.xlab.healthcloud.utils.QiniuUploadUtils;
import com.wonders.xlab.healthcloud.utils.ValidateUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URLDecoder;

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
     * 医师手机登陆
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
            // 判断element是否为空
            Element element = idenCodeCache.get(iden.getTel());
            if (element == null) {
                return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码失效！");
            }
            // 获取验证码
            String iden_code = (String)element.getObjectValue();

            if (iden_code == null) {
                // cache失效罗
                return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码失效！");
            } else {
                if (!iden_code.equals(iden.getCode())) {
                    // 前台输错验证码罗
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码输入错误！");
                } else {
                    Doctor doctor = this.doctorRepository.findByTel(iden.getTel());
                    if (doctor == null) { // 如果是新用户，插入记录
                        doctor = new Doctor();
                        doctor.setTel(iden.getTel());
                        doctor = this.doctorRepository.save(doctor);
                        return new ControllerResult<Doctor>().setRet_code(0).setRet_values(doctor).setMessage("成功");
                    } else {
                        return new ControllerResult<Doctor>().setRet_code(0).setRet_values(doctor).setMessage("成功");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage(e.getLocalizedMessage());
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
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage(builder.toString());
        }
        try {

            DoctorThird third = this.doctorThirdRepository.findByThirdIdAndThirdType(token.getThirdId(), ThirdBaseInfo.ThirdType.values()[Integer.parseInt(token.getThirdType())]);

            // 没有手机号登陆
            if (token.getTel() == null) {


                // 找不到第三方账号，第一次用第三方登陆
                if (third == null) {
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("用户不存在");
                } else {
                    // 有第三方账号，返回医师id
                    return new ControllerResult<Doctor>().setRet_code(0).setRet_values(third.getDoctor()).setMessage("成功");
                }
            } else {

                if (third != null) {
                    // 有第三方账号，返回医师id
                    return new ControllerResult<Doctor>().setRet_code(0).setRet_values(third.getDoctor()).setMessage("成功");
                }

                // 带有手机登陆，创建第三方账号，查看医师是否用手机注册，有就绑定
                if (!ValidateUtils.validateTel(token.getTel())) {
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("关联的手机号格式不正确！");
                }

                // 判断element是否为空
                Element element = idenCodeCache.get(token.getTel());
                if (element == null) {
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码失效！");
                }
                // 获取验证码
                String iden_code = (String)element.getObjectValue();

                if (iden_code == null) {
                    // cache失效罗
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码失效！");
                } else {
                    if (!iden_code.equals(token.getCode())) {
                        // 前台输错验证码罗
                        return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码输入错误！");
                    } else {
                        Doctor doctor = this.doctorRepository.findByTel(token.getTel());

                        // 医师手机注册，创建手机注册
                        if (doctor == null) {
                            doctor = new Doctor();
                            doctor.setTel(token.getTel());
                            doctor = this.doctorRepository.save(doctor);
                        }

                        DoctorThird dThird = new DoctorThird();
                        dThird.setDoctor(doctor);
                        dThird.setThirdId(token.getThirdId());
                        dThird.setThirdType(ThirdBaseInfo.ThirdType.values()[Integer.parseInt(token.getThirdType())]);
                        dThird = this.doctorThirdRepository.save(dThird);
                        return new ControllerResult<Doctor>().setRet_code(0).setRet_values(doctor).setMessage("成功");
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage(e.getLocalizedMessage());
        }
    }

    /**
     * 医师上传图片
     *
     * @param id   医师id
     * @param file 医师图像
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadPic/{id}", method = RequestMethod.POST)
    public Object uploadPic(@PathVariable long id, @RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("图片不存在");
        }

        try {
            Doctor doctor = this.doctorRepository.findOne(id);
            String filename = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");
            String url = QiniuUploadUtils.upload(file.getBytes(), filename);
            doctor.setIconUrl(url);
            this.doctorRepository.save(doctor);
            return new ControllerResult<String>().setRet_code(0).setRet_values(url).setMessage("成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage(e.getLocalizedMessage());
        }
    }

}
