package com.wonders.xlab.healthcloud.repository.discovery;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfoClickInfo;

public interface HealthInfoClickInfoRepository extends MyRepository<HealthInfoClickInfo, Long> {
	HealthInfoClickInfo findByUserIdAndClickDateAndHealthInfoId(Long userId, Date clickDate, Long healthInfoId);
	
	@Query("select sum(clickCount) from HealthInfoClickInfo h where h.healthInfo.id =?1 ")
	Long healthInfoTotalClickCount(Long infoId);
}
