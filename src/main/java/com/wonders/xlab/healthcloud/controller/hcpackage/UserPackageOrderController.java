package com.wonders.xlab.healthcloud.controller.hcpackage;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageDetailStatementRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageOrderRepository;
import com.wonders.xlab.healthcloud.service.discovery.DiscoveryService;
import com.wonders.xlab.healthcloud.service.hcpackage.UserPackageOrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jeffrey on 15/7/8.
 */
@RestController
@RequestMapping("plan")
public class UserPackageOrderController extends AbstractBaseController<UserPackageOrder, Long> {

    @Autowired
    private UserPackageOrderRepository userPackageOrderRepository;

    @Autowired
    private UserPackageOrderService userPackageOrderService;

    @Autowired
    private HcPackageRepository hcPackageRepository;

    @Autowired
    private UserPackageDetailStatementRepository userPackageDetailStatementRepository;

    @Autowired
    @Qualifier("discoveryServiceProxy")
    private DiscoveryService discoveryService;

    @RequestMapping(value = "join/{userId}/{packageId}", method = RequestMethod.POST)
    public ControllerResult<String> joinPlan(@PathVariable long userId, @PathVariable Long packageId) {
        int code = userPackageOrderService.joinHealthPlan(userId, packageId);
        ControllerResult<String> result = new ControllerResult<>();
        if (500 == code) {
            return result.setRet_code(-1)
                    .setRet_values("")
                    .setMessage("最多选择两个健康包！");
        } else if (400 == code) {
            return result.setRet_code(-1)
                    .setRet_values("")
                    .setMessage("健康包已加入！");
        }
        return result.setRet_code(0)
                .setRet_values("")
                .setMessage("加入成功！");
    }

    @RequestMapping(value = "cancel/{userId}/{packageId}", method = RequestMethod.POST)
    public Object deletePlan(@PathVariable Long userId, @PathVariable Long packageId) {
        UserPackageOrder userPackageOrder = userPackageOrderRepository.findByUserIdAndHcPackageIdAndPackageComplete(userId, packageId, false);

        List<Long> completeDetailIds = new ArrayList<>();
        if (userPackageOrder != null && userPackageOrder.getHcPackageDetailIds() != null) {
            String[] detailIds = StringUtils.split(userPackageOrder.getHcPackageDetailIds(), ",");
            for (String id : detailIds) {
                completeDetailIds.add(NumberUtils.toLong(id));
            }
        }

        if (null == userPackageOrder) {
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values("")
                    .setMessage("未找到健康包");
        }

        try {
            // 删除未完成的订单下的 用户对任务的语句
            userPackageDetailStatementRepository.deleteByUserIdAndHcPackageIdAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(userId, packageId, userPackageOrder.getCreatedDate(), new Date());
            int orderSize = userPackageOrderRepository.findSizeByUserIdAndPackageComplete(userId, false);
            if (orderSize == 1)
                return new ControllerResult<>()
                        .setRet_code(-1).setRet_values("").setMessage("为了能正常给您推荐健康计划必须保留一个包！");
            userPackageOrderRepository.delete(userPackageOrder.getId());
            HcPackage hcPackage = hcPackageRepository.findOne(packageId);
            discoveryService.deleteUserCategoryRelated(userId, hcPackage.getHealthCategory().getId());
            return new ControllerResult<>()
                    .setRet_code(0).setRet_values("").setMessage("取消成功！");
        } catch (Exception exp) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("取消失败！");
        }
    }

    @Override
    protected MyRepository<UserPackageOrder, Long> getRepository() {
        return userPackageOrderRepository;
    }
}
