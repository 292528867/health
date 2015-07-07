package com.wonders.xlab.healthcloud.repository.discovery;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;

public interface HealthInfoRepository extends MyRepository<HealthInfo, Long> {
	List<HealthInfo> findByHealthCategoryId(Long id);
	@Query("select info from HealthInfo info where info.healthCategory.id in (?1)")
	List<HealthInfo> findHealthCategoryIds(Long... ids);
	HealthInfo findByHealthCategoryIdAndId(Long caid, Long infoid);
}
