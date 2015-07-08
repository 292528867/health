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

    @Query("from UserPackageOrder uo left join fetch uo.hcPackage where uo.user.id = :userId")
    List<UserPackageOrder> findByUserId(@Param("userId") Long userId);

    UserPackageOrder findByUserAndHcPackageAndPackageComplete(User user, HcPackage hcPackage, boolean complete);

}
