package com.wonders.xlab.healthcloud.repository;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.EmMessages;

import java.util.Date;
import java.util.List;

/**
 * Created by lixuanwu on 15/7/4.
 */
public interface EmMessagesRepository extends MyRepository<EmMessages, Long> {

    List<EmMessages> findTop5ByToUserOrderByCreatedDateAsc(String groupId);

    EmMessages findByToUserAndIsShowForDoctorAndCreatedDateBetween(String groupId, int IsShowForDoctor, Date startTime,Date endTime);

    EmMessages findTopByToUserAndIsShowForDoctorOrderByCreatedDateDesc(String groupId,int IsShowForDoctor);

   // List<EmMessages> findByToUserAndIsShowForDoctorCreatedDateBetweenOrderByCreatedDateDesc(String groupid,int IsShowForDoctor, Date startTime, Date endTime);
/*
    @Query(value = "from EmMessages e where e.toUser = ?1 and e.isShowForDoctor = ?2 and e.createdDate =?3 order by e.createdDate desc")
   List<EmMessages> findTodayMessage(String groupId,int IsShowForDoctor, Date startTime, Date endTime);*/

    List<EmMessages> findByToUserAndIsShowForDoctorAndCreatedDateBetweenOrderByCreatedDateDesc(String groupId, int IsShowForDoctor, Date startTime,Date endTime );

    List<EmMessages> findByToUserAndCreatedDateBetweenOrderByCreatedDateDesc(String groupId, Date startTime,Date endTime);
}
