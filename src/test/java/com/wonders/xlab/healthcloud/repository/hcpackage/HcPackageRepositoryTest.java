package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.wonders.xlab.framework.Application;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
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
public class HcPackageRepositoryTest {

    @Autowired
    private HcPackageRepository hcPackageRepository;

    @Test
    public void testFindAllOrderByCreateDate() throws Exception {

    }

    @Test
    public void testFindByHealthCategoryId() throws Exception {

    }

    @Test
    public void testFindByClassificationId() throws Exception {
        System.out.println("-------------------------------------");
        List<HcPackage> hcPackages = hcPackageRepository.findByClassificationId(4l);
        System.out.println("hcPackages.size() = " + hcPackages.size());
        System.out.println("=====================================");
    }

    @Test
    public void testFindOnePackage() throws Exception {

    }

    @Test
    public void testFindByCategoryId() throws Exception {

    }

    @Test
    public void testFindByIdLessThan() throws Exception {

    }

    @Test
    public void testFindByIdFetchHealthCategory() throws Exception {
        HcPackage hcPackage = hcPackageRepository.findById(1l);
        Assert.isTrue(hcPackage.getHealthCategory().getId() == 7l);
    }
}