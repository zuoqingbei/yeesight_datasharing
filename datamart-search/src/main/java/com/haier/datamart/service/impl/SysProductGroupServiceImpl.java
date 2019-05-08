package com.haier.datamart.service.impl;

import com.haier.datamart.entity.SysProductGroup;
import com.haier.datamart.mapper.SysProductGroupMapper;
import com.haier.datamart.service.ISysProductGroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 项目用户服务实现类
 * @author zuoqb123
 * @date 2018-09-29
 */
@Service
@Transactional
public class SysProductGroupServiceImpl extends ServiceImpl<SysProductGroupMapper, SysProductGroup> implements ISysProductGroupService {

    @Autowired
    private SysProductGroupMapper sysProductGroupMapper;
   
}
