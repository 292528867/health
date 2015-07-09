package com.wonders.xlab.healthcloud.controller.hcpackage;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    private UserRepository userRepository;

    @Autowired
    private HcPackageRepository hcPackageRepository;

    @RequestMapping(value = "join/{userId}/{packageId}",method = RequestMethod.POST)
    public Object joinPlan(@PathVariable Long userId, @PathVariable Long packageId) {

        List<UserPackageOrder> userPackageOrders = userPackageOrderRepository.findFetchPackageByUserIdAndPackageCompleteFalse(userId);

        if (userPackageOrders != null && userPackageOrders.size() >= 2) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("最多选择两个健康包！");
        }
        for (UserPackageOrder userPackageOrder : userPackageOrders) {
            if (packageId == userPackageOrder.getHcPackage().getId()) {
                return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("健康包已加入！");
            }
        }

        try {
            User user = userRepository.findOne(userId);
            HcPackage hcPackage = hcPackageRepository.findOne(packageId);

            UserPackageOrder userPackageOrder = new UserPackageOrder();
            userPackageOrder.setUser(user);
            userPackageOrder.setHcPackage(hcPackage);
            userPackageOrder.setCycleIndex(hcPackage.getDuration() / 7);
            userPackageOrderRepository.save(userPackageOrder);
            return new ControllerResult<>().setRet_code(0).setRet_values("").setMessage("加入成功！");
        } catch (Exception e) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("加入失败！");
        }

    }

    @RequestMapping(value = "cancel/{userId}/{packageId}",method = RequestMethod.POST)
    public Object deletePlan(@PathVariable Long userId, @PathVariable Long packageId) {
        UserPackageOrder userPackageOrder = userPackageOrderRepository.findByUserIdAndHcPackageIdAndPackageComplete(userId, packageId, false);
        if (null == userPackageOrder) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("未找到健康包");
        }
        try {
            userPackageOrderRepository.delete(userPackageOrder.getId());
            return new ControllerResult<>().setRet_code(0).setRet_values("").setMessage("取消成功！");
        } catch (Exception exp) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("取消失败！");
        }
    }

    @Override
    protected MyRepository<UserPackageOrder, Long> getRepository() {
        return userPackageOrderRepository;
    }
}
