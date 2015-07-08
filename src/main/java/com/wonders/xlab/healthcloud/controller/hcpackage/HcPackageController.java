package com.wonders.xlab.healthcloud.controller.hcpackage;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.hcpackage.HcPackageDetailDto;
import com.wonders.xlab.healthcloud.dto.hcpackage.HcPackageDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;
import com.wonders.xlab.healthcloud.repository.discovery.HealthCategoryRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageDetailRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageRepository;
import com.wonders.xlab.healthcloud.utils.BeanUtils;
import com.wonders.xlab.healthcloud.utils.QiniuUploadUtils;
import org.apache.commons.codec.net.URLCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
    private HealthCategoryRepository healthCategoryRepository;

    @Override
    protected MyRepository<HcPackage, Long> getRepository() {
        return hcPackageRepository;
    }

    /**
     * 查询健康包
     * @return
     */
    @RequestMapping("listHcPackage")
    private Object listHcPackage(
            @PageableDefault(sort = "recommendValue", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return  new ControllerResult<List<HcPackage>>().setRet_code(0).setRet_values(hcPackageRepository.findAll(pageable).getContent()).setMessage("成功");
    }

    /**
     * 添加健康包
     * @param hcPackageDto
     * @param result
     * @return
     */
    @RequestMapping(value = "addHcPackage/{healthCategoryId}",method = RequestMethod.POST)
    private Object addHcPackage(@PathVariable Long healthCategoryId, @Valid HcPackageDto hcPackageDto, MultipartFile icon,MultipartFile detailDescriptionIcon,BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }
        try {
            String iconUrl = QiniuUploadUtils.upload(icon.getBytes(), URLDecoder.decode(icon.getOriginalFilename(), "UTF-8"));
            String detailDescriptionIconUrl = QiniuUploadUtils.upload(detailDescriptionIcon.getBytes(), URLDecoder.decode(detailDescriptionIcon.getOriginalFilename(), "UTF-8"));

            HealthCategory healthCategory = healthCategoryRepository.findOne(healthCategoryId);

            HcPackage hcPackage = new HcPackage();
            hcPackage.setHealthCategory(healthCategory);
            BeanUtils.copyProperties(hcPackageDto,hcPackage,"healthCategoryId");
            hcPackage.setIcon(iconUrl);
            hcPackage.setDetailDescriptionIcon(detailDescriptionIconUrl);
            hcPackageRepository.save(hcPackage);
            return new ControllerResult<String>().setRet_code(0).setRet_values("").setMessage("添加成功！");
        } catch (UnsupportedEncodingException e) {
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage(e.getLocalizedMessage());
        } catch (IOException e) {
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage(e.getLocalizedMessage());
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
    private Object updateHcPackage(@PathVariable Long hcPackageId, @Valid HcPackageDto hcPackageDto, MultipartFile icon,MultipartFile detailDescriptionIcon, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }
        try {
            HcPackage hcPackage = this.hcPackageRepository.findOne(hcPackageId);
            if (hcPackage == null) {
                return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("竟然没找到！");
            }
            String iconUrl = QiniuUploadUtils.upload(icon.getBytes(), URLDecoder.decode(icon.getOriginalFilename(), "UTF-8"));
            String detailDescriptionIconUrl = QiniuUploadUtils.upload(detailDescriptionIcon.getBytes(), URLDecoder.decode(detailDescriptionIcon.getOriginalFilename(), "UTF-8"));

            BeanUtils.copyProperties(hcPackageDto,hcPackage,"healthCategoryId");
            hcPackage.setIcon(iconUrl);
            hcPackage.setDetailDescriptionIcon(detailDescriptionIconUrl);
            hcPackageRepository.save(hcPackage);
            return new ControllerResult<String>().setRet_code(0).setRet_values("").setMessage("添加成功！");
        } catch (UnsupportedEncodingException e) {
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage(e.getLocalizedMessage());
        } catch (IOException e) {
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage(e.getLocalizedMessage());
        }
    }




}
