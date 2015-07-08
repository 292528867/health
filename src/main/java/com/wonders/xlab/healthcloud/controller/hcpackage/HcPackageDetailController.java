package com.wonders.xlab.healthcloud.controller.hcpackage;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.hcpackage.HcPackageDetailDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageDetailRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageRepository;
import com.wonders.xlab.healthcloud.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Jeffrey on 15/7/8.
 */
@RestController
@RequestMapping("task")
public class HcPackageDetailController extends AbstractBaseController<HcPackageDetail, Long>{

    @Autowired
    private HcPackageDetailRepository hcPackageDetailRepository;

    @Autowired
    private HcPackageRepository hcPackageRepository;

    @RequestMapping(value = "addTask")
    public Object addPackageTask(@Valid HcPackageDetailDto hcPackageDetailDto, BindingResult result) {

        HcPackage hcPackage = hcPackageRepository.findOne(Long.valueOf(hcPackageDetailDto.getPackageId()));
        if (null == hcPackage) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("竟然没找到健康包！");
        }

        HcPackageDetail hcPackageDetail = new HcPackageDetail();
        BeanUtils.copyProperties(hcPackageDetailDto, hcPackageDetail, "packageId");
        hcPackageDetail.setHcPackage(hcPackage);
        hcPackageDetailRepository.save(hcPackageDetail);

        return new ControllerResult<>().setRet_code(0).setRet_values("").setMessage("添加成功！");
    }


    @RequestMapping("tasksInPackage/{packageId}")
    public List<HcPackageDetail> findTasksInPackage(@PathVariable Long packageId) {
        return hcPackageDetailRepository.findByHcPackageId(packageId);
    }

    @RequestMapping(value = "modify/{id}", method = {RequestMethod.PUT})
    public HcPackageDetail modify(@PathVariable Long id, @Valid HcPackageDetailDto hcPackageDetailDto) {
        HcPackageDetail hcPackageDetail = hcPackageDetailRepository.findOne(id);
        BeanUtils.copyProperties(hcPackageDetailDto, hcPackageDetail);
        return super.modify(hcPackageDetail);
    }

    @Override
    protected MyRepository<HcPackageDetail, Long> getRepository() {
        return hcPackageDetailRepository;
    }
}
