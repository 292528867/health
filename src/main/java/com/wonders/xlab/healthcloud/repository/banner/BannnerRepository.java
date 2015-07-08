package com.wonders.xlab.healthcloud.repository.banner;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.banner.Banner;
import com.wonders.xlab.healthcloud.entity.banner.BannerType;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by mars on 15/7/6.
 */
public interface BannnerRepository extends MyRepository<Banner, Long> {

    @Query("from Banner b where b.bannerType = ?1 group by b.bannerTag order by b.lastModifiedDate desc ")
    List<Banner> findBannerOrderByLastModifiedDate(BannerType type);

    @Query("from Banner b group by b.bannerType, b.bannerTag order by b.lastModifiedDate desc ")
    List<Banner> findBannerOrderByLastModifiedDate();

    List<Banner> findByEnabled(boolean enabled);
}
