package com.wonders.xlab.healthcloud.repository;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.AppMenu;

import java.util.List;

/**
 * Created by Jeffrey on 15/7/9.
 */
public interface MenuRespository extends MyRepository<AppMenu, Long> {

    List<AppMenu> findByOrderByAppMenuTypeAscMenuSortAsc();
}
