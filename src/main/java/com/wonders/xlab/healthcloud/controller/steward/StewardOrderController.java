package com.wonders.xlab.healthcloud.controller.steward;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.steward.StewardOrder;
import com.wonders.xlab.healthcloud.repository.steward.StewardOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lixuanwu on 15/7/12.
 */
@RestController
@RequestMapping(value = "stewardOrder")
public class StewardOrderController extends AbstractBaseController<StewardOrder, Long> {

    @Autowired
    private StewardOrderRepository stewardOrderRepository;

    @Override
    protected MyRepository<StewardOrder, Long> getRepository() {
        return stewardOrderRepository;
    }

    @RequestMapping(value = "getAllStewardOrder",method = RequestMethod.GET)
    public List<StewardOrder> getAllStewardOrder(){

        return stewardOrderRepository.findAllStewardOrder();
    }


}
