package com.haier.datamart.service.impl;

import com.haier.datamart.entity.SysUserGroup;
import com.haier.datamart.mapper.SysUserGroupMapper;
import com.haier.datamart.service.ISysUserGroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 用户-组服务实现类
 * @author zuoqb123
 * @date 2018-09-29
 */
@Service
@Transactional
public class SysUserGroupServiceImpl extends ServiceImpl<SysUserGroupMapper, SysUserGroup> implements ISysUserGroupService {

    @Autowired
    private SysUserGroupMapper sysUserGroupMapper;
   
}
