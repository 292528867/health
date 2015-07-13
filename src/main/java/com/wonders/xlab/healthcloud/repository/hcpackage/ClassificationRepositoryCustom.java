package com.wonders.xlab.healthcloud.repository.hcpackage;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created by Jeffrey on 15/7/13.
 */
@NoRepositoryBean
public interface ClassificationRepositoryCustom {

    List findOrderByCountPackage();

}
