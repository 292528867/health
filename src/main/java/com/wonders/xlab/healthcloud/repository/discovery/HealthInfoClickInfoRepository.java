package com.wonders.xlab.healthcloud.repository.discovery;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfoClickInfo;

public interface HealthInfoClickInfoRepository extends MyRepository<HealthInfoClickInfo, Long> {
	HealthInfoClickInfo findByUserIdAndClickDateAndHealthInfoId(Long userId, Date clickDate, Long healthInfoId);
	
	@Query("select sum(clickCount) from HealthInfoClickInfo h where h.healthInfo.id =?1 ")
	Long healthInfoTotalClickCount(Long infoId);
	@Query("select h.healthInfo.id, sum(clickCount) from HealthInfoClickInfo h group by h.healthInfo.id ")
	List<Object> healthInfoTotalClickCount(); 
	@Query("select h.healthInfo.id, sum(clickCount) from HealthInfoClickInfo h where h.healthInfo.healthCategory.id =?1 group by h.healthInfo.id")
	List<Object> healthInfoTotalClickCountWithCategoryId(Long categoryId);
}
