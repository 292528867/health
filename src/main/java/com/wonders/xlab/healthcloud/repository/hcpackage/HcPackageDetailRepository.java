package com.wonders.xlab.healthcloud.repository.hcpackage;

        import com.wonders.xlab.framework.repository.MyRepository;
        import com.wonders.xlab.healthcloud.dto.IdenCode;
        import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.query.Param;

        import java.util.List;

/**
 * Created by mars on 15/7/4.
 */
public interface HcPackageDetailRepository extends MyRepository<HcPackageDetail, Long> {

    @Query("select pgd from HcPackageDetail pgd left join pgd.hcPackage pg left join pg.users us where us.id = :id")
    List<HcPackageDetail> findByUserid(@Param("id")Long userId);

    List<HcPackageDetail> findByHcPackageId(Long id);

}
