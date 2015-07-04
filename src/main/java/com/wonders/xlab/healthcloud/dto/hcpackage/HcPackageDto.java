package com.wonders.xlab.healthcloud.dto.hcpackage;

import javax.validation.constraints.NotNull;

/**
 * Created by mars on 15/7/4.
 */
public class HcPackageDto {

    /** 标题 */
    @NotNull(message = "标题不能为空")
    private String title;

    /** 描述 */
    @NotNull(message = "描述不能为空")
    private String description;

    /** 是否推荐 */
    private boolean recommend;
}
