package com.wonders.xlab.healthcloud.repository.hcpackage.impl;

import com.wonders.xlab.healthcloud.entity.hcpackage.Classification;
import com.wonders.xlab.healthcloud.repository.hcpackage.ClassificationRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Jeffrey on 15/7/13.
 */
public class ClassificationRepositoryImpl implements ClassificationRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Classification> findOrderByCountPackage() {
        System.out.println("entityManager = " + entityManager);
        String hql = "SELECT hcc.id, hcc.title, hcc.icon,hcc.created_date, hcc.last_modified_date, hcc.remark, hcc.recommend, hcc.description, hcc.recommend_value " +
                "FROM hc_classification hcc " +
                "left join hc_health_category ca on hcc.id = ca.classification_id " +
                "left join hc_package hp on ca.id = hp.health_category_id " +
                "group by hcc.id, hcc.title, hcc.icon " +
                "order by count(hp.id) desc";
        Query query = entityManager.createNativeQuery(hql, Classification.class);
        return query.getResultList();
    }

}
