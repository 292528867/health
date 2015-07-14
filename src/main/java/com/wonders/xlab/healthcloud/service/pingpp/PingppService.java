package com.wonders.xlab.healthcloud.service.pingpp;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import com.wonders.xlab.healthcloud.dto.pingpp.PingDto;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mars on 15/7/7.
 */
@Service
public class PingppService {

    private static final String apiKey = "sk_live_jX1mb908ern5r1qz9KGGiXfH";
    private static final String appId = "app_KavHuL08GO8O4Wbn";

    public Charge payOrder(PingDto pingDto,String payWay) {

        Pingpp.apiKey = apiKey;
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("order_no", new Date().getTime());
        chargeParams.put("amount", (int)(pingDto.getMoney() * 100));
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", appId);
        chargeParams.put("app", app);
        chargeParams.put("currency", "cny");
        chargeParams.put("subject", "健康套餐");
        chargeParams.put("body", "健康云养生套餐");
        chargeParams.put("client_ip", "127.0.0.1");
        //支付宝:alipay 微信:wx
        chargeParams.put("channel", payWay);
        Map<String, String> initialMetadata = new HashMap<String, String>();
        chargeParams.put("metadata", initialMetadata);
        Charge charge;
        try {
            charge = Charge.create(chargeParams);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return charge;
    }

    public Charge queryCharge(String id) throws APIException, AuthenticationException, InvalidRequestException, APIConnectionException {

        Pingpp.apiKey = apiKey;

        Charge charge = null;
        try {
            charge = Charge.retrieve(id);
        } catch (ChannelException e) {
            e.printStackTrace();
        }

        return charge;
    }

}
