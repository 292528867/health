package com.wonders.xlab.healthcloud.controller;

import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.service.cache.HCCache;
import com.wonders.xlab.healthcloud.service.cache.HCCacheProxy;
import com.wonders.xlab.healthcloud.utils.SmsUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by lixuanwu on 15/7/2.
 */
@RestController
@RequestMapping("message")
public class SendValidCode {

    @Autowired
    @Qualifier("idenCodeCache")
    private Cache idenCodeCache;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private HCCache<String, String> cache;

    @PostConstruct
    private void init() {
        cache = new HCCacheProxy<String, String>(idenCodeCache);
    }

    /**
     * 获取验证码，并且放入缓存中
     *
     * @param mobiles
     * @return
     * @throws RuntimeException
     */

    @RequestMapping(value = "sendValidCode/{mobiles}", method = RequestMethod.GET)
    public ControllerResult sendValidCode(@PathVariable String mobiles) throws RuntimeException {

        String code = RandomStringUtils.randomNumeric(4);


        int resultCode = SmsUtils.sendValidCode(mobiles, code);

        logger.info(new Date() + ",验证码：" + code);

        if (resultCode == 0) {

            cache.addToCache(mobiles, code);

            return new ControllerResult().setRet_code(0).setRet_values("").setMessage("已成功发送，请耐心等待!");
        } else {
            return new ControllerResult().setRet_code(-1).setRet_values("").setMessage("发送失败!");
        }

    }


}
