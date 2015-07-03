package com.wonders.xlab.healthcloud.controller.discovery;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wonders.xlab.healthcloud.dto.discovery.HealthCatagoryDto;
import com.wonders.xlab.healthcloud.dto.discovery.HealthInfoDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;
import com.wonders.xlab.healthcloud.repository.discovery.HealthCategoryRepository;
import com.wonders.xlab.healthcloud.repository.discovery.HealthInfoRepository;
import com.wonders.xlab.healthcloud.utils.QiniuUploadUtils;

/**
 * discovery cms后台控制器。
 * @author xu
 */
@RestController
@RequestMapping(value = "discovery/cms")
public class CmsController {
	/** 日志记录器 */
	private static final Logger logger = LoggerFactory.getLogger("com.wonders.xlab.healthcloud.controller.discovery.CmsController");
	
	@Autowired
	private HealthCategoryRepository healthCategoryRepository;
	@Autowired
	private HealthInfoRepository healthInfoRepository;
	
	// 添加分类
	@RequestMapping(value = "addCategory", method = RequestMethod.POST)
	public ControllerResult<?> addHealthCategory(@RequestBody @Valid HealthCatagoryDto dto, BindingResult result) {
		if (result.hasErrors()) {
			StringBuilder builder = new StringBuilder();
			for (ObjectError error : result.getAllErrors()) 
				builder.append(error.getDefaultMessage());
			return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败！");
		}
		this.healthCategoryRepository.save(dto.toNewHealthCategory());
		return new ControllerResult<String>().setRet_code(0).setRet_values("添加成功！").setMessage("成功！");
	}
	
	// 查询分类
	@RequestMapping(value = "listCategory", method = RequestMethod.GET)
	public ControllerResult<?> listHealthCategory() {
		return new ControllerResult<List<HealthCategory>>().setRet_code(0).setRet_values(this.healthCategoryRepository.findAll()).setMessage("成功");
	}
	
	// 查询分类
	@RequestMapping(value = "listCategory/{healthCategoryId}", method = RequestMethod.GET)
	public ControllerResult<?> listHealthCategory(@PathVariable Long healthCategoryId) {
		HealthCategory hc = this.healthCategoryRepository.findOne(healthCategoryId);
		if (hc == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没有找到！").setMessage("竟然没有找到！");
		else
			return new ControllerResult<HealthCategory>().setRet_code(0).setRet_values(hc).setMessage("成功");
	}
	
	// 修改分类
	@RequestMapping(value = "updateCategory/{healthCategoryId}", method = RequestMethod.POST)
	public ControllerResult<?> updateHealthCategory(@PathVariable Long healthCategoryId, @RequestBody @Valid HealthCatagoryDto dto, BindingResult result) {
		if (result.hasErrors()) {
			StringBuilder builder = new StringBuilder();
			for (ObjectError error : result.getAllErrors()) 
				builder.append(error.getDefaultMessage());
			return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败！");
		}
		HealthCategory hc = this.healthCategoryRepository.findOne(healthCategoryId);
		if (hc == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没有找到！").setMessage("竟然没有找到！");
		this.healthCategoryRepository.save(dto.updateHealthCategory(hc));
		return new ControllerResult<String>().setRet_code(0).setRet_values("更新成功！").setMessage("成功！");
	}
	
	// 添加分类健康信息
	@RequestMapping(value = "addInfo/{healthCategoryId}", method = RequestMethod.POST)
	public ControllerResult<?> addHealthInfo(@PathVariable Long healthCategoryId, @RequestBody @Valid HealthInfoDto dto, BindingResult result) {
		if (result.hasErrors()) {
			StringBuilder builder = new StringBuilder();
			for (ObjectError error : result.getAllErrors()) 
				builder.append(error.getDefaultMessage());
			return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败！");
		}
		
		HealthCategory hc = this.healthCategoryRepository.findOne(healthCategoryId);
		if (hc == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没有找到！").setMessage("竟然没有找到！");
		this.healthInfoRepository.save(dto.toNewHealthInfo(hc));
		return new ControllerResult<String>().setRet_code(0).setRet_values("添加成功！").setMessage("成功！");
	}
	
	// 查询分类健康信息
	@RequestMapping(value = "listInfo/{healthCategoryId}", method = RequestMethod.GET)
	public ControllerResult<?> listHealthInfo(@PathVariable Long healthCategoryId) {
		return new ControllerResult<List<HealthInfo>>().setRet_code(0).setRet_values(this.healthInfoRepository.findByHealthCategoryId(healthCategoryId)).setMessage("成功");
	}
	
	// 修改分类健康信息
	@RequestMapping(value = "updateInfo/{healthInfoId}", method = RequestMethod.POST)
	public ControllerResult<?> updateHealthInfo(@PathVariable Long healthInfoId, @RequestBody @Valid HealthInfoDto dto, BindingResult result) {
		if (result.hasErrors()) {
			StringBuilder builder = new StringBuilder();
			for (ObjectError error : result.getAllErrors()) 
				builder.append(error.getDefaultMessage());
			return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败！");
		}
		HealthInfo hi = this.healthInfoRepository.findOne(healthInfoId);
		if (hi == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没有找到！").setMessage("竟然没有找到！");
		this.healthInfoRepository.save(dto.updateHealthInfo(hi));
		return new ControllerResult<String>().setRet_code(0).setRet_values("更新成功！").setMessage("成功！");
	}
	
	// 上传图片
	@RequestMapping(value = "uploadPicture", method = RequestMethod.POST)
	public ControllerResult<?> uploadPic(MultipartFile file) {
		if (file != null && !file.isEmpty()) {
			try {
				String fileName = "healthinfo-icon-" + String.valueOf((new Date()).getTime());
				String iconUrl = QiniuUploadUtils.upload(file.getBytes(), fileName);
				return new ControllerResult<String>().setRet_code(0).setRet_values(iconUrl).setMessage("上传图片成功！");
			} catch (IOException exp) {
				exp.printStackTrace();
				return new ControllerResult<String>().setRet_code(-1).setRet_values("上传图片失败：" + exp.getLocalizedMessage()).setMessage("失败！");
			}
		} else {
			return new ControllerResult<String>().setRet_code(-1).setRet_values("上传文件为空！").setMessage("上传文件为空！");
		}
	}
	

}
