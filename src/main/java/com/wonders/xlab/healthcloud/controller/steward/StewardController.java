package com.wonders.xlab.healthcloud.controller.steward;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.steward.RecommendPackage;
import com.wonders.xlab.healthcloud.entity.steward.Services;
import com.wonders.xlab.healthcloud.entity.steward.Steward;
import com.wonders.xlab.healthcloud.repository.steward.RecommendPackageRepository;
import com.wonders.xlab.healthcloud.repository.steward.ServicesRepository;
import com.wonders.xlab.healthcloud.repository.steward.StewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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


}
