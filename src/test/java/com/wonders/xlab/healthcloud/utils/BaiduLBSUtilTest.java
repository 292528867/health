package com.wonders.xlab.healthcloud.utils;

import com.wonders.xlab.framework.Application;
import com.wonders.xlab.healthcloud.dto.steward.LBSResult;
import com.wonders.xlab.healthcloud.entity.steward.Steward;
import com.wonders.xlab.healthcloud.repository.steward.StewardRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by lixuanwu on 15/7/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BaiduLBSUtilTest extends TestCase {

    @Autowired
    private StewardRepository stewardRepository;

    @Autowired
    private BaiduLBSUtil baiduLBSUtil;

    @Test
    public void testCreatePOI() throws Exception {
        List<Steward> stewards = stewardRepository.findAll();

        for (Steward steward : stewards) {
            String result = (String) baiduLBSUtil.createPOI(steward);
            System.out.println(result);
        }


    }

    @Test
    public void testNearbyJob() throws Exception {

        String coordinate = "121.01094570986,31.003614615668";
        long distance = 100;

        LBSResult result = baiduLBSUtil.nearbyJob(coordinate, distance);
        System.out.println(result.getContents());
    }
}