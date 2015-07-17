package com.wonders.xlab.healthcloud.repository.discovery;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;

public interface HealthInfoRepository extends MyRepository<HealthInfo, Long> {
	Page<HealthInfo> findByHealthCategoryId(Long id, Pageable pageable);
	@Query("select info from HealthInfo info where info.healthCategory.id in (?1)")
	List<HealthInfo> findHealthCategoryIds(Long... ids);
	
	@Query("select info from HealthInfo info left join fetch info.healthInfoClickInfo where info.healthCategory.id in (?1)")
	List<HealthInfo> findHealthCategoryIdsWithClickInfo(Long... ids);
	@Query("select info from HealthInfo info left join fetch info.healthInfoClickInfo where info.id in (?1)")
	List<HealthInfo> findByHealthInfoIdsWithClickInfo(Long... ids);
	
	HealthInfo findByHealthCategoryIdAndId(Long caid, Long infoid);
	
	@Query("select info from HealthInfo info where info.healthCategory.id = ?1 and info.id not in (?2) "
			+ " order by info.healthInfoClickInfo.virtualClickCount, info.createdDate "
	)
	Page<HealthInfo> pageablefindByCategoryIdWithOutIds(Long categoryId, List<Long> withoutids, Pageable pageable);
	
	@Query("select info from HealthInfo info where info.healthCategory.id = ?1  "
			+ " order by info.healthInfoClickInfo.virtualClickCount, info.createdDate "
	)
	Page<HealthInfo> pageablefindByCategoryId(Long categoryId, Pageable pageable);

	List<HealthInfo> findByHealthCategoryId(Long id);
}
