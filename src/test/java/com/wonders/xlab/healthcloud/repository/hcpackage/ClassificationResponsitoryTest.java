package com.wonders.xlab.healthcloud.repository.hcpackage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.xlab.framework.Application;
import com.wonders.xlab.healthcloud.dto.hcpackage.ThirdPackageDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by Jeffrey on 15/7/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ClassificationResponsitoryTest {

    @Autowired
    private ClassificationRepository classificationRepository;

    @Test
    public void testFindOrderCountPackage() throws Exception {
        List<ThirdPackageDto> thirdPackageDtos = classificationRepository.findOrderByCountPackage();
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(thirdPackageDtos);
        System.out.println("result = " + result);
    }
}