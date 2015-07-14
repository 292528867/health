package com.wonders.xlab.healthcloud.controller.doctor;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.IdenCode;
import com.wonders.xlab.healthcloud.dto.ThirdLoginToken;
import com.wonders.xlab.healthcloud.dto.doctor.DoctorBaseInfoDto;
import com.wonders.xlab.healthcloud.dto.doctor.DoctorQualificationUrlDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.ThirdBaseInfo;
import com.wonders.xlab.healthcloud.entity.doctor.Doctor;
import com.wonders.xlab.healthcloud.entity.doctor.DoctorThird;
import com.wonders.xlab.healthcloud.repository.doctor.DoctorRepository;
import com.wonders.xlab.healthcloud.repository.doctor.DoctorThirdRepository;
import com.wonders.xlab.healthcloud.service.cache.HCCache;
import com.wonders.xlab.healthcloud.service.cache.HCCacheProxy;
import com.wonders.xlab.healthcloud.utils.BeanUtils;
import com.wonders.xlab.healthcloud.utils.EMUtils;
import com.wonders.xlab.healthcloud.utils.QiniuUploadUtils;
import com.wonders.xlab.healthcloud.utils.ValidateUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.management.RuntimeErrorException;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by mars on 15/7/2.
 */
@RestController
@RequestMapping("doctor")
public class DoctorController extends AbstractBaseController<Doctor, Long> {

    private HCCache<String, String> hcCache;

    @Autowired
    @Qualifier("idenCodeCache")
    private Cache idenCodeCache;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorThirdRepository doctorThirdRepository;

    @PostConstruct
    private void init() {
        hcCache = new HCCacheProxy<>(idenCodeCache);
    }

    @Override
    protected MyRepository<Doctor, Long> getRepository() {
        return doctorRepository;
    }

    @Autowired
    private EMUtils emUtils;

    /**
     * 医师手机登陆
     *
     * @param iden
     * @param result
     * @return
     */
    @RequestMapping(value = "hclogin", method = RequestMethod.POST)
    public Object hclogin(@RequestBody @Valid IdenCode iden, BindingResult result) {

        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
                return builder.toString();
            }
        }
        try {
            // 获取指定手机号的验证编码缓存并，比较是否相同
            String cascheValue = hcCache.getFromCache(iden.getTel());

            if (cascheValue == null) {
                // cache失效罗
                return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码失效！");
            } else {
                if (!cascheValue.equals(iden.getCode())) {
                    // 前台输错验证码罗
                    return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("验证码输入错误！");
                } else {
                    Doctor doctor = doctorRepository.findByTel(iden.getTel());
                    return null == doctor ?
                            addDoctorBeforeLogin(iden) :
                            new ControllerResult<Doctor>().setRet_code(0).setRet_values(doctor).setMessage("成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage(e.getLocalizedMessage());
        }
    }

    private Object addDoctorBeforeLogin(IdenCode iden) {
        Doctor doctor = new Doctor();
        doctor.setTel(iden.getTel());
        doctor.setAppPlatform(iden.getAppPlatform());
        doctor = this.doctorRepository.save(doctor);
        if (emUtils.registerEmUser("doctor" + iden.getTel(), iden.getTel())) {
            //TODO 从缓存中获取环信token 医生群组暂时写死
            String groupId = "82830104253694376";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer YWMtEJuECCJLEeWN-d-uaORhJQAAAU-OGpHmVNOp0Va6o2OEAUzNiA1O9UB_oFw");
            headers.setContentType(MediaType.TEXT_PLAIN);
            emUtils.requestEMChat(headers, "post", "chatgroups/" + groupId + "/users/" + "doctor" + iden.getTel(), String.class);

            return new ControllerResult<Doctor>().setRet_code(0).setRet_values(doctor).setMessage("成功");
        }
        return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("注册失败！");
    }

    /**
     * 第三方登陆
     *
     * @param token
     * @param result
     * @return
     */
    @RequestMapping(value = "thirdLogin", method = RequestMethod.POST)
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
                String iden_code = (String) element.getObjectValue();

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

    @RequestMapping(value = "updateBaseInfo/{doctorId}", method = RequestMethod.POST)
    public Object modifyBaseInfo(@PathVariable long doctorId, @RequestBody @Valid DoctorBaseInfoDto doctorBaseInfoDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage(builder.toString());
        }

        Doctor doctor = doctorRepository.findOne(doctorId);
        BeanUtils.copyNotNullProperties(doctorBaseInfoDto, doctor);
        doctor.setValid(Doctor.Valid.valid);
        try {
            doctor = doctorRepository.save(doctor);
            return new ControllerResult<>()
                    .setRet_code(0)
                    .setRet_values(doctor)
                    .setMessage("更新信息成功！");
        } catch (RuntimeErrorException exp) {
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values("")
                    .setMessage("更新信息失败！");
        }
    }

    @RequestMapping(value = "certification/{doctorId}", method = RequestMethod.POST)
    public Object supplementaryQualificationInfo(@PathVariable long doctorId, MultipartFile icon ,MultipartFile iCard, MultipartFile qualification, MultipartFile permit) {
        if (null == iCard || null == qualification || null == permit) {
            StringBuilder builder = new StringBuilder();
            if (null == iCard)
                builder.append("身份证不能为空！");
            if (null == qualification) {
                builder.append("职称证件不能为空！");
            }
            if (null == permit) {
                builder.append("执行认证证件不能为空！");
            }
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values("")
                    .setMessage(builder.toString());
        }

        DoctorQualificationUrlDto doctorQualificationUrlDto;
        try {
            // 上传图片到七牛并创建地址dto
            doctorQualificationUrlDto = new DoctorQualificationUrlDto(
                    uploadQualification(icon),
                    uploadQualification(iCard),
                    uploadQualification(qualification),
                    uploadQualification(permit)
            );
        } catch (IOException e) {
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values("")
                    .setMessage("上传认证失败！");
        }

        Doctor doctor = doctorRepository.findOne(doctorId);
        BeanUtils.copyNotNullProperties(doctorQualificationUrlDto, doctor);
        doctor.setChecked(Doctor.Checked.apply);
        try {
            doctorRepository.save(doctor);
            return new ControllerResult<>()
                    .setRet_code(0)
                    .setRet_values("")
                    .setMessage("上传认证成功！");
        } catch (RuntimeErrorException exp) {
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values("")
                    .setMessage("上传认证失败！");
        }

    }

    @RequestMapping(value = "check/{doctorId}", method = RequestMethod.POST)
    public boolean checkDoctor(@PathVariable long doctorId, Doctor.Checked checked) {
        Doctor doctor = doctorRepository.findOne(doctorId);
        doctor.setChecked(checked);
        try {
            modify(doctor);
            return true;
        } catch (RuntimeErrorException exp) {
            return false;
        }
    }


    private String uploadQualification(MultipartFile file) throws IOException {
        return null == file ? "" :
                QiniuUploadUtils.upload(
                    file.getBytes(),
                    URLDecoder.decode(file.getOriginalFilename(), "UTF-8")
                );
    }

}
