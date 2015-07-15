package com.wonders.xlab.healthcloud.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jeffrey on 15/7/15.
 */
public class ShareCodeUtilsTest {

    @Test
    public void testToSerialCode() throws Exception {

        Map<String, Object> map = new HashMap<>();
        for (long i = 152; i < 50000; i++) {
            String s = ShareCodeUtils.toSerialCode(i);
            if (map.containsValue(s)) {
                System.out.println("i = " + i);
            }
            map.put("" + i, s);
        }

    }

    @Test
    public void testCodeToId() throws Exception {
        assertEquals(16, ShareCodeUtils.codeToId(ShareCodeUtils.toSerialCode(16)));
    }
}