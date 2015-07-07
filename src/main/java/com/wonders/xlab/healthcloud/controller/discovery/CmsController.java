package com.wonders.xlab.healthcloud.controller.discovery;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.xlab.healthcloud.dto.discovery.HealthCategoryDto;
import com.wonders.xlab.healthcloud.dto.discovery.HealthInfoDto;
import com.wonders.xlab.healthcloud.dto.discovery.RelatedCategoryComboDto;
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
    protected ObjectMapper objectMapper;
    
	@Autowired
	private HealthCategoryRepository healthCategoryRepository;
	@Autowired
	private HealthInfoRepository healthInfoRepository;
	
	// 添加分类
	@RequestMapping(value = "addCategory", method = RequestMethod.POST)
	public ControllerResult<?> addHealthCategory(@RequestBody @Valid HealthCategoryDto dto, BindingResult result) {
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
	@RequestMapping(value = "listCategory/groupinfo", method = RequestMethod.GET)
	public ControllerResult<?> listHealthCategoryGroup() {
		List<HealthCategory> hclist = this.healthCategoryRepository.findAll();
		
		// 计算分组输出信息
		List<RelatedCategoryComboDto> groupInfo = new ArrayList<>();
		Map<String, List<HealthCategoryDto>> map = new HashMap<>();
		for (HealthCategory hc_t : hclist) {
			String type = hc_t.getType() == null ? "其他类型" : hc_t.getType();
			if (map.get(type) == null) 
				map.put(type, new ArrayList<HealthCategoryDto>());
			map.get(type).add(new HealthCategoryDto().toNewHealthCategoryDto(hc_t));
		}
		for (String key : map.keySet()) {
			RelatedCategoryComboDto cdto = new RelatedCategoryComboDto();
			cdto.setType(key);
			cdto.setCategories(map.get(key));
			groupInfo.add(cdto);
		}
		
		return new ControllerResult<List<RelatedCategoryComboDto>>().setRet_code(0).setRet_values(groupInfo).setMessage("成功！");
	}
	
	// 查询分类
	@RequestMapping(value = "listCategory/{healthCategoryId}", method = RequestMethod.GET)
	public ControllerResult<?> listHealthCategory(@PathVariable Long healthCategoryId) {
		HealthCategory hc = this.healthCategoryRepository.findOne(healthCategoryId);
		if (hc == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没有找到！").setMessage("竟然没有找到！");
		return new ControllerResult<HealthCategory>().setRet_code(0).setRet_values(hc).setMessage("成功");
	}
	
	// 查询分类1级关联分类
	@RequestMapping(value = "listCategory/groupinfo/releatedlevel/1/{healthCategoryId}", method = RequestMethod.GET)
	public ControllerResult<?> listFirstRelatedGroupCategories(@PathVariable Long healthCategoryId) {
		HealthCategory hc = this.healthCategoryRepository.findOne(healthCategoryId);
		if (hc == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没有找到！").setMessage("竟然没有找到！");
		
		if (StringUtils.isEmpty(hc.getFirstRelatedIds())) 
			return new ControllerResult<List>().setRet_code(0).setRet_values(new ArrayList<>()).setMessage("没有1级关联！");
		String[] str_ids = hc.getFirstRelatedIds().split(",");
		Long[] long_ids = new Long[str_ids.length];
		for (int i = 0; i < str_ids.length; i++) 
			long_ids[i] = Long.parseLong(str_ids[i]);
		
		List<HealthCategory> firstRelatedCategories = this.healthCategoryRepository.findAll(Arrays.asList(long_ids));
		
		// 计算分组输出信息
		List<RelatedCategoryComboDto> groupInfo = new ArrayList<>();
		Map<String, List<HealthCategoryDto>> map = new HashMap<>();
		for (HealthCategory hc_t : firstRelatedCategories) {
			String type = hc_t.getType() == null ? "其他类型" : hc_t.getType();
			if (map.get(type) == null) 
				map.put(type, new ArrayList<HealthCategoryDto>());
			map.get(type).add(new HealthCategoryDto().toNewHealthCategoryDto(hc_t));
		}
		for (String key : map.keySet()) {
			RelatedCategoryComboDto cdto = new RelatedCategoryComboDto();
			cdto.setType(key);
			cdto.setCategories(map.get(key));
			groupInfo.add(cdto);
		}
		
		return new ControllerResult<List<RelatedCategoryComboDto>>().setRet_code(0).setRet_values(groupInfo).setMessage("成功！");
	}
	// 查询分类2级关联分类
	@RequestMapping(value = "listCategory/groupinfo/releatedlevel/2/{healthCategoryId}", method = RequestMethod.GET)
	public ControllerResult<?> listSecondRelatedGroupCategories(@PathVariable Long healthCategoryId) {
		HealthCategory hc = this.healthCategoryRepository.findOne(healthCategoryId);
		if (hc == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没有找到！").setMessage("竟然没有找到！");
		
		if (StringUtils.isEmpty(hc.getOtherRelatedIds())) 
			return new ControllerResult<List>().setRet_code(0).setRet_values(new ArrayList<>()).setMessage("没有无关系关联！");
		String[] str_ids = hc.getFirstRelatedIds().split(",");
		Long[] long_ids = new Long[str_ids.length];
		for (int i = 0; i < str_ids.length; i++) 
			long_ids[i] = Long.parseLong(str_ids[i]);
		
		List<HealthCategory> otherRelatedCategories = this.healthCategoryRepository.findAll(Arrays.asList(long_ids));
		
		// 计算分组输出信息
		List<RelatedCategoryComboDto> groupInfo = new ArrayList<>();
		Map<String, List<HealthCategoryDto>> map = new HashMap<>();
		for (HealthCategory hc_t : otherRelatedCategories) {
			String type = hc_t.getType() == null ? "其他类型" : hc_t.getType();
			if (map.get(type) == null) 
				map.put(type, new ArrayList<HealthCategoryDto>());
			map.get(type).add(new HealthCategoryDto().toNewHealthCategoryDto(hc_t));
		}
		for (String key : map.keySet()) {
			RelatedCategoryComboDto cdto = new RelatedCategoryComboDto();
			cdto.setType(key);
			cdto.setCategories(map.get(key));
			groupInfo.add(cdto);
		}
		
		return new ControllerResult<List<RelatedCategoryComboDto>>().setRet_code(0).setRet_values(groupInfo).setMessage("成功！");
	}
	
	// 修改分类
	@RequestMapping(value = "updateCategory/{healthCategoryId}", method = RequestMethod.POST)
	public ControllerResult<?> updateHealthCategory(@PathVariable Long healthCategoryId, @RequestBody @Valid HealthCategoryDto dto, BindingResult result) {
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
		return new ControllerResult<List<HealthInfo>>().setRet_code(0).setRet_values(
				this.healthInfoRepository.findByHealthCategoryId(healthCategoryId)).setMessage("成功");
	}
	
	// 查询分类健康信息
	@RequestMapping(value = "listInfo/{healthCategoryId}/{healthInfoId}", method = RequestMethod.GET)
	public ControllerResult<?> listHealthInfo(@PathVariable Long healthCategoryId, @PathVariable Long healthInfoId) {
		return new ControllerResult<HealthInfo>().setRet_code(0).setRet_values(
				this.healthInfoRepository.findByHealthCategoryIdAndId(healthCategoryId, healthInfoId)).setMessage("成功");
	}
	
	// 修改分类健康信息
	@RequestMapping(value = "updateInfo/{healthCategoryId}/{healthInfoId}", method = RequestMethod.POST)
	public ControllerResult<?> updateHealthInfo(@PathVariable Long healthCategoryId, @PathVariable Long healthInfoId, @RequestBody @Valid HealthInfoDto dto, BindingResult result) {
		if (result.hasErrors()) {
			StringBuilder builder = new StringBuilder();
			for (ObjectError error : result.getAllErrors()) 
				builder.append(error.getDefaultMessage());
			return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败！");
		}
		HealthCategory hc = this.healthCategoryRepository.findOne(healthCategoryId);
		if (hc == null)
			return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没有找到！").setMessage("竟然没有找到！");
		HealthInfo hi = this.healthInfoRepository.findOne(healthInfoId);
		if (hi == null) 
			return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没有找到！").setMessage("竟然没有找到！");
		
		
		HealthInfo hi_yl = dto.updateHealthInfo(hi);
		hi_yl.setHealthCategory(hc);		
		this.healthInfoRepository.save(hi_yl);
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
	
	@RequestMapping(value = "fileUpLoad", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> map = new HashMap<>();
        PrintWriter out = response.getWriter();
        // 转型为MultipartHttpRequest
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得上传的文件
        MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
        String url = null;
        for (String s : multiValueMap.keySet()) {
            List<MultipartFile> multipartFile = multiValueMap.get(s);
            for (MultipartFile file : multipartFile) {
                System.out.println(file.getOriginalFilename());
                url = QiniuUploadUtils.upload(file.getBytes(), URLDecoder.decode(file.getOriginalFilename(), "UTF-8"));
                System.out.println(url);
            }
        }
        map.put("error", 0);
        map.put("url", url);
        String json = objectMapper.writeValueAsString(map);
        out.println(json);

    }
	

}
