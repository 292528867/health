package com.wonders.xlab.healthcloud.service.pingplusplus;


import com.wonders.xlab.healthcloud.service.pingplusplus.exception.*;
import com.wonders.xlab.healthcloud.service.pingplusplus.model.Channel;
import com.wonders.xlab.healthcloud.service.pingplusplus.model.Charge;
import com.wonders.xlab.healthcloud.service.pingplusplus.model.Refund;

import java.util.HashMap;
import java.util.Map;

public class PingppExample {

    private String chargeID;

    public static void main(String[] args) {
        Pingpp.apiKey = "app_KavHuL08GO8O4Wbn";
        PingppExample example = new PingppExample();
        example.charge();
    }

    public void charge() {
//        Map<String, Object> chargeMap = new HashMap<String, Object>();
//        chargeMap.put("amount", 100);
//        chargeMap.put("currency", "cny");
//        chargeMap.put("subject",  "苹果");
//        chargeMap.put("body",  "一个又大又红的红富士苹果");
//        chargeMap.put("order_no",  "1234567890ab");
//        chargeMap.put("channel",  Channel.WECHAT);
//        chargeMap.put("client_ip",  "127.0.0.1");
//        Map<String, String> app = new HashMap<String, String>();
//        app.put("id", "sk_live_jX1mb908ern5r1qz9KGGiXfH");
//        chargeMap.put("app", app);
//        try {
//            Charge charge = Charge.create(chargeMap);
//            chargeID = charge.getId();
//            System.out.println(chargeID);
//            System.out.println(charge);
//            String credential = charge.getCredential();
//            System.out.println(credential);
//        } catch (PingppException e) {
//            e.printStackTrace();
//        }

        Pingpp.apiKey = "sk_test_SOujjTjTar5KeP4a9OvvD4CG";

        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("order_no",  "123456789");
        chargeParams.put("amount", 100);
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", "app_KavHuL08GO8O4Wbn");
        chargeParams.put("app", app);
        chargeParams.put("channel",  Channel.WECHAT);
        chargeParams.put("currency", "cny");
        chargeParams.put("client_ip",  "127.0.0.1");
        chargeParams.put("subject",  "苹果");
        chargeParams.put("body",  "一个又大又红的红富士苹果");
        Map<String, String> initialMetadata = new HashMap<String, String>();
        initialMetadata.put("color", "red");
        chargeParams.put("metadata", initialMetadata);

        try {
            Charge charge = Charge.create(chargeParams);
            chargeID = charge.getId();
            System.out.println(chargeID);
            System.out.println(charge);
            String credential = charge.getCredential();
            System.out.println(credential);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        }
    }

    public void refund() {
        try {
            Charge charge = Charge.retrieve("CHARGE-ID");
            Map<String, Object> refundMap = new HashMap<String, Object>();
            refundMap.put("amount", 100);
            refundMap.put("description", "小苹果");
            Refund re = charge.getRefunds().create(refundMap);
            System.out.println(re);
        } catch (PingppException e) {
            e.printStackTrace();
        }
    }
}
