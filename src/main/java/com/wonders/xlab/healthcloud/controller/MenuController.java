package com.wonders.xlab.healthcloud.controller;

import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.AppMenu;
import com.wonders.xlab.healthcloud.repository.MenuRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Jeffrey on 15/7/9.
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuRespository menuRespository;

    @RequestMapping("findMenus")
    Object findAllMenu() {

        try {
            List<AppMenu> menus = menuRespository.findByOrderByAppMenuTypeAscMenuSortAsc();
            return new ControllerResult<>().setRet_code(0).setRet_values(menus).setMessage("获取成功！");
        } catch (Exception exp) {
            return new ControllerResult<>().setRet_code(-1).setRet_values("").setMessage(exp.getLocalizedMessage());
        }

    }

}
