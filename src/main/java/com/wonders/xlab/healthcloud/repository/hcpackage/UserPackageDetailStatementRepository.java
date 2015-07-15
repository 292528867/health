package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageDetailStatement;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageStatement;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by mars on 15/7/8.
 */
public interface UserPackageDetailStatementRepository extends MyRepository<UserPackageDetailStatement, Long> {

    @Query("from UserPackageDetailStatement upds where upds.user.id = ?1 and upds.hcPackage.id = ?2 and date_format(upds.createdDate, '%Y-%m-%d') = date_format(?3, '%Y-%m-%d') and upds.hcPackageDetail.isNeedSupplemented = true order by upds.createdDate desc")
    List<UserPackageDetailStatement> findByUserIdAndHcPackageIdAndDate(Long userId, Long packageId, Date currentDate);


    Long deleteByUserIdAndHcPackageIdAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(long userId, long packageId, Date from, Date to);
}
