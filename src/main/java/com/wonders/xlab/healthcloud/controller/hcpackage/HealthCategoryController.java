package com.wonders.xlab.healthcloud.controller.hcpackage;


import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.repository.discovery.HealthCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wukai on 15/7/8.
 */
@RestController
@RequestMapping("healthCategory")
public class HealthCategoryController extends AbstractBaseController<HealthCategory, Long> {
    @Autowired
    private HealthCategoryRepository healthCategoryRepository;

    @Override
    protected MyRepository<HealthCategory, Long> getRepository() {
        return healthCategoryRepository;
    }
}
