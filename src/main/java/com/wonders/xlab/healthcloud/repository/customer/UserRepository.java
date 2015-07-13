package com.wonders.xlab.healthcloud.repository.customer;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.repository.customer.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Jeffrey on 15/7/2.
 */
public interface UserRepository extends MyRepository<User,Long>,UserRepositoryCustom {


    @Query("select u from User u left join fetch u.hcs hcs left join fetch hcs.hins hins left join fetch hins.hicis where u.id = ?1")
    User queryUserHealthInfo(Long userId);

    User findByTel(String tel);
}
