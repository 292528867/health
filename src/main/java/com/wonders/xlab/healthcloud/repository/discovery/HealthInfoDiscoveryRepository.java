package com.wonders.xlab.healthcloud.repository.discovery;

import java.util.Date;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfoDiscovery;

public interface HealthInfoDiscoveryRepository extends MyRepository<HealthInfoDiscovery, Long> {
	HealthInfoDiscovery findByUserIdAndDiscoveryDate(Long id, Date date);
}
