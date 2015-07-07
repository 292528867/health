package com.wonders.xlab.healthcloud.controller.steward;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.pingpp.PingDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.dto.steward.ServiceDto;
import com.wonders.xlab.healthcloud.entity.steward.RecommendPackage;
import com.wonders.xlab.healthcloud.entity.steward.Services;
import com.wonders.xlab.healthcloud.entity.steward.Steward;
import com.wonders.xlab.healthcloud.repository.steward.OrderRepository;
import com.wonders.xlab.healthcloud.repository.steward.RecommendPackageRepository;
import com.wonders.xlab.healthcloud.repository.steward.ServicesRepository;
import com.wonders.xlab.healthcloud.repository.steward.StewardRepository;
import com.wonders.xlab.healthcloud.service.pingpp.PingppService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by lixuanwu on 15/7/7.
 */
@RestController
@RequestMapping(value = "steward")
public class StewardController extends AbstractBaseController<Steward, Long> {

    @Autowired
    private StewardRepository stewardRepository;

    @Autowired
    private RecommendPackageRepository recommendPackageRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PingppService pingppService;
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

//        List<RecommendPackage> recommendPackages = this.recommendPackageRepository.findAll();
//        for (RecommendPackage rp : recommendPackages) {
//            if (!StringUtils.isEmpty(rp.getServiceIds())) {
//                String[] strIds = rp.getServiceIds().split(",");
//                Long[] serviceIds = new Long[strIds.length];
//                for (int i = 0; i< strIds.length; i++)
//                    serviceIds[i] = Long.parseLong(strIds[i]);
//                // 查询服务，管家
//                List<Services> services = this.servicesRepository.findAll(Arrays.asList(serviceIds));
//                Set<Services> servicesSet = new HashSet<>();
//                servicesSet.addAll(services);
//                rp.setServices(servicesSet);
//
//                // TODO:管家
//                Steward steward = this.stewardRepository.findOne(1l);
//                rp.setSteward(steward);
//            }
//        }

        return new ControllerResult<List<RecommendPackage>>().setRet_code(0).setRet_values(this.recommendPackageRepository.findAll()).setMessage("成功！");
    }

    /**
     * 获取推荐包详情
     * @param packageId
     * @return
     */
    @RequestMapping("getRecommendPackageDetail/{packageId}")
    public Object getRecommendPackageDetail(@PathVariable Long packageId){

        RecommendPackage rp = this.recommendPackageRepository.findOne(packageId);

        if (!StringUtils.isEmpty(rp.getServiceIds())) {
            String[] strIds = rp.getServiceIds().split(",");
            Long[] serviceIds = new Long[strIds.length];
            for (int i = 0; i < strIds.length; i++)
                serviceIds[i] = Long.parseLong(strIds[i]);
            // 查询服务，管家
            List<Services> services = this.servicesRepository.findAll(Arrays.asList(serviceIds));
            Set<Services> servicesSet = new HashSet<>();
            servicesSet.addAll(services);
            rp.setServices(servicesSet);

            // TODO:管家
            Steward steward = this.stewardRepository.findOne(1l);
            rp.setSteward(steward);
        }

        return new ControllerResult<RecommendPackage>().setRet_code(0).setRet_values(rp).setMessage("成功！");

    }

    /**
     * 获取自定义包
     * @return
     */
    @RequestMapping("listCustomPackage")
    public Object listCustomPackage() {

        List<Services> services = this.servicesRepository.findAll();
        List<Steward> stewards = this.stewardRepository.findAll();
        Map<String, Object> map = new HashMap<>();
        map.put("services", services);
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
    @RequestMapping("payServices/{userId}")
    public void payServices(@PathVariable Long userId, @RequestBody @Valid ServiceDto serviceDto, BindingResult result,
                              HttpServletRequest req, HttpServletResponse resp) {

        PrintWriter out;
        resp.setContentType("application/json; charset=utf-8");
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            try {
                out = resp.getWriter();
                out.print(new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败"));
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            int integration = 0;
            int amount = 0;

            String[] strIds = serviceDto.getServiceIds().split(",");
            Long[] serviceIds = new Long[strIds.length];
            for (int i = 0; i< strIds.length; i++)
                serviceIds[i] = Long.parseLong(strIds[i]);
            // 查询服务，管家
            List<Services> services = this.servicesRepository.findAll(Arrays.asList(serviceIds));
            Steward steward = this.stewardRepository.findOne(Long.parseLong(serviceDto.getStewardId()));
            // 计算积分
            for (Services service : services)
                integration += service.getServiceIntegration();
            integration += steward.getStewardIntegration();

            // 存在推荐包
            if (serviceDto.getPackageId() != null) {
                // 获取推荐包，管家
                RecommendPackage rp = this.recommendPackageRepository.findOne(Long.parseLong(serviceDto.getPackageId()));

                amount = Integer.parseInt(rp.getPrice());
//                return new ControllerResult<Map<String, Object>>().setRet_code(0).setRet_values(map).setMessage("成功！");

            }  else {
                // 判断自定义积分换算金额
                if (integration >= 0 && integration <= 4) {
                    amount = 0;
                } else if (integration >= 5 && integration <= 10) {
                    amount = 28;
                } else if (integration >= 11 && integration <= 17) {
                    amount = 78;
                } else if (integration >= 18 && integration <= 48) {
                    amount = 158;
                }
            }

            PingDto pingDto = new PingDto("健康套餐", "健康云养生套餐", String.valueOf(amount));

            pingppService.payOrder(userId, pingDto, result, req, resp);
        } catch (Exception exp) {
            exp.printStackTrace();
            try {
                out = resp.getWriter();
                out.print( new ControllerResult<String>().setRet_code(-1).setRet_values(exp.getLocalizedMessage()).setMessage("失败"));
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}
