package com.wonders.xlab.healthcloud.repository.hcpackage.impl;

import com.wonders.xlab.healthcloud.dto.hcpackage.ThirdPackageDto;
import com.wonders.xlab.healthcloud.repository.hcpackage.ClassificationRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeffrey on 15/7/13.
 */
public class ClassificationRepositoryImpl implements ClassificationRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ThirdPackageDto> findOrderByCountPackage() {
        System.out.println("entityManager = " + entityManager);
        String hql = "SELECT hcc.id, hcc.title, hcc.icon, count(hp.id) countPackage " +
                "FROM hc_classification hcc " +
                "left join hc_health_category ca on hcc.id = ca.classification_id " +
                "left join hc_package hp on ca.id = hp.health_category_id " +
                "group by hcc.id, hcc.title, hcc.icon " +
                "order by count(hp.id) desc";
        Query query = entityManager.createNativeQuery(hql);

        List<Object[]> list = query.getResultList();
        List<ThirdPackageDto> thirdPackageDtos = new ArrayList<>();
        for (Object[] objects : list) {
            ThirdPackageDto dto = new ThirdPackageDto();
            dto.setId(Long.valueOf(objects[0].toString()));
            dto.setTitle((String) objects[1]);
            dto.setIconUrl((String) objects[2]);
            dto.setDescription((String)objects[3]);
            dto.setCountPackage(Integer.valueOf(objects[4].toString()));
            thirdPackageDtos.add(dto);
        }
        return thirdPackageDtos;
    }

}
