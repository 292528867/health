package com.wonders.xlab.healthcloud.controller.market;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.market.StoreDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.market.Store;
import com.wonders.xlab.healthcloud.repository.market.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mars on 15/7/11.
 */
@RestController
@RequestMapping("store")
public class StoreController extends AbstractBaseController<Store, Long> {

    @Autowired
    private StoreRepository storeRepository;

    @Override
    protected MyRepository<Store, Long> getRepository() {
        return storeRepository;
    }

    /**
     * 市场列表
     * @return
     */
    @RequestMapping(value = "listStores", method = RequestMethod.GET)
    public Object listStores(@RequestParam(required = false) Integer tag) {

        Sort sort = new Sort(Sort.Direction.DESC, "lastModifiedDate");

        if (tag != null) {
            Map<String, Object> filters = new HashMap<>();
            filters.put("tag_equal", Store.Tag.values()[tag]);
            return new ControllerResult<List<Store>>()
                    .setRet_code(0).setRet_values(this.storeRepository.findAll(filters, sort))
                    .setMessage("成功");
        }

        return new ControllerResult<List<Store>>()
                .setRet_code(0).setRet_values(this.storeRepository.findAll(sort))
                .setMessage("成功");
    }

    /**
     * 添加商城信息
     * @param storeDto
     * @param result
     * @return
     */
    @RequestMapping(value = "addStore", method = RequestMethod.POST)
    public Object addStore(@RequestBody @Valid StoreDto storeDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>()
                    .setRet_code(-1).setRet_values(builder.toString())
                    .setMessage("失败！");
        }
        this.storeRepository.save(storeDto.toNewStore());
        return new ControllerResult<String>()
                .setRet_code(0)
                .setRet_values("添加成功")
                .setMessage("成功");
    }

    /**
     * 更新商城信息
     * @param storeId
     * @param storeDto
     * @param result
     * @return
     */
    @RequestMapping(value = "updateStore/{storeId}", method = RequestMethod.POST)
    public Object updateStore(@PathVariable long storeId, @RequestBody @Valid StoreDto storeDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>()
                    .setRet_code(-1).setRet_values(builder.toString())
                    .setMessage("失败！");
        }
        Store market = storeRepository.findOne(storeId);
        if (market == null)
            return new ControllerResult<String>()
                    .setRet_code(-1).setRet_values("竟然没找到")
                    .setMessage("失败！");
        this.storeRepository.save(storeDto.updateStore(market));
        return new ControllerResult<String>()
                .setRet_code(0).setRet_values("更新成功")
                .setMessage("成功");
    }

}
