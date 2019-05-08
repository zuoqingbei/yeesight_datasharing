package com.haier.datamart.service.impl;

import com.haier.datamart.entity.SysRoleMenu;
import com.haier.datamart.mapper.SysRoleMenuMapper;
import com.haier.datamart.service.ISysRoleMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 角色-菜单服务实现类
 * @author zuoqb123
 * @date 2018-09-29
 */
@Service
@Transactional
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
   
}
