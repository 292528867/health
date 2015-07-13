package com.wonders.xlab.healthcloud.repository.steward;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.steward.Steward;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by lixuanwu on 15/7/7.
 */
public interface StewardRepository extends MyRepository<Steward, Long> {

    List<Steward> findByRank(Steward.Rank rank);

}
