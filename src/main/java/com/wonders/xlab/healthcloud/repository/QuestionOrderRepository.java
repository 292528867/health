package com.wonders.xlab.healthcloud.repository;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.QuestionOrder;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by wukai on 15/7/13.
 */
public interface QuestionOrderRepository extends MyRepository<QuestionOrder, Long> {
    @Query(value = "select q from QuestionOrder q left join fetch q.user where q.questionStatus = ?1 order by q.pushCount asc, q.id desc")
    List<QuestionOrder> findAllNewQuestionsOrderByPushCountAndId(QuestionOrder.QuestionStatus questionStatus);
}
