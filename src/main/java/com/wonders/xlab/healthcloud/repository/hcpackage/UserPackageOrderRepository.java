package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Jeffrey on 15/7/8.
 */
public interface UserPackageOrderRepository extends MyRepository<UserPackageOrder, Long> {

    @Query("from UserPackageOrder uo left join fetch uo.hcPackage where uo.packageComplete = 0")
    List<UserPackageOrder> findFetchPackageByPackageCompleteFalse();

    @Query("from UserPackageOrder uo left join fetch uo.hcPackage where uo.user.id = :userId")
    List<UserPackageOrder> findByUserId(@Param("userId") Long userId);

    @Query("FROM UserPackageOrder uo LEFT JOIN FETCH uo.hcPackage where uo.user.id = :userId and uo.packageComplete = 0")
    List<UserPackageOrder> findFetchPackageByUserIdAndPackageCompleteFalse(Long userId);

    List<UserPackageOrder> findByUserIdAndPackageCompleteFalse(Long userId);

    UserPackageOrder findByUserAndHcPackageAndPackageComplete(User user, HcPackage hcPackage, boolean complete);

    UserPackageOrder findByUserIdAndHcPackageIdAndPackageComplete(Long userId, Long hcPackageId, boolean complete);
}
