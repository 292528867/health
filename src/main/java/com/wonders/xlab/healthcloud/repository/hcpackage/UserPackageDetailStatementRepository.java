package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageDetailStatement;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by mars on 15/7/8.
 */
public interface UserPackageDetailStatementRepository extends MyRepository<UserPackageDetailStatement, Long> {

    @Query("from UserPackageDetailStatement upds where upds.user.id = ?1 and upds.hcPackageDetail.id = ?2 and date_format(upds.createdDate, '%Y-%m-%d') = date_format(?3, '%Y-%m-%d') order by upds.createdDate desc")
    List<UserPackageDetailStatement> findByUserIdHcPackageDetail(Long userId, Long detailId, Date currentDate);
}
