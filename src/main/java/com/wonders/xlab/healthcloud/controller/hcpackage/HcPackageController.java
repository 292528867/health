package com.wonders.xlab.healthcloud.controller.hcpackage;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.hcpackage.HcPackageDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by mars on 15/7/4.
 */
@RestController
@RequestMapping("hcPackage")
public class HcPackageController extends AbstractBaseController<HcPackage, Long> {

    @Autowired
    private HcPackageRepository hcPackageRepository;

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
            return new ControllerResult<String>().setRet_code(0).setRet_values("新增成功").setMessage("成功");

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
            return new ControllerResult<String>().setRet_code(0).setRet_values("更新成功！").setRet_values("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values(exp.getLocalizedMessage()).setMessage("失败");
        }
    }

}
