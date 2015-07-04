package com.wonders.xlab.healthcloud.repository.discovery;

import java.util.List;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;

public interface HealthInfoRepository extends MyRepository<HealthInfo, Long> {
	List<HealthInfo> findByHealthCategoryId(Long id);
	HealthInfo findByHealthCategoryIdAndId(Long caid, Long infoid);
}
