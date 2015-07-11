package com.wonders.xlab.healthcloud.controller.market;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.market.MarketDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.market.Market;
import com.wonders.xlab.healthcloud.repository.market.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by mars on 15/7/11.
 */
@RestController
@RequestMapping("market")
public class MarketController extends AbstractBaseController<Market, Long> {

    @Autowired
    private MarketRepository marketRepository;

    @Override
    protected MyRepository<Market, Long> getRepository() {
        return marketRepository;
    }

    /**
     * 市场列表
     * @return
     */
    @RequestMapping("listMarket")
    public Object listMarket() {
        Sort sort = new Sort(Sort.Direction.DESC, "lastModifiedDate");
        return new ControllerResult<List<Market>>().setRet_code(0).setRet_values(this.marketRepository.findAll(sort)).setMessage("成功");
    }

    /**
     * 添加商城信息
     * @param marketDto
     * @param result
     * @return
     */
    @RequestMapping("addMarket")
    public Object addMarket(@RequestBody @Valid MarketDto marketDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败！");
        }
        this.marketRepository.save(marketDto.toNewMarket());
        return new ControllerResult<String>().setRet_code(0).setRet_values("添加成功").setMessage("成功");
    }

    /**
     * 更新商城信息
     * @param marketId
     * @param marketDto
     * @param result
     * @return
     */
    @RequestMapping("updateMarket/{marketId}")
    public Object updateMarket(@PathVariable long marketId, @RequestBody @Valid MarketDto marketDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败！");
        }
        Market market = marketRepository.findOne(marketId);
        if (market == null)
            return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没找到").setMessage("失败！");
        this.marketRepository.save(marketDto.updateMarket(market));
        return new ControllerResult<String>().setRet_code(0).setRet_values("更新成功").setMessage("成功");
    }

}
