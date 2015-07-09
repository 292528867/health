package com.wonders.xlab.healthcloud.controller;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.HomePageTips;
import com.wonders.xlab.healthcloud.repository.TipsRepository;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wukai on 15/7/9.
 */
@RestController
@RequestMapping("tips")
public class TipsController extends AbstractBaseController<HomePageTips, Long> {

    @Autowired
    private TipsRepository tipsRepository;

    @Override
    protected MyRepository<HomePageTips, Long> getRepository() {
        return tipsRepository;
    }


    @RequestMapping(value = "random", method = RequestMethod.GET)
    public ControllerResult findOneTipRandom(){
        List<HomePageTips> tips = tipsRepository.findAll();
        if(CollectionUtils.isEmpty(tips)){
            return new ControllerResult().setRet_code(-1).setRet_values(null).setMessage("未获取到消息");
        }

        int index = (int) System.currentTimeMillis()%tips.size();
        return new ControllerResult().setRet_code(0).setRet_values(tips.get(index)).setMessage("成功");
    }

}
