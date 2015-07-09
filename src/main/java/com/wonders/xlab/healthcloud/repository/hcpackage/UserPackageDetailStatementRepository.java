package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageDetailStatement;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by mars on 15/7/8.
 */
public interface UserPackageDetailStatementRepository extends MyRepository<UserPackageDetailStatement, Long> {

    @Query("from UserPackageDetailStatement upds where upds.user.id = ?1 and upds.hcPackageDetail.id = ?2 order by createdDate desc")
    List<UserPackageDetailStatement> findByUserIdHcPackageDetail(Long userId, Long detailId);
}
