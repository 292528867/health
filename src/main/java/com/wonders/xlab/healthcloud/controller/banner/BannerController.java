package com.wonders.xlab.healthcloud.controller.banner;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.banner.BannerDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.banner.Banner;
import com.wonders.xlab.healthcloud.entity.banner.BannerTag;
import com.wonders.xlab.healthcloud.entity.banner.BannerType;
import com.wonders.xlab.healthcloud.repository.banner.BannnerRepository;
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
 * Created by mars on 15/7/6.
 */
@RestController
@RequestMapping("banner")
public class BannerController extends AbstractBaseController<Banner, Long> {

    @Autowired
    private BannnerRepository bannnerRepository;

    @Override
    protected MyRepository<Banner, Long> getRepository() {
        return bannnerRepository;
    }

    /**
     * 标语列表
     * @return
     */
    @RequestMapping(value = "listBannerForConsole", method = RequestMethod.POST)
    public Object listBannerForConsole(@RequestParam(required = false) Boolean enabled,
                                       @RequestParam(required = false) Integer type,
                                       @RequestParam(required = false) Integer tag) {
        Map<String, Object> filters = new HashMap<>();
        if (type != null) {
            filters.put("bannerType_equal", BannerType.values()[type]);
        }
        if (tag != null) {
            filters.put("bannerTag_equal", BannerTag.values()[tag]);
        }
        if (enabled != null && enabled == true) {
            filters.put("enabled_equal", true);
        }
        if (enabled != null && enabled == false) {
            filters.put("enabled_equal", false);
        }
        Sort sort = new Sort(Sort.Direction.DESC, "lastModifiedDate");

        return new ControllerResult<List<Banner>>()
                .setRet_code(0)
                .setRet_values(this.bannnerRepository.findAll(filters, sort))
                .setMessage("成功");
    }

    /**
     * 标语列表
     * @return
     */
    @RequestMapping(value = "listBanner", method = RequestMethod.GET)
    public Object listBanner() {
        return new ControllerResult<List<Banner>>()
                .setRet_code(0)
                .setRet_values(this.bannnerRepository.findBannerOrderByLastModifiedDate())
                .setMessage("成功");
    }

    /**
     * 添加标语
     * @param bannerDto
     * @param result
     * @return
     */
    @RequestMapping(value = "addBanner", method = RequestMethod.POST)
    public Object addBanner(@RequestBody @Valid BannerDto bannerDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values(builder.toString())
                    .setMessage("失败");
        }
        try {
            Banner banner = bannerDto.toNewBanner();
            this.bannnerRepository.save(banner);
            this.bannnerRepository.save(banner);
            return new ControllerResult<>()
                    .setRet_code(0)
                    .setRet_values("添加成功")
                    .setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>()
                    .setRet_code(-1)
                    .setRet_values(exp.getLocalizedMessage())
                    .setMessage("失败");
        }
    }

    /**
     * 更新标语
     * @param bannerId
     * @param bannerDto
     * @param result
     * @return
     */
    @RequestMapping(value = "updateBanner/{bannerId}", method = RequestMethod.POST)
    public Object updateBanner(@PathVariable Long bannerId, @RequestBody @Valid BannerDto bannerDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values(builder.toString())
                    .setMessage("失败");
        }
        try {
            Banner banner = this.bannnerRepository.findOne(bannerId);
            if (banner == null) {
                return new ControllerResult<String>()
                        .setRet_code(-1)
                        .setRet_values("竟然没找到")
                        .setMessage("失败");
            }
            banner = bannerDto.updateBanner(banner);
            this.bannnerRepository.save(banner);
            return new ControllerResult<>()
                    .setRet_code(0)
                    .setRet_values("更新成功")
                    .setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>()
                    .setRet_code(-1)
                    .setRet_values(exp.getLocalizedMessage())
                    .setMessage("失败");
        }
    }



}
