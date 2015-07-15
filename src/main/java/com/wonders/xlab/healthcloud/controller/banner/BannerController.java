package com.wonders.xlab.healthcloud.controller.banner;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.banner.BannerDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.banner.Banner;
import com.wonders.xlab.healthcloud.entity.banner.BannerTag;
import com.wonders.xlab.healthcloud.entity.banner.BannerType;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.repository.banner.BannerRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by mars on 15/7/6.
 */
@RestController
@RequestMapping("banner")
public class BannerController extends AbstractBaseController<Banner, Long> {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private HcPackageRepository hcPackageRepository;

    @Autowired
    private HealthInfoRepository healthInfoRepository;

    @Override
    protected MyRepository<Banner, Long> getRepository() {
        return bannerRepository;
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

        List<Banner> banners = bannerRepository.findAll(filters, sort);

        List<BannerDto> bannerDtos = new ArrayList<>();
        for (Banner banner : banners) {
            BannerDto dto = new BannerDto();
            dto.setId(banner.getId());
            dto.setBannerTag(String.valueOf(banner.getBannerTag()));
            dto.setBannerType(String.valueOf(banner.getBannerType()));
            if (banner.getArticleId() != null) {
                HealthInfo hi = healthInfoRepository.findOne(banner.getArticleId());
                if (hi != null) {
                    dto.setArticleId(banner.getArticleId());
                    dto.setArticleTitle(hi.getTitle());
                }
            }
            dto.setPicUrl(banner.getPicUrl());
            dto.setLinkUrl(banner.getLinkUrl());
            dto.setEnabled(String.valueOf(banner.isEnabled()));
            dto.setPosition(String.valueOf(banner.getPosition()));
            dto.setTitle(banner.getTitle());
            bannerDtos.add(dto);
        }
        return new ControllerResult<List<BannerDto>>()
                .setRet_code(0)
                .setRet_values(bannerDtos)
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
                .setRet_values(this.bannerRepository.findBannerOrderByLastModifiedDate())
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
            this.bannerRepository.save(banner);
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
            Banner banner = this.bannerRepository.findOne(bannerId);
            if (banner == null) {
                return new ControllerResult<String>()
                        .setRet_code(-1)
                        .setRet_values("竟然没找到")
                        .setMessage("失败");
            }
            banner = bannerDto.updateBanner(banner);
            this.bannerRepository.save(banner);
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

    /**
     * 通过获取文章
     * @param packageId
     * @return
     */
    @RequestMapping(value = "retrieveHealthInfos/{packageId}", method = RequestMethod.GET)
    public Object retrieveHealthInfos(@PathVariable long packageId) {

        HcPackage hcPackage = hcPackageRepository.findOne(packageId);
        if (hcPackage == null) {
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values("竟然没找到")
                    .setMessage("失败");
        }
        List<HealthInfo> healthInfos = new ArrayList<>();
        if (hcPackage.getHealthCategory() != null) {
            healthInfos = healthInfoRepository.findByHealthCategoryId(hcPackage.getHealthCategory().getId());
        }
        return new ControllerResult<>()
                .setRet_code(0)
                .setRet_values(healthInfos)
                .setMessage("成功");
    }

}
