package com.wonders.xlab.healthcloud.controller;

import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.utils.SmsUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixuanwu on 15/7/2.
 */
@RestController
@RequestMapping("message")
public class SendValidCode {

    @Autowired
    @Qualifier("idenCodeCache")
    private Cache idenCodeCache;

    /**
     * 获取验证码，并且放入缓存中
     * @param mobiles
     * @return
     * @throws RuntimeException
     */

    @RequestMapping(name = "sendValidCode/{mobiles}", method = RequestMethod.GET)
    public ControllerResult sendValidCode(@PathVariable String mobiles) throws RuntimeException{

        String code = RandomStringUtils.random(4);

        int resultCode = SmsUtils.sendValidCode(mobiles, code);

        if (resultCode == 0) {

            Element element = new Element(mobiles, code);
            idenCodeCache.put(element);
            return new ControllerResult().setRet_code(0).setRet_values("已成功发送，请耐心等待!");
        } else {
            return new ControllerResult().setRet_code(1).setRet_values("发送失败!");
        }

    }

}
