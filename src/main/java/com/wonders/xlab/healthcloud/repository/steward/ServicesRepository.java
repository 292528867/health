package com.wonders.xlab.healthcloud.repository.steward;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.steward.Services;

import java.util.List;

/**
 * Created by lixuanwu on 15/7/7.
 */
public interface ServicesRepository extends MyRepository<Services, Long> {

    List<Services> findByIsForceOrderByUsedNumberAsc(boolean isForce);

    List<Services> findByServiceId(List<String> ids);
}
