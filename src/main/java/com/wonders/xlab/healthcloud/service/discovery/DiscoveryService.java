package com.wonders.xlab.healthcloud.service.discovery;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wonders.xlab.healthcloud.dto.discovery.HealthInfoDto;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.discovery.HealthCategory;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfo;
import com.wonders.xlab.healthcloud.entity.discovery.HealthInfoClickInfo;

/**
 * 发现服务（文章推荐，分类标签推荐，点击数规则）
 * @author xu
 *
 */
public interface DiscoveryService {
	/**
	 * 显示更多信息，分类标签信息返回。
	 * @param user 用户
	 * @return 推荐的标签集合
	 * @return 推荐的分类
	 */
	List<HealthCategory> getRecommandTag(User user);
	
	/**
	 * 根据选择的标签，再次变化推荐的标签
	 * @param user 用户
	 * @param categories 选择的分类
	 * @return 推荐的分离
	 */
	List<HealthCategory> getRecommandTag(User user, HealthCategory... categories);
	/**
	 * 分页获取指定分类标签，某个用户的健康info信息
	 * @param healthCategory 健康分类
	 * @param user 用户
	 * @pageable 分页对象
	 * @return 所有信息文章
	 */
	Page<HealthInfoDto> getTagInfos(HealthCategory healthCategory, User user, Pageable pageable);
	
	/**
	 * 获取推荐文章。
	 * @param user 用户
	 * @return 推荐信息文章
	 */
	List<HealthInfoDto> getRecommandArticles(User user);
	
	/**
	 * 点击某个标签，记录点击。
	 * @param user 用户
	 * @param healthInfo 健康文章
	 * @return 点击对象
	 */
	HealthInfoClickInfo clickHealthInfo(User user, HealthInfo healthInfo);
	
	/**
	 * 显示具体的健康info信息
	 * @param healthInfo 健康文章信息
	 * @return 具体健康信息info
	 */
	HealthInfoDto detailHealthInfo(HealthInfo healthInfo);
	
	/**
	 * 添加用户包关联。
	 * @param userId
	 * @param categoryId
	 */
	void addUserCategoryRelated(Long userId, Long categoryId);
	/**
	 * 删除用户包关联。
	 * @param userId
	 * @param categoryId
	 */
	void deleteUserCategoryRelated(Long userId, Long categoryId);
}
