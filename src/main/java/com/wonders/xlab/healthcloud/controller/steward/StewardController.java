package com.wonders.xlab.healthcloud.controller.steward;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.dto.steward.ServiceDto;
import com.wonders.xlab.healthcloud.entity.steward.RecommendPackage;
import com.wonders.xlab.healthcloud.entity.steward.Services;
import com.wonders.xlab.healthcloud.entity.steward.Steward;
import com.wonders.xlab.healthcloud.repository.steward.RecommendPackageRepository;
import com.wonders.xlab.healthcloud.repository.steward.ServicesRepository;
import com.wonders.xlab.healthcloud.repository.steward.StewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by lixuanwu on 15/7/7.
 */
@RestController
@RequestMapping(name = "steward")
public class StewardController extends AbstractBaseController<Steward, Long> {

    @Autowired
    private StewardRepository stewardRepository;

    @Autowired
    private RecommendPackageRepository recommendPackageRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Override
    protected MyRepository<Steward, Long> getRepository() {
        return stewardRepository;
    }

    /**
     * 获取所有的任务包
     *
     * @return
     */
    @RequestMapping(value = "getAllRecommendPackage", method = RequestMethod.GET)
    public Object getAllRecommendPackage() {

        return new ControllerResult<List<RecommendPackage>>().setRet_code(0).setRet_values(this.recommendPackageRepository.findAll()).setMessage("成功！");
    }

    /**
     * 获取推荐包详情
     * @param packageId
     * @return
     */
    @RequestMapping("getRecommendPackageDetail/{packageId}")
    public Object getRecommendPackageDetail(@PathVariable Long packageId){

        return new ControllerResult<RecommendPackage>().setRet_code(0).setRet_values(this.recommendPackageRepository.findOne(packageId)).setMessage("成功！");
    }

    /**
     * 获取自定义包
     * @return
     */
    @RequestMapping("listCustomPackage")
    public Object listCustomPackage() {

        List<RecommendPackage> recommendPackages = this.recommendPackageRepository.findAll();
        List<Steward> stewards = this.stewardRepository.findAll();
        Map<String, Object> map = new HashMap<>();
        map.put("package", recommendPackages);
        map.put("steward", stewards);
        return new ControllerResult<>().setRet_code(0).setRet_values(map).setMessage("成功");
    }

    /**
     * 查看服务详情
     * @param serviceId
     * @return
     */
    @RequestMapping("serviceDetail/{serviceId}")
    public Object serviceDetail(@PathVariable Long serviceId) {

        return new ControllerResult<Services>().setRet_code(0).setRet_values(this.servicesRepository.findOne(serviceId)).setMessage("成功！");

    }

    /**
     * 查询管家详情
     * @param stewardId
     * @return
     */
    @RequestMapping("stewardDetail/{stewardId}")
    public Object stewardDetail(@PathVariable Long stewardId) {
        return new ControllerResult<Steward>().setRet_code(0).setRet_values(this.stewardRepository.findOne(stewardId)).setMessage("成功！");

    }

    /**
     * 计算服务费用
     * @param serviceDto
     * @param result
     * @return
     */
    @RequestMapping("calculateServiceAmount")
    public Object calculateServiceAmount(@RequestBody @Valid ServiceDto serviceDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }
        try {
            double amount = 0;
            // 存在推荐包
            if (serviceDto.getPackageId() != null) {
                // 获取推荐包
                RecommendPackage rp = this.recommendPackageRepository.findOne(Long.parseLong(serviceDto.getPackageId()));
                amount = Double.parseDouble(rp.getPrice());
                Steward steward = this.stewardRepository.findOne(Long.parseLong(serviceDto.getStewardId()));
//                amount += steward.get

                String strSeviceIds = this.recommendPackageRepository.findOne(Long.parseLong(serviceDto.getPackageId())).getServiceIds();
                String[] serviceIds = strSeviceIds.split(",");
                Long[] longServiceIds = new Long[serviceIds.length];
                for (int i = 0; i < serviceIds.length; i++)
                    longServiceIds[i] = Long.parseLong(serviceIds[i]);
                List<Services> packageServices = this.servicesRepository.findAll(Arrays.asList(longServiceIds));

            }
            String[] strIds = serviceDto.getServiceIds().split(",");
            Long[] serviceIds = new Long[strIds.length];
            for (int i = 0; i< strIds.length; i++)
                serviceIds[i] = Long.parseLong(strIds[i]);
            List<Services> services = this.servicesRepository.findAll(Arrays.asList(serviceIds));
            Steward steward = this.stewardRepository.findOne(Long.parseLong(serviceDto.getStewardId()));
            for (Services service : services)
                amount += service.getServiceIntegration();
            amount += steward.getStewardIntegration();

            int cash = 0;
            if (amount >= 0 && amount <= 4) {
                cash = 0;
            } else if (amount >= 5 && amount <= 10) {
                cash = 28;
            } else if (amount >= 11 && amount <= 17) {
                cash = 78;
            } else if (amount >= 18 && amount <= 48) {
                cash = 158;
            }

            Map<String, Object> map = new HashMap<>();

            return new ControllerResult<>().setRet_code(0).setRet_values(map).setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values(exp.getLocalizedMessage()).setMessage("失败");
        }

    }


}
