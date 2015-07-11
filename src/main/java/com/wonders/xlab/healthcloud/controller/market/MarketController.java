package com.wonders.xlab.healthcloud.controller.market;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.market.Market;
import com.wonders.xlab.healthcloud.repository.market.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mars on 15/7/11.
 */
@RestController
@RequestMapping("market")
public class MarketController extends AbstractBaseController<Market, Long> {

    @Autowired
    private MarketRepository marketRepository;

    @Override
    protected MyRepository<Market, Long> getRepository() {
        return marketRepository;
    }



}
