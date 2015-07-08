package com.wonders.xlab.healthcloud.controller.steward;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.steward.Services;
import com.wonders.xlab.healthcloud.repository.steward.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lixuanwu on 15/7/8.
 */
@RestController
@RequestMapping(value = "services")
public class ServicesController extends AbstractBaseController<Services, Long> {

    @Autowired
    private ServicesRepository servicesRepository;

    @Override
    protected MyRepository<Services, Long> getRepository() {
        return servicesRepository;
    }

    @RequestMapping(value = "saveService", method = RequestMethod.POST)
    public ControllerResult saveService(@RequestBody Services services) {

        //初始为5～20的岁数
        services.setUsedNumber((int)(5+Math.random()*(20-5+1)));
        servicesRepository.save(services);

        return new ControllerResult<>().setRet_code(0).setRet_values("").setMessage("成功！");
    }
}
