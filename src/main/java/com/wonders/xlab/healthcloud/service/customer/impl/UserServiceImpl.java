package com.wonders.xlab.healthcloud.service.customer.impl;

import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import com.wonders.xlab.healthcloud.service.customer.UserService;
import com.wonders.xlab.healthcloud.service.hcpackage.UserPackageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Jeffrey on 15/7/15.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserPackageOrderService userPackageOrderService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public int updateUserAndJoinHealthPlan(User user, long packageId) {
        System.out.println("user.getHcPackages() = " + user.getHcPackages());
        userRepository.save(user);
        int code = userPackageOrderService.joinHealthPlan(user.getId(), packageId);
        return code;
    }

}
