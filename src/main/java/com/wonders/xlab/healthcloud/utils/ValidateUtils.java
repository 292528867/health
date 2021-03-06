package com.wonders.xlab.healthcloud.utils;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by mars on 15/7/2.
 */
public class ValidateUtils {

    /** 判断是否为合法的手机号码 */
    public static boolean validateTel(String tel) {
        return isNotEmpty(tel) && tel.matches("^1((3|5|8){1}\\d{1}|70|77)\\d{8}$");
    }
}
