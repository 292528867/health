package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageComplete;

import java.util.List;

/**
 * Created by mars on 15/7/8.
 */
public interface UserPackageCompleteRepository extends MyRepository<UserPackageComplete, Long> {

    List<UserPackageComplete> findByUserIdAndPackageComplete(Long user, boolean complete);
}
