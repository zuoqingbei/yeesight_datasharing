package com.haier.datamart.service.impl;

import com.haier.datamart.entity.SysGroup;
import com.haier.datamart.mapper.SysGroupMapper;
import com.haier.datamart.service.ISysGroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 用户组服务实现类
 * @author zuoqb123
 * @date 2018-09-29
 */
@Service
@Transactional
public class SysGroupServiceImpl extends ServiceImpl<SysGroupMapper, SysGroup> implements ISysGroupService {

    @Autowired
    private SysGroupMapper sysGroupMapper;
   
}
