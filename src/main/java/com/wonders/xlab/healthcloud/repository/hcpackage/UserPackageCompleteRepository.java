package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;

import java.util.List;

/**
 * Created by mars on 15/7/8.
 */
public interface UserPackageCompleteRepository extends MyRepository<UserPackageOrder, Long> {

    List<UserPackageOrder> findByUserIdAndPackageComplete(Long user, boolean complete);
}
