package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageDetailStatement;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageStatement;

import java.util.List;

/**
 * Created by mars on 15/7/8.
 */
public interface UserPackageDetailStatementRepository extends MyRepository<UserPackageDetailStatement, Long> {

    List<UserPackageStatement> findByHcPackageDetail(Long detailId);
}
