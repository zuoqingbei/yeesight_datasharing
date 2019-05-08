package com.haier.datamart.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.Role;
import com.haier.datamart.service.IRoleService;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zuoqb123
 * @since 2018-05-03
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping(value = "/all")
    @RequiresAuthentication
    public PublicResult<List<Role>> findAll() {
        EntityWrapper<Role> ew = new EntityWrapper<>();
        List<Role> result = roleService.selectList(ew);
        return new PublicResult<>(PublicResultConstant.SUCCESS, result);
    }

}

