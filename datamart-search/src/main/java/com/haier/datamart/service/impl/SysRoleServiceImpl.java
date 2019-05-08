package com.haier.datamart.service.impl;

import com.haier.datamart.entity.SysRole;
import com.haier.datamart.mapper.SysRoleMapper;
import com.haier.datamart.service.ISysRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 角色表服务实现类
 * @author zuoqb123
 * @date 2018-09-29
 */
@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
   
}
