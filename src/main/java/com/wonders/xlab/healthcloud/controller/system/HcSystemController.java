package com.wonders.xlab.healthcloud.controller.system;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.system.HcSystem;
import com.wonders.xlab.healthcloud.repository.system.HcSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lixuanwu on 15/7/14.
 */
@RestController
@RequestMapping(value = "system")
public class HcSystemController extends AbstractBaseController<HcSystem,Long> {


    @Autowired
    private HcSystemRepository hcSystemRepository;

    @Override
    protected MyRepository<HcSystem, Long> getRepository() {
        return hcSystemRepository;
    }

    @RequestMapping(value = "findSystemInfo" , method = RequestMethod.GET)
    public Object findSystemInfo() {

        return  new ControllerResult<List<HcSystem>>().setRet_code(0).setRet_values(hcSystemRepository.findAll()).setMessage("查询成功");
    }
}
