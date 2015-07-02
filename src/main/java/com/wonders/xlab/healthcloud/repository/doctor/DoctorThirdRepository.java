package com.wonders.xlab.healthcloud.repository.doctor;

import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.ThirdBaseInfo;
import com.wonders.xlab.healthcloud.entity.doctor.DoctorThird;

/**
 * Created by mars on 15/7/2.
 */
public interface DoctorThirdRepository extends MyRepository<DoctorThird, Long> {

    DoctorThird findByThirdIdAndThirdType(String thridId, ThirdBaseInfo.ThirdType type);
}
