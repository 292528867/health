package com.wonders.xlab.healthcloud.controller.hcpackage;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageOrderRepository;
import com.wonders.xlab.healthcloud.service.hcpackage.UserPackageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "join/{userId}/{packageId}", method = RequestMethod.POST)
    public Object joinPlan(@PathVariable Long userId, @PathVariable Long packageId) {
        return userPackageOrderService.joinPlan(userId, packageId);
    }

    @RequestMapping(value = "cancel/{userId}/{packageId}", method = RequestMethod.POST)
    public Object deletePlan(@PathVariable Long userId, @PathVariable Long packageId) {
        UserPackageOrder userPackageOrder = userPackageOrderRepository.findByUserIdAndHcPackageIdAndPackageComplete(userId, packageId, false);
        if (null == userPackageOrder) {
            return new ControllerResult<>()
                    .setRet_code(-1)
                    .setRet_values("")
                    .setMessage("未找到健康包");
        }
        try {
            userPackageOrderRepository.delete(userPackageOrder.getId());
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
