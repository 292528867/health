package com.wonders.xlab.healthcloud.repository;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.EmMessages;

import java.util.List;

/**
 * Created by lixuanwu on 15/7/4.
 */
public interface EmMessagesRepository extends MyRepository<EmMessages, Long> {

    List<EmMessages> findTop5ByFromUserOrToUserOrderByCreatedDateAsc(String fromUser,String toUser);
}
