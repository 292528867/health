package com.wonders.xlab.healthcloud.repository.customer;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.customer.AddressBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by lixuanwu on 15/7/12.
 */
public interface AddressBookRepository extends MyRepository<AddressBook, Long> {

    @Query("from AddressBook ab where ab.user.id =:userId")
    List<AddressBook> findAllByUserId(@Param("userId") long userId);

    AddressBook findByUserIdAndMobile(long userId,String mobile);
}
