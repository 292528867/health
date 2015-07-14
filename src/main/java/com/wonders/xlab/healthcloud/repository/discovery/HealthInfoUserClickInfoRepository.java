package com.wonders.xlab.healthcloud.repository.discovery;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfoUserClickInfo;

public interface HealthInfoUserClickInfoRepository extends MyRepository<HealthInfoUserClickInfo, Long> {

	@Query("select h.healthInfo.id, sum(clickCount) from HealthInfoUserClickInfo h where h.user.id = ?1 group by h.healthInfo.id ")
	List<Object> healthInfoTotalClickCountWithUserId(Long userId);
	
	HealthInfoUserClickInfo findByUserIdAndClickDateAndHealthInfoId(Long userId, Date clickDate, Long healthInfoId);
	
}
