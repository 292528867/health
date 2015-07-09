package com.wonders.xlab.healthcloud.repository.customer.custom;

import com.wonders.xlab.healthcloud.entity.docs.HealthDocs;

import java.util.List;

/**
 * Created by mars on 15/7/9.
 */
public interface UserRepositoryCustom {

    List<HealthDocs> findHealthDocs();
}
