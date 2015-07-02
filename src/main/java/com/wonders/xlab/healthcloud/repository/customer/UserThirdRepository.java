package com.wonders.xlab.healthcloud.repository.customer;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.ThirdBaseInfo;
import com.wonders.xlab.healthcloud.entity.customer.UserThird;

/**
 * Created by Jeffrey on 15/7/2.
 */
public interface UserThirdRepository extends MyRepository<UserThird, Long> {

    UserThird findByThirdIdAndThirdType(String thirdId, ThirdBaseInfo.ThirdType thirdType);
}
