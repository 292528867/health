package com.wonders.xlab.healthcloud.repository.customer;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.customer.User;

/**
 * Created by Jeffrey on 15/7/2.
 */
public interface UserRepository extends MyRepository<User,Long> {

    User findByTel(String tel);
}
