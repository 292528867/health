package com.wonders.xlab.healthcloud.controller.hcpackage;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.hcpackage.HcPackageDetailDto;
import com.wonders.xlab.healthcloud.dto.hcpackage.HcPackageDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageDetailRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageRepository;
import com.wonders.xlab.healthcloud.utils.QiniuUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by mars on 15/7/4.
 */
@RestController
@RequestMapping("hcPackage")
public class HcPackageController extends AbstractBaseController<HcPackage, Long> {

    @Autowired
    private HcPackageRepository hcPackageRepository;

    @Autowired
    private HcPackageDetailRepository hcPackageDetailRepository;

    @Override
    protected MyRepository<HcPackage, Long> getRepository() {
        return hcPackageRepository;
    }

    /**
     * 查询健康包
     * @return
     */
    @RequestMapping("listHcPackage")
    private Object listHcPackage() {
        return  new ControllerResult<List<HcPackage>>().setRet_code(0).setRet_values(hcPackageRepository.findAll()).setMessage("成功");
    }

    /**
     * 添加健康包
     * @param hcPackageDto
     * @param result
     * @return
     */
    @RequestMapping("addHcPackage")
    private Object addHcPackage(@RequestBody @Valid HcPackageDto hcPackageDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }
        try {
            this.hcPackageRepository.save(hcPackageDto.toNewHcPackage());
            return new ControllerResult<String>().setRet_code(0).setRet_values("添加成功").setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values(exp.getLocalizedMessage()).setMessage("失败");
        }
    }

    /**
     * 更新健康包
     * @param hcPackageId
     * @param hcPackageDto
     * @param result
     * @return
     */
    @RequestMapping("updateHcPackage/{hcPackageId}")
    private Object updateHcPackage(@PathVariable Long hcPackageId, @RequestBody @Valid HcPackageDto hcPackageDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }
        try {
            HcPackage hp = this.hcPackageRepository.findOne(hcPackageId);
            if (hp == null) {
                return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没找到！").setMessage("竟然没找到！");
            }
            this.hcPackageRepository.save(hcPackageDto.updateHcPackage(hp));
            return new ControllerResult<String>().setRet_code(0).setRet_values("更新成功！").setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values(exp.getLocalizedMessage()).setMessage("失败");
        }
    }


    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping(value = "uploadPicture", method = RequestMethod.POST)
    public ControllerResult<?> uploadPic(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = "hcpackage-icon-" + String.valueOf((new Date()).getTime());
                String iconUrl = QiniuUploadUtils.upload(file.getBytes(), fileName);
                return new ControllerResult<String>().setRet_code(0).setRet_values(iconUrl).setMessage("上传图片成功！");
            } catch (IOException exp) {
                exp.printStackTrace();
                return new ControllerResult<String>().setRet_code(-1).setRet_values("上传图片失败：" + exp.getLocalizedMessage()).setMessage("失败！");
            }
        } else {
            return new ControllerResult<String>().setRet_code(-1).setRet_values("上传文件为空！").setMessage("上传文件为空！");
        }
    }


    /**
     * 添加健康包详细
     * @param hcPackageId
     * @param hcPackageDetailDto
     * @param result
     * @return
     */
    @RequestMapping("addHcPackageDetail/{hcPackageId}")
    private Object addHcPackageDetail(@PathVariable Long hcPackageId, @RequestBody @Valid HcPackageDetailDto hcPackageDetailDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }
        try {
            HcPackage hp = this.hcPackageRepository.findOne(hcPackageId);
            if (hp == null)
                return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没找到！").setMessage("竟然没找到！");
            this.hcPackageDetailRepository.save(hcPackageDetailDto.toNewHcPackageDetail(hp));
            return new ControllerResult<String>().setRet_code(0).setRet_values("添加成功").setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values(exp.getLocalizedMessage()).setMessage("失败");
        }
    }

    /**
     * 更新健康包详细
     * @param detailId
     * @param hcPackageDetailDto
     * @param result
     * @return
     */
    @RequestMapping("updateHcPackageDetail/{detailId}")
    private Object updateHcPackageDetail(@PathVariable Long detailId, @RequestBody @Valid HcPackageDetailDto hcPackageDetailDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }
        try {
            HcPackageDetail hpd = this.hcPackageDetailRepository.findOne(detailId);
            if (hpd == null)
                return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没找到！").setMessage("竟然没找到！");
            this.hcPackageDetailRepository.save(hcPackageDetailDto.updateHcPackageDetail(hpd));
            return new ControllerResult<String>().setRet_code(0).setRet_values("更新成功").setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values(exp.getLocalizedMessage()).setMessage("失败");
        }
    }

}
