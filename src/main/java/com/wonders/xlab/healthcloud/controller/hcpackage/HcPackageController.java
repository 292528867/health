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
import com.wonders.xlab.healthcloud.repository.hcpackage.ClassificationRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageDetailRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageOrderRepository;
import com.wonders.xlab.healthcloud.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ClassificationRepository classificationRepository;

    @Override
    protected MyRepository<HcPackage, Long> getRepository() {
        return hcPackageRepository;
    }

    /**
     * 查询健康包
     *
     * @return
     */
    @RequestMapping(value = "listHcPackage", method = RequestMethod.GET)
    public Object listHcPackage() {
        Sort sort = new Sort(Sort.Direction.DESC, "recommendValue");
        return new ControllerResult<List<HcPackage>>()
                .setRet_code(0).setRet_values(hcPackageRepository.findAll(sort))
                .setMessage("成功");
    }

    /**
     * 添加健康包
     *
     * @param hcPackageDto
     * @param result
     * @return
     */
    @RequestMapping(value = "addHcPackage/{healthCategoryId}", method = RequestMethod.POST)
    public Object addHcPackage(@RequestBody HcPackageDto hcPackageDto, @PathVariable Long healthCategoryId, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }

        HealthCategory healthCategory = healthCategoryRepository.findOne(healthCategoryId);

        HcPackage hcPackage = new HcPackage();
        int value = (int) (10 + Math.random() * 40);
        hcPackage.setHealthCategory(healthCategory);
        hcPackage.setCoefficient(value);
        BeanUtils.copyProperties(hcPackageDto, hcPackage, "healthCategoryId");
        hcPackage.setIcon(hcPackageDto.getIcon());
        hcPackageRepository.save(hcPackage);
        return new ControllerResult<String>().setRet_code(0).setRet_values("").setMessage("添加成功！");
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
    public Object updateHcPackage(@PathVariable Long hcPackageId, @RequestBody HcPackageDto hcPackageDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>()
                    .setRet_code(-1)
                    .setRet_values(builder.toString())
                    .setMessage("失败");
        }
        HcPackage hcPackage = hcPackageRepository.findOne(hcPackageId);
        if (hcPackage == null) {
            return new ControllerResult<String>()
                    .setRet_code(-1)
                    .setRet_values("")
                    .setMessage("竟然没找到！");
        }
        BeanUtils.copyProperties(hcPackageDto, hcPackage, "healthCategoryId");
        hcPackageRepository.save(hcPackage);
        return new ControllerResult<String>().setRet_code(0)
                .setRet_values("")
                .setMessage("添加成功！");
    }

    /**
     * 添加健康包详细
     *
     * @param hcPackageId
     * @param hcPackageDetailDto
     * @param result
     * @return
     */
    @RequestMapping(value = "addHcPackageDetail/{hcPackageId}", method = RequestMethod.POST)
    public Object addHcPackageDetail(@PathVariable Long hcPackageId, @Valid HcPackageDetailDto hcPackageDetailDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }
        HcPackage hp = this.hcPackageRepository.findOne(hcPackageId);
        if (hp == null) {
            return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没找到！").setMessage("竟然没找到！");
        }
        hcPackageDetailRepository.save(hcPackageDetailDto.toNewHcPackageDetail(hp));
        return new ControllerResult<String>()
                .setRet_code(0)
                .setRet_values("添加成功")
                .setMessage("成功");
    }


    /**
     * 查询计划分类
     *
     * @return
     */
    @RequestMapping(value = "listPackageInfo", method = RequestMethod.GET)
    public Object listPackageInfo() {
        // 查询所有分类（二大类）
        List<ThirdPackageDto> thirdPackageDtos = classificationRepository.findOrderByCountPackage();
        return new ControllerResult<List<ThirdPackageDto>>()
                .setRet_code(0)
                .setRet_values(thirdPackageDtos)
                .setMessage("成功");
    }

    /**
     * 根据二大类跳过三大类获取所有健康包
     *
     * @param classificationId
     * @return
     */
    @RequestMapping(value = "listPackage/{classificationId}/{userId}", method = RequestMethod.GET)
    public Object listPackageInfoByCategoryId(@PathVariable long classificationId, @PathVariable long userId) {

        List<HcPackage> hcPackages = hcPackageRepository.findByClassificationId(classificationId);
        List<UserPackageOrder> orders = userPackageOrderRepository.findByUserIdAndPackageCompleteFalse(userId);
        List<UserPackageOrderDto> userPackageOrderDtos = new ArrayList<>();

        for (HcPackage hcPackage : hcPackages) {
            UserPackageOrderDto userPackageOrderDto = new UserPackageOrderDto();
            Integer countTask = null == hcPackage.getHcPackageDetails() ? 0 : hcPackage.getHcPackageDetails().size();
            BeanUtils.copyProperties(hcPackage, userPackageOrderDto);
            userPackageOrderDto.setId(hcPackage.getId());
            userPackageOrderDto.setCountTask(countTask > 0 ? countTask : 0);
            userPackageOrderDto.setIsJoin(false);

            if (orders != null && orders.size() != 0) {
                for (UserPackageOrder userPackageOrder : orders) {
                    //判断用户是否已经加入计划并在返回内容打上加入标记
                    if (userPackageOrder.getHcPackage().getId() == hcPackage.getId()) {
                        userPackageOrderDto.setIsJoin(true);
                        break;
                    }
                }
            }
            userPackageOrderDtos.add(userPackageOrderDto);
        }

        int packageSize = userPackageOrderRepository.countByUserIdAndPackageCompleteFalse(userId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userPackageSize", packageSize);
        resultMap.put("packages", userPackageOrderDtos);
        return new ControllerResult<>()
                .setRet_code(0)
                .setRet_values(resultMap)
                .setMessage("成功");
    }

    /**
     * 首页用户注册默认健康包列表
     *
     * @return
     */
    @RequestMapping("homePackages")
    public Object homePackages() {
        List<HcPackage> hcPackages = hcPackageRepository.findTop4ByOrderByRecommendValueDesc();
        List<UserPackageOrderDto> userPackageOrderDtos = new ArrayList<>();
        for (HcPackage hcPackage : hcPackages) {
            UserPackageOrderDto userPackageOrderDto = new UserPackageOrderDto();
            BeanUtils.copyProperties(hcPackage, userPackageOrderDto);
            userPackageOrderDto.setId(hcPackage.getId());
            userPackageOrderDto.setIsJoin(false);
            userPackageOrderDtos.add(userPackageOrderDto);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userPackageSize", 0);
        resultMap.put("packages", userPackageOrderDtos);
        return new ControllerResult<>()
                .setRet_code(0)
                .setRet_values(resultMap)
                .setMessage("成功");
    }

    /**
     * 根据ID查询包信息，以及category信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "findOnePackage/{id}", method = RequestMethod.GET)
    public HcPackage findOnePackage(@PathVariable Long id) {
        HcPackage hcPackage = hcPackageRepository.findOnePackage(id);
        return hcPackage;
    }

    @RequestMapping("packageClick/{packageId}")
    public Object packageClick(@PathVariable Long packageId) {
        HcPackage hcPackage = hcPackageRepository.findOne(packageId);
        int clickCount = hcPackage.getClickAmount() + 1;
        hcPackage.setClickAmount(clickCount);
        hcPackageRepository.save(hcPackage);
        return new ControllerResult<>()
                .setRet_code(0)
                .setRet_values("")
                .setMessage("关注成功！");
    }

    /**
     * 获取用户已订阅健康计划包
     *
     * @param userId
     * @return
     */
    @RequestMapping("checked/{userId}")
    public Object checkedPackege(@PathVariable Long userId) {
        List<UserPackageOrder> userPackageOrders = userPackageOrderRepository.findByUserIdAndPackageCompleteFalse(userId);
        List<UserPackageOrderDto> userPackageOrderDtos = new ArrayList<>();
        for (UserPackageOrder userPackageOrder : userPackageOrders) {
            long packageId = userPackageOrder.getHcPackage().getId();
            UserPackageOrderDto userPackageOrderDto = new UserPackageOrderDto();
            userPackageOrderDto.setId(packageId);
            BeanUtils.copyProperties(userPackageOrder.getHcPackage(), userPackageOrderDto);
            userPackageOrderDto.setIsJoin(true);
            userPackageOrderDto.setCountTask(
                    hcPackageDetailRepository.countByHcPackageId(packageId)
            );
            userPackageOrderDtos.add(userPackageOrderDto);
        }
        int packageSize = userPackageOrderRepository.countByUserIdAndPackageCompleteFalse(userId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userPackageSize", packageSize);
        resultMap.put("packages", userPackageOrderDtos);
        return new ControllerResult<>()
                .setRet_code(0)
                .setRet_values(resultMap)
                .setMessage("订阅计划列表获取成功！");
    }

    @RequestMapping(value = "findPackagesByCategoryId/{categoryId}", method = RequestMethod.GET)
    public ControllerResult findPackagesByCategoryId(@PathVariable Long categoryId) {
        List<HcPackage> packageList = hcPackageRepository.findByCategoryId(categoryId);
        if (CollectionUtils.isNotEmpty(packageList)) {
            return new ControllerResult<>()
                    .setRet_code(0)
                    .setRet_values(packageList)
                    .setMessage("成功");
        } else {
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values(null)
                    .setMessage("未获取到数据");
        }
    }


}
