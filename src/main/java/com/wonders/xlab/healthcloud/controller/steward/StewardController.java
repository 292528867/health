package com.wonders.xlab.healthcloud.controller.steward;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.entity.steward.Steward;

/**
 * Created by lixuanwu on 15/7/7.
 */
public class StewardController extends AbstractBaseController<Steward,Long> {

    @Override
    protected MyRepository<Steward, Long> getRepository() {
        return null;
    }
}
