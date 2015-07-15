package com.wonders.xlab.healthcloud.controller.banner;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.banner.ToplineDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.banner.Topline;
import com.wonders.xlab.healthcloud.repository.banner.ToplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by mars on 15/7/15.
 */
@RestController
@RequestMapping("topline")
public class ToplineController extends AbstractBaseController<Topline, Long> {

    @Autowired
    private ToplineRepository toplineRepository;

    @Override
    protected MyRepository<Topline, Long> getRepository() {
        return toplineRepository;
    }

    /**
     * 头条列表
     * @return
     */
    @RequestMapping(value = "listToplines", method = RequestMethod.GET)
    public Object listToplines() {

        String[] order = {"position", "lastModifiedDate"};
        Sort sort = new Sort(Sort.Direction.DESC, order);
        return new ControllerResult<>()
                .setRet_code(0)
                .setRet_values(toplineRepository.findByEnabledTrue(sort))
                .setMessage("成功");
    }

    /**
     * 添加头条
     * @param toplineDto
     * @param result
     * @return
     */
    @RequestMapping(value = "addTopline", method = RequestMethod.POST)
    public Object addTopline(@RequestBody @Valid ToplineDto toplineDto, BindingResult result) {
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
        toplineRepository.save(toplineDto.toNewTopline());
        return new ControllerResult<>()
                .setRet_code(0)
                .setRet_values("添加成功")
                .setMessage("成功");
    }

    /**
     * 修改头条
     * @param toplineId
     * @param toplineDto
     * @param result
     * @return
     */
    @RequestMapping(value = "updateTopline/{toplineId}", method = RequestMethod.POST)
    public Object updateTopline(@PathVariable long toplineId,
                                @RequestBody @Valid ToplineDto toplineDto, BindingResult result) {
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
        Topline topline = toplineRepository.findOne(toplineId);
        if (topline == null) {
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values("竟然没找到")
                    .setMessage("失败");
        }
        Topline top = toplineDto.updateTopline(topline);
        if (toplineDto.getEnabled() != null) {
            top.setEnabled(toplineDto.getEnabled());
        }
        toplineRepository.save(top);
        return new ControllerResult<>()
                .setRet_code(0)
                .setRet_values("更新成功")
                .setMessage("成功");
    }

    /**
     * 禁用启用
     * @param toplineId
     * @param enabled
     * @return
     */
    @RequestMapping(value = "enabledTopline/{toplineId}", method = RequestMethod.GET)
    public Object enabledTopline(@PathVariable long toplineId, @RequestParam Boolean enabled) {
        Topline topline = toplineRepository.findOne(toplineId);

        if (enabled != null) {
            topline.setEnabled(enabled);
        }
        toplineRepository.save(topline);
        return new ControllerResult<>()
                .setRet_code(0)
                .setRet_values("更新成功")
                .setMessage("成功");
    }
}
