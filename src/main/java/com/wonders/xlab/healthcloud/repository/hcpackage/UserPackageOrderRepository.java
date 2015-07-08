package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Jeffrey on 15/7/8.
 */
public interface UserPackageOrderRepository extends MyRepository<UserPackageOrder, Long> {

    @Query("from UserPackageOrder uo left join fetch uo.hcPackage where uo.packageComplete = 0")
    List<UserPackageOrder> findFetchPackageByPackageCompleteFalse();

}
