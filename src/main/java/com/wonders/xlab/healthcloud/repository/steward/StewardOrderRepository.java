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

    @Query("from StewardOrder so left join fetch so.user left join fetch so.services where so.user.id = :id order by so.payDate asc")
    List<StewardOrder> findAllByUser(@Param("id") long id);

}
