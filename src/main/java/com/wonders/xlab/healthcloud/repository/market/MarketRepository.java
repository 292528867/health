package com.wonders.xlab.healthcloud.repository.market;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.market.Market;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by mars on 15/7/11.
 */
public interface MarketRepository extends MyRepository<Market, Long> {

    @Query("from Market m order by m.lastModifiedDate desc")
    List<Market> findOrderByLastModifiedDateDesc();
}
