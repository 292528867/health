package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by mars on 15/7/4.
 */
public interface HcPackageDetailRepository extends MyRepository<HcPackageDetail, Long> {

//    @Query("select pgd from HcPackageDetail pgd left join pgd.hcPackage pg left join pg.users us where us.id = :id")
//    List<HcPackageDetail> findByUserid(@Param("id")Long userId);

    List<HcPackageDetail> findByHcPackageId(Long id);

    @Query("from HcPackageDetail hpd where hpd.hcPackage.id in (?1) and hpd.taskDay = ?2 order by hpd.recommendTimeFrom asc")
    List<HcPackageDetail> findByHcPackageIdsOrderByRecommendTimeFrom(List<Long> packageIds, int taskDay);

    @Query("from HcPackageDetail hpd where hpd.hcPackage.id = ?1 and hpd.taskDay = ?2 order by hpd.recommendTimeFrom asc")
    List<HcPackageDetail> findByHcPackageIdOrderbyRecommendTimeFrom(Long packageId, int taskDay);

    @Query("from HcPackageDetail hpd where hpd.hcPackage.id = ?1 and hpd.isFullDay =?2 and hpd.taskDay = ?3 order by hpd.recommendTimeFrom asc")
    List<HcPackageDetail> findByHcPackageIdAndIsFullDayOrderbyRecommendTimeFrom(Long packageId, boolean isFullDay, int Day);

    @Query("from HcPackageDetail hpd where hpd.hcPackage.id = ?1 and hpd.isFullDay =?2 and hpd.taskDay = ?3 and hpd.recommendTimeFrom < ?4")
    Page<HcPackageDetail> findByPackageIdAndIsFullDayOrderByTimeFromAndPageable(Long packageId, boolean isFullDay, int Day, Date date, Pageable pageable);
}
