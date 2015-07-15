package com.wonders.xlab.healthcloud.repository.steward;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.steward.StewardOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by lixuanu on 15/7/7.
 */
public interface StewardOrderRepository extends MyRepository<StewardOrder, Long> {

    @Query("from StewardOrder so left join fetch so.steward left join fetch so.services where so.steward.id = :id")
    List<StewardOrder> findAllBySteward(@Param("id") long id);

    StewardOrder findTop1ByUserIdOrderByCreatedDateDesc(long userId);

    @Query("from StewardOrder so left join fetch so.steward left join fetch so.services left join fetch so.user")
    List<StewardOrder> findAllStewardOrder();

    @Query("from StewardOrder so left join fetch so.steward left join fetch so.services where so.chargeId = :id")
    StewardOrder findAllByChargeId(@Param("id") String id);

}
