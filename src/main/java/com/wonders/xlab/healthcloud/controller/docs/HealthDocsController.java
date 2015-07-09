package com.wonders.xlab.healthcloud.controller.docs;

import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mars on 15/7/9.
 */
@RestController
@RequestMapping("healthDocs")
public class HealthDocsController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("listHealthDocs")
    public Object listHealthDocs() {

        return userRepository.findHealthDocs();

    }

}
