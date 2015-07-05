package com.wonders.xlab.healthcloud.repository.discovery;

import java.util.List;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;

public interface HealthCategoryRepository extends MyRepository<HealthCategory, Long> {
	List<HealthCategory> findTop4ByType(String type);
}
