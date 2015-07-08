package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.xlab.framework.Application;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by Jeffrey on 15/7/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class HcPackageDetailRepositoryTest {

    @Autowired
    private HcPackageDetailRepository hcPackageDetailRepository;


//    @Test
//    public void testFindByUserid() throws Exception {
//        List<HcPackageDetail> hcPackageDetail = hcPackageDetailRepository.findByUserid(47l);
//        System.out.println(new ObjectMapper().writeValueAsString(hcPackageDetail));
//    }

    @Test
    public void testFindByHcPackageId() throws Exception {
        List<HcPackageDetail> hcPackageDetails = hcPackageDetailRepository.findByHcPackageId(1l);
        System.out.println(new ObjectMapper().writeValueAsString(hcPackageDetails));
    }
}