package com.wonders.xlab.healthcloud.controller.hcpackage;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.hcpackage.DayPackageDetailDto;
import com.wonders.xlab.healthcloud.dto.hcpackage.HcPackageDetailDto;
import com.wonders.xlab.healthcloud.dto.hcpackage.UserStatementDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageDetailStatement;
import com.wonders.xlab.healthcloud.entity.hcpackage.UserPackageOrder;
import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageDetailRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.HcPackageRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageDetailStatementRepository;
import com.wonders.xlab.healthcloud.repository.hcpackage.UserPackageOrderRepository;
import com.wonders.xlab.healthcloud.utils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by Jeffrey on 15/7/8.
 */
@RestController
@RequestMapping("task")
public class HcPackageDetailController extends AbstractBaseController<HcPackageDetail, Long>{

    @Autowired
    private HcPackageDetailRepository hcPackageDetailRepository;

    @Autowired
    private HcPackageRepository hcPackageRepository;

    @Autowired
    private UserPackageDetailStatementRepository userPackageDetailStatementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPackageOrderRepository userPackageOrderRepository;



    @RequestMapping(value = "addTask")
    public Object addPackageTask(@Valid HcPackageDetailDto hcPackageDetailDto, BindingResult result) {

        HcPackage hcPackage = hcPackageRepository.findOne(Long.valueOf(hcPackageDetailDto.getPackageId()));
        if (null == hcPackage) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage("竟然没找到健康包！");
        }

        HcPackageDetail hcPackageDetail = new HcPackageDetail();
        BeanUtils.copyProperties(hcPackageDetailDto, hcPackageDetail, "packageId");
        hcPackageDetail.setHcPackage(hcPackage);
        hcPackageDetailRepository.save(hcPackageDetail);

        return new ControllerResult<>().setRet_code(0).setRet_values("").setMessage("添加成功！");
    }


    @RequestMapping("tasksInPackage/{packageId}")
    public List<HcPackageDetail> findTasksInPackage(@PathVariable Long packageId) {
        return hcPackageDetailRepository.findByHcPackageId(packageId);
    }

    @RequestMapping(value = "modify/{id}", method = {RequestMethod.PUT})
    public HcPackageDetail modify(@PathVariable Long id, @Valid HcPackageDetailDto hcPackageDetailDto) {
        HcPackageDetail hcPackageDetail = hcPackageDetailRepository.findOne(id);
        BeanUtils.copyProperties(hcPackageDetailDto, hcPackageDetail);
        return super.modify(hcPackageDetail);
    }

    /**
     * 获取详情
     * @param detailId
     * @return
     */
    @RequestMapping("retrievePackageDetail/{userId}/{detailId}")
    public Object retrievePackageDetail(@PathVariable Long userId, @PathVariable Long detailId) {

        // 获取健康包任务
        HcPackageDetail detail = this.hcPackageDetailRepository.findOne(detailId);

        // 用户健康包任务语句
        List<UserPackageDetailStatement> userStatements = this.userPackageDetailStatementRepository.findByUserIdAndHcPackageIdAndDate(userId, detail.getHcPackage().getId(), new Date());
        // 用户订单
        UserPackageOrder order = this.userPackageOrderRepository.findByUserIdAndHcPackageIdAndPackageComplete(userId, detail.getHcPackage().getId(), false);

        Set<UserStatementDto> statementDtos = new HashSet<>();

        for (UserPackageDetailStatement packageStatement : userStatements) {
            UserStatementDto statement = new UserStatementDto(
                    packageStatement.getId(),
                    packageStatement.getStatement(),
                    packageStatement.getCreatedDate()
            );
            statementDtos.add(statement);
        }
        DayPackageDetailDto dto = new DayPackageDetailDto(
                detail.getId(),
                detail.getTaskName(),
                detail.getClickAmount(),
                detail.getDetail(),
                detail.getIcon()
        );
        if (detail.isNeedSupplemented())
            dto.setType(1);
        if (detail.getIcon() == null)
            dto.setIconType(0);
        if (detail.getIcon().endsWith("mp4")) {
            dto.setIconType(2);
        } else {
            dto.setIconType(1);
        }

        if (order != null && order.getHcPackageDetailIds() != null) {
            String[] detailIds = order.getHcPackageDetailIds().split(",");
            Long[] longDetailIds = new Long[detailIds.length];
            for (int i = 0; i < detailIds.length; i++)
                longDetailIds[i] = Long.parseLong(detailIds[i]);
            if (Arrays.asList(longDetailIds).contains(detailId)) {
                dto.setComplete(1);
            }
        }

        dto.setStatementDtos(statementDtos);

        return new ControllerResult<DayPackageDetailDto>().setRet_code(0).setRet_values(dto).setMessage("成功");
    }

    /**
     * 确认信息
     * @param userId
     * @param detailId
     * @param content
     * @return
     */
    @RequestMapping("confirmDetail/{userId}/{detailId}")
    public Object confirmDetail(@PathVariable Long userId, @PathVariable Long detailId, @RequestParam(required = false) String content) {

        User user = this.userRepository.findOne(userId);

        HcPackageDetail hpDetail = this.hcPackageDetailRepository.findOne(detailId);
        if (hpDetail == null)
            return new ControllerResult<String>().setRet_code(-1).setRet_values("找不到任务").setMessage("成功");
        UserPackageOrder order = this.userPackageOrderRepository.findByUserAndHcPackageAndPackageComplete(user, hpDetail.getHcPackage(), false);
        if (order == null)
            return new ControllerResult<String>().setRet_code(-1).setRet_values("找不到订单").setMessage("成功");

        if (hpDetail.isNeedSupplemented()) {
            if (content == null)
                return new ControllerResult<String>().setRet_code(-1).setRet_values("内容不能为空").setMessage("成功");
            UserPackageDetailStatement statement = new UserPackageDetailStatement(
                    user,
                    hpDetail,
                    content
            );
            this.userPackageDetailStatementRepository.save(statement);
        }
        if (order.getHcPackageDetailIds() != null) {
            String[] detailIds = order.getHcPackageDetailIds().split(",");
            Long[] longDetailIds = new Long[detailIds.length + 1];
            for (int i = 0; i < detailIds.length; i++)
                longDetailIds[i] = Long.parseLong(detailIds[i]);
            longDetailIds[detailIds.length -1] = detailId;
            order.setHcPackageDetailIds(StringUtils.join(longDetailIds, ","));
        } else {
            Long[] longDetailIds = new Long[1];
            longDetailIds[0] = detailId;
            order.setHcPackageDetailIds(StringUtils.join(longDetailIds, ","));
        }
        this.userPackageOrderRepository.save(order);
        return new ControllerResult<String>().setRet_code(0).setRet_values("添加成功").setMessage("成功");

    }


    @Override
    protected MyRepository<HcPackageDetail, Long> getRepository() {
        return hcPackageDetailRepository;
    }
}
