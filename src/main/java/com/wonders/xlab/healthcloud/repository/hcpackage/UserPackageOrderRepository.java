package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Jeffrey on 15/7/8.
 */
public interface UserPackageOrderRepository extends MyRepository<UserPackageOrder, Long> {

    @Query("from UserPackageOrder uo left join fetch uo.hcPackage where uo.packageComplete = 0")
    List<UserPackageOrder> findFetchPackageByPackageCompleteFalse();

    @Query("from UserPackageOrder u left join fetch u.hcPackage p left join fetch u.user where u.packageComplete = :isCompleted and p.loops = :isLoop")
    List<UserPackageOrder> findByPackageCompleteAndPackageLoops(@Param("isCompleted")boolean isCompleted, @Param("isLoop")boolean isLoop);

    @Query("from UserPackageOrder u left join fetch u.hcPackage p left join fetch u.user where u.packageComplete = :isCompleted and p.loops = :isLoop and u.id%3 = :number")
    List<UserPackageOrder> findByPackageCompleteAndPackageLoopsRemainder(@Param("isCompleted")boolean isCompleted, @Param("isLoop")boolean isLoop,@Param("number") long number);

    List<UserPackageOrder> findByUserId(Long userId);

    @Query("from UserPackageOrder uo LEFT JOIN FETCH uo.hcPackage where uo.user.id = :userId and uo.packageComplete = 0")
    List<UserPackageOrder> findFetchPackageByUserIdAndPackageCompleteFalse(@Param("userId") Long userId);

    List<UserPackageOrder> findByUserIdAndPackageCompleteFalse(Long userId);

    List<UserPackageOrder> findByUserIdAndPackageCompleteTrue(Long userId);

    UserPackageOrder findByUserAndHcPackageAndPackageComplete(User user, HcPackage hcPackage, boolean complete);

    UserPackageOrder findByUserIdAndHcPackageIdAndPackageComplete(Long userId, Long hcPackageId, boolean complete);

    @Query("select count(uo.id) from UserPackageOrder uo where uo.user.id = :userId and uo.packageComplete = :packageComplete")
    int findSizeByUserIdAndPackageComplete(@Param("userId") Long userId, @Param("packageComplete") boolean packageComplete);

    int countByUserIdAndPackageCompleteFalse(long userId);
}
