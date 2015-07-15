package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.Application;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import com.wonders.xlab.healthcloud.service.hcpackage.UserPackageOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Jeffrey on 15/7/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserPackageOrderRepositoryTest {

    @Autowired
    private UserPackageOrderService userPackageOrderService;

    @Autowired
    private UserPackageOrderRepository userPackageOrderRepository;

    @Test
    public void testFindFetchPackageByPackageCompleteFalse() throws Exception {

    }

    @Test
    public void testFindByUserId() throws Exception {
        List<UserPackageOrder> userPackageOrders = userPackageOrderRepository.findByUserId(47l);
        Assert.isTrue(userPackageOrders.size() == 1);
    }

    @Test
    public void testFindByUserIdAndPackageCompleteFalse() throws Exception {
        List<UserPackageOrder> list = userPackageOrderRepository.findByUserIdAndPackageCompleteFalse(47L);
        Assert.isTrue(list.size() == 0);
    }

    @Test
    public void testFindByUserAndHcPackageAndPackageComplete() throws Exception {

    }

    @Test
    public void testFindByUserIdAndHcPackageIdAndPackageComplete() throws Exception {

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2015, 7, 8, 23, 59);
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.set(2015, 7, 9, 0, 1);
//        System.out.println(DateUtils.calculateDaysOfTwoDateIgnoreHours(calendar.getTime(), calendar1.getTime()));
//        Assert.isTrue(DateUtils.calculateDaysOfTwoDateIgnoreHours(calendar.getTime(), calendar1.getTime()) == 1);
        UserPackageOrder list = userPackageOrderRepository.findByUserIdAndHcPackageIdAndPackageComplete(9l, 40l, false);
        Assert.notNull(list);
    }


    @Test
    public void testFindFetchPackageByUserIdAndPackageCompleteFalse() throws Exception {
        List<UserPackageOrder> list = userPackageOrderRepository.findFetchPackageByUserIdAndPackageCompleteFalse(47L);
        Assert.isTrue(list.size() == 0);
    }

    @Test
    public void testfindByUserIdAndPackageCompleteTrue() throws Exception {
        List<UserPackageOrder> userPackageOrders = userPackageOrderRepository.findByUserId(47l);
        Assert.isTrue(userPackageOrders.size() == 1);
    }

    @Test
    public void testfindByPackageCompleteAndPackageLoops() throws Exception {
        List<UserPackageOrder> list = userPackageOrderRepository.findByPackageCompleteAndPackageLoops(false, false);
//        userPackageOrderService.scheduleCalculateIsPackageFinished();
        System.out.println("list.size() = " + list.size());
    }

    @Test
    public void testfindByPackageCompleteAndPackageLoopsRemainder3() throws Exception {
        List<UserPackageOrder> list = userPackageOrderRepository.findByPackageCompleteAndPackageLoopsRemainder(false, false, 2);
        System.out.println("list.size() = " + list.size());
    }

    @Test
    public void testJoinPlan() throws Exception {
        Object joinPlan = userPackageOrderService.joinHealthPlan(47l, 1l);
        System.out.println("joinHealthPlan = " + joinPlan);
    }

    @Test
    public void testFindSizeByUserIdAndPackageComplete() throws Exception {
        int size = userPackageOrderRepository.findSizeByUserIdAndPackageComplete(54l, false);
        Assert.isTrue(size == 2);
    }
}