package com.wonders.xlab.healthcloud.service.pingpp;

import com.wonders.xlab.healthcloud.dto.pingpp.PingDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.repository.steward.StewardOrderRepository;
import com.wonders.xlab.healthcloud.service.pingplusplus.Pingpp;
import com.wonders.xlab.healthcloud.service.pingplusplus.model.Channel;
import com.wonders.xlab.healthcloud.service.pingplusplus.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mars on 15/7/7.
 */
@Service
public class PingppService {

    @Autowired
    private StewardOrderRepository orderRepository;

    public String payOrder(Long userId, PingDto pingDto, BindingResult result, HttpServletRequest req, HttpServletResponse resp
    ) {
        PrintWriter out;
        resp.setContentType("application/json; charset=utf-8");

        if (result.hasErrors()) {
            if (result.hasErrors()) {
                StringBuilder builder = new StringBuilder();
                for (ObjectError error : result.getAllErrors()) {
                    builder.append(error.getDefaultMessage());
                }
               try {
                    out = resp.getWriter();
                    out.print(new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败"));
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

//        Pingpp.apiKey = "app_KavHuL08GO8O4Wbn";

//        Pingpp.apiKey = "sk_test_SOujjTjTar5KeP4a9OvvD4CG";
        Pingpp.apiKey = "sk_live_jX1mb908ern5r1qz9KGGiXfH";

        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("order_no",  new Date().getTime());
        chargeParams.put("amount", Long.parseLong(pingDto.getMoney()) * 100);
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", "app_KavHuL08GO8O4Wbn");
        chargeParams.put("app", app);
        chargeParams.put("channel",  Channel.WECHAT);
        chargeParams.put("currency", "cny");
        chargeParams.put("client_ip",  "127.0.0.1");
        chargeParams.put("subject",  "健康套餐");
        chargeParams.put("body",  "健康云养生套餐");
        Map<String, String> initialMetadata = new HashMap<String, String>();
//        initialMetadata.put("color", "red");
        chargeParams.put("metadata", initialMetadata);
        String chargeID = "";
        try {
            Charge charge = Charge.create(chargeParams);
            chargeID = charge.getId();
            System.out.println(chargeID);
            System.out.println(charge);
            String credential = charge.getCredential();
            System.out.println(credential);
            out = resp.getWriter();
            out.print(charge);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return chargeID;
    }

}
