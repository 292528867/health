package com.wonders.xlab.healthcloud.repository;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.QuestionOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by wukai on 15/7/13.
 */
public interface QuestionOrderRepository extends MyRepository<QuestionOrder, Long> {
    @Query(value = "select q from QuestionOrder q left join fetch q.user left join fetch q.messages where q.questionStatus = ?1 order by q.pushCount asc, q.id asc")
    List<QuestionOrder> findAllNewQuestionsOrderByPushCountAndId(QuestionOrder.QuestionStatus questionStatus);

    @Query(value = "select q from QuestionOrder q left join fetch q.messages where q.doctor.id = ?1 and q.questionStatus in ?2 order by q.questionStatus asc, q.id asc")
    List<QuestionOrder> findQuestionOrdersByDoctorID(long doctorId, QuestionOrder.QuestionStatus[] statusArray, Pageable pageable);
}
