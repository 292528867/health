package com.wonders.xlab.healthcloud.repository.banner;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.banner.Topline;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by mars on 15/7/15.
 */
public interface ToplineRepository extends MyRepository<Topline, Long> {

    List<Topline> findByEnabledTrue(Sort sort);


}
