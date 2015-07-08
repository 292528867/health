package com.wonders.xlab.healthcloud.repository.discovery;

import java.util.Date;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategoryDiscovery;

public interface HealthCategoryDiscoveryRepository extends MyRepository<HealthCategoryDiscovery, Long> {
	HealthCategoryDiscovery findByUserIdAndDiscoveryDate(Long id, Date date);
}
