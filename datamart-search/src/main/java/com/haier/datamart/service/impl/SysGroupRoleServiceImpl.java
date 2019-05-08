package com.haier.datamart.service.impl;

import com.haier.datamart.entity.SysGroupRole;
import com.haier.datamart.mapper.SysGroupRoleMapper;
import com.haier.datamart.service.ISysGroupRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 用户组-角色服务实现类
 * @author zuoqb123
 * @date 2018-09-29
 */
@Service
@Transactional
public class SysGroupRoleServiceImpl extends ServiceImpl<SysGroupRoleMapper, SysGroupRole> implements ISysGroupRoleService {

    @Autowired
    private SysGroupRoleMapper sysGroupRoleMapper;
   
}
