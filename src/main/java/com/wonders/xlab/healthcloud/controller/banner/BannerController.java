package com.wonders.xlab.healthcloud.controller.banner;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.banner.BannerDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.banner.Banner;
import com.wonders.xlab.healthcloud.repository.banner.BannnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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
    @RequestMapping("listBannerForConsole")
    private Object listBannerForConsole(@PageableDefault(sort = "lastModifiedDate", direction = Sort.Direction.DESC)
                              Pageable pageable) {
        return new ControllerResult<List<Banner>>().setRet_code(0).setRet_values(this.bannnerRepository.findAll(pageable).getContent()).setMessage("成功");
    }

    /**
     * 标语列表
     * @return
     */
    @RequestMapping("listBanner")
    private Object listBanner() {

        
        return new ControllerResult<List<Banner>>().setRet_code(0).setRet_values(this.bannnerRepository.findAll()).setMessage("成功");
    }

    /**
     * 添加标语
     * @param bannerDto
     * @param result
     * @return
     */
    @RequestMapping("addBanner")
    private Object addBanner(@RequestBody @Valid BannerDto bannerDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }
        try {
            this.bannnerRepository.save(bannerDto.toNewBanner());
            return new ControllerResult<>().setRet_code(0).setRet_values("添加成功").setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values(exp.getLocalizedMessage()).setMessage("失败");
        }
    }

    /**
     * 更新标语
     * @param bannerId
     * @param bannerDto
     * @param result
     * @return
     */
    @RequestMapping("updateBanner/{bannerId}")
    private Object updateBanner(@PathVariable Long bannerId, @RequestBody @Valid BannerDto bannerDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }
        try {
            Banner banner = this.bannnerRepository.findOne(bannerId);
            if (banner == null)
                return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没找到").setMessage("失败");
            this.bannnerRepository.save(bannerDto.updateBanner(banner));
            return new ControllerResult<>().setRet_code(0).setRet_values("更新成功").setMessage("成功");
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values(exp.getLocalizedMessage()).setMessage("失败");
        }
    }



}
