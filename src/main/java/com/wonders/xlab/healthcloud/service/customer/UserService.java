package com.wonders.xlab.healthcloud.service.customer;

import com.wonders.xlab.healthcloud.entity.customer.User;

/**
 * Created by Jeffrey on 15/7/15.
 */
public interface UserService {

    int updateUserAndJoinHealthPlan(User user, long packageId);

}
