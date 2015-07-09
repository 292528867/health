package com.wonders.xlab.healthcloud.controller.hcpackage;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.hcpackage.HcPackageDetailDto;
import com.wonders.xlab.healthcloud.dto.hcpackage.HcPackageDto;
import com.wonders.xlab.healthcloud.dto.hcpackage.ThirdPackageDto;
import com.wonders.xlab.healthcloud.dto.hcpackage.UserPackageOrderDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import com.wonders.xlab.healthcloud.repository.discovery.HealthCategoryRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageDetailRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageOrderRepository;
import com.wonders.xlab.healthcloud.utils.BeanUtils;
import com.wonders.xlab.healthcloud.utils.QiniuUploadUtils;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.*;

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

    @Autowired
    private HcPackageDetailRepository hcPackageDetailRepository;

    @Autowired
    private UserPackageOrderRepository userPackageOrderRepository;

    @Override
    protected MyRepository<HcPackage, Long> getRepository() {
        return hcPackageRepository;
    }

    /**
     * 查询健康包
     *
     * @return
     */
    @RequestMapping("listHcPackage")
    private Object listHcPackage(
            @PageableDefault(sort = "recommendValue", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return new ControllerResult<List<HcPackage>>().setRet_code(0).setRet_values(hcPackageRepository.findAll(pageable).getContent()).setMessage("成功");
    }

    /**
     * 添加健康包
     *
     * @param hcPackageDto
     * @param result
     * @return
     */
    @RequestMapping(value = "addHcPackage/{healthCategoryId}", method = RequestMethod.POST)
    private Object addHcPackage(@RequestBody HcPackageDto hcPackageDto, @PathVariable Long healthCategoryId, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }
        try {
//            String iconUrl = QiniuUploadUtils.upload(icon.getBytes(), URLDecoder.decode(icon.getOriginalFilename(), "UTF-8"));
//            String detailDescriptionIconUrl = QiniuUploadUtils.upload(detailDescriptionIcon.getBytes(), URLDecoder.decode(detailDescriptionIcon.getOriginalFilename(), "UTF-8"));

            HealthCategory healthCategory = healthCategoryRepository.findOne(healthCategoryId);

            HcPackage hcPackage = new HcPackage();
            hcPackage.setHealthCategory(healthCategory);
            BeanUtils.copyProperties(hcPackageDto, hcPackage, "healthCategoryId");
            hcPackage.setIcon(hcPackageDto.getIconUrl());
//            hcPackage.setDetailDescriptionIcon(detailDescriptionIconUrl);
            hcPackageRepository.save(hcPackage);
            return new ControllerResult<String>().setRet_code(0).setRet_values("").setMessage("添加成功！");
        } catch (Exception e) {
            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage(e.getLocalizedMessage());
        }
    }

    /**
     * 更新健康包
     *
     * @param hcPackageId
     * @param hcPackageDto
     * @param result
     * @return
     */
    @RequestMapping("updateHcPackage/{hcPackageId}")
    private Object updateHcPackage(@PathVariable Long hcPackageId, @Valid HcPackageDto hcPackageDto, MultipartFile icon, MultipartFile detailDescriptionIcon, BindingResult result) {
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

            BeanUtils.copyProperties(hcPackageDto, hcPackage, "healthCategoryId");
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
     * 添加健康包详细
     *
     * @param hcPackageId
     * @param hcPackageDetailDto
     * @param result
     * @return
     */
    @RequestMapping("addHcPackageDetail/{hcPackageId}")
    private Object addHcPackageDetail(@PathVariable Long hcPackageId, @Valid HcPackageDetailDto hcPackageDetailDto, BindingResult result) {
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
     * 查询健康包
     *
     * @return
     */
    @RequestMapping("listPackageInfo")
    public Object listPackageInfo() {
        // 查询所有健康包
//        List<HcPackage> hcPackages = this.hcPackageRepository.findAllOrderByCreateDate();
        List<HealthCategory> healthCategories = healthCategoryRepository.findAll();
        List<ThirdPackageDto> thirdPackageDtos = new ArrayList<>();

        for (HealthCategory healthCategorie : healthCategories) {
            thirdPackageDtos.add(new ThirdPackageDto(
                    healthCategorie.getId(),
                    healthCategorie.getTitle(),
                    healthCategorie.getIcon()
            ));
        }
        return new ControllerResult<List<ThirdPackageDto>>().setRet_code(0).setRet_values(thirdPackageDtos).setMessage("成功");
    }

    /**
     * 查询计划包
     *
     * @param categoryId
     * @return
     */
    @RequestMapping("listPackage/{categoryId}/{userId}")
    public Object listPackageInfoByCategoryId(@PathVariable long categoryId, @PathVariable long userId) {

        List<HcPackage> hcPackages = hcPackageRepository.findByHealthCategoryId(categoryId);
        List<UserPackageOrder> orders = userPackageOrderRepository.findByUserId(userId);
        List<UserPackageOrderDto> userPackageOrderDtos = new ArrayList<>();

        for (HcPackage hcPackage : hcPackages) {
            UserPackageOrderDto userPackageOrderDto = new UserPackageOrderDto();
            BeanUtils.copyProperties(hcPackage, userPackageOrderDto);
            userPackageOrderDto.setId(hcPackage.getId());
            userPackageOrderDto.setIsJoin(false);

            if (orders != null && orders.size() != 0) {
                for (UserPackageOrder userPackageOrder : orders) {
                    if (userPackageOrder.getHcPackage().getId() == hcPackage.getId()) {
                        userPackageOrderDto.setIsJoin(true);
                        break;
                    }
                }
            }
            userPackageOrderDtos.add(userPackageOrderDto);
        }

        return new ControllerResult<List<UserPackageOrderDto>>().setRet_code(0).setRet_values(userPackageOrderDtos).setMessage("成功");
    }

    @RequestMapping("packageClick/{packageId}")
    public Object packageClick(@PathVariable Long packageId) {
        try {
            HcPackage hcPackage = hcPackageRepository.findOne(packageId);
            int clickCount = hcPackage.getClickAmount() + 1;
            hcPackage.setClickAmount(clickCount);
            hcPackageRepository.save(hcPackage);
            return new ControllerResult<>().setRet_code(0).setRet_values("").setMessage("关注成功！");
        } catch (Exception exp) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("关注失败！");
        }

    }

    @RequestMapping("checked/{userId}")
    Object checkedPackege(@PathVariable Long userId) {
        try {
            List<UserPackageOrder> userPackageOrders = userPackageOrderRepository.findByUserIdAndPackageCompleteFalse(userId);
            List<HcPackage> hcPackages = new ArrayList<>();
            for (UserPackageOrder userPackageOrder : userPackageOrders) {
                HcPackage hcPackage = new HcPackage();
                BeanUtils.copyProperties(userPackageOrder.getHcPackage(), hcPackage);
                hcPackage.setId(userPackageOrder.getHcPackage().getId());
                hcPackages.add(hcPackage);
            }
            return new ControllerResult<>().setRet_code(0).setRet_values(hcPackages).setMessage("订阅计划列表获取成功！");
        } catch (Exception exp) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("订阅计划列表获取失败！");
        }
    }


}
