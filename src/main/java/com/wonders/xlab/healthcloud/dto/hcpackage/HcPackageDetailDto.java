package com.wonders.xlab.healthcloud.dto.hcpackage;

import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackage;
import com.wonders.xlab.healthcloud.entity.hcpackage.HcPackageDetail;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.text.ParseException;

/**
 * Created by mars on 15/7/4.
 */
public class HcPackageDetailDto {


    /** 健康包 */
    @NotNull(message = "健康包id不能为空")
    private String packageId;

    /**
     * 任务名称
     */
    @NotNull(message = "任务名称不能为空")
    private String taskName;

    /**
     * 任务配图
     */
    @NotNull(message = "配图不能为空")
    private String icon;

    /** 任务详细信息 */
    @NotNull(message = "任务详细介绍不能为空")
    private String detail;

    /**
     * 是否需要补充内容
     */
    @NotNull(message = "是否需要补充内容判断不能为空")
    @Pattern(regexp = "^0|1$", message = "是否需要补充内容必须为0否1是")
    private String isNeedSupplemented;

    /**
     * 补充内容
     */
    @NotNull(message = "补充内容不能为空")
    private String supplemented;

    /**
     * 任务推荐时间
     */
    @NotNull(message = "任务推荐开始时间不能为空")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$", message = "时间格式必须为HH:mm:ss")
    private String recommendTimeFrom;

    /**
     * 任务积分
     */
    @NotNull(message = "积分不能为空")
    private String integration;

    private String isFullDay;

    private MultipartFile file;

    public HcPackageDetail toNewHcPackageDetail(HcPackage hcPackage) {
            HcPackageDetail hcPackageDetail = new HcPackageDetail(
                    hcPackage,
                    taskName,
                    detail,
                    Boolean.valueOf(isNeedSupplemented),
                    supplemented,
                    Integer.parseInt(integration),
                    Boolean.valueOf(isFullDay));

        try {
            hcPackageDetail.setRecommendTimeFrom(DateUtils.parseDate(recommendTimeFrom, "H:m:s"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hcPackageDetail;

    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIsNeedSupplemented() {
        return isNeedSupplemented;
    }

    public void setIsNeedSupplemented(String isNeedSupplemented) {
        this.isNeedSupplemented = isNeedSupplemented;
    }

    public String getSupplemented() {
        return supplemented;
    }

    public void setSupplemented(String supplemented) {
        this.supplemented = supplemented;
    }

    public String getRecommendTimeFrom() {
        return recommendTimeFrom;
    }

    public void setRecommendTimeFrom(String recommendTimeFrom) {
        this.recommendTimeFrom = recommendTimeFrom;
    }

    public String getIntegration() {
        return integration;
    }

    public void setIntegration(String integration) {
        this.integration = integration;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getIsFullDay() {
        return isFullDay;
    }

    public void setIsFullDay(String isFullDay) {
        this.isFullDay = isFullDay;
    }
}
