package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.Application;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import org.drools.core.command.assertion.AssertEquals;
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
        System.out.println("list.size() = " + list.size());
    }
}