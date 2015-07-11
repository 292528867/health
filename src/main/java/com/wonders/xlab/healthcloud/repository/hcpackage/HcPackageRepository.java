package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by mars on 15/7/3.
 */
public interface HcPackageRepository extends MyRepository<HcPackage, Long> {

    @Query("from HcPackage p order by p.createdDate desc")
    List<HcPackage> findAllOrderByCreateDate();

    @Query("from HcPackage p left join fetch p.healthCategory where p.id = ?1 ")
    HcPackage findOnePackage(Long packageId);

    @Query("select p from HcPackage p left join p.healthCategory c left join c.classification f where f.id = :id")
    List<HcPackage> findByClassificationId(@Param("id") Long id);

    @Query("from HcPackage p left join fetch p.healthCategory c where c.id = :categoryId")
    List<HcPackage> findByCategoryId(@Param("categoryId")Long categoryId);
}
