package com.wonders.xlab.healthcloud.controller.lottery;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.banner.Banner;
import com.wonders.xlab.healthcloud.entity.lottery.LotteryInfo;
import com.wonders.xlab.healthcloud.repository.lottery.LotteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * Created by qinwenshi on 7/13/15.
 */
@RestController
@RequestMapping("lottery")
public class LotteryController extends AbstractBaseController<LotteryInfo, Long> {

    @Autowired
    LotteryRepository lotteryRepository;

    @Override
    protected MyRepository<LotteryInfo, Long> getRepository() {
        return lotteryRepository;
    }

    @RequestMapping(value = "getResult", method = RequestMethod.GET)
    public Object getLotteryResult(@PageableDefault(sort = "lastModifiedDate", direction = Sort.Direction.DESC)
                                                     Pageable pageable) {

        List<LotteryInfo> list = lotteryRepository.findAll();
        float currentNumber = new Random().nextFloat();
        float lowerProb = 0;
        LotteryInfo prize = new LotteryInfo();
        for (LotteryInfo lotteryInfo : list) {
            //lowerProb = lotteryInfo.getProbability();
            System.out.println("currentNumber"+ currentNumber+ " lower Prob. = " + lowerProb +" Higher Prob."+ (lowerProb + lotteryInfo.getProbability()));
            if( currentNumber * 100 <= lowerProb + lotteryInfo.getProbability()
                    && currentNumber * 100> lowerProb)
            {
                prize = lotteryInfo;
                break;
            }

            lowerProb += lotteryInfo.getProbability();
        }
        return new ControllerResult<String>().setRet_code(0).setRet_values(prize.getName()).setMessage("success");
        //return "{\"success\": true, \"prize\":\"" + prize.getName()+"\"}";
    }
}
