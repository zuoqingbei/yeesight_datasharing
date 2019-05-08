package com.haier.datamart.service.impl;

import com.haier.datamart.entity.SysProductExc;
import com.haier.datamart.mapper.SysProductExcMapper;
import com.haier.datamart.service.ISysProductExcService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 项目接口服务实现类
 * @author zuoqb123
 * @date 2018-12-11
 */
@Service
@Transactional
public class SysProductExcServiceImpl extends ServiceImpl<SysProductExcMapper, SysProductExc> implements ISysProductExcService {

    @Autowired
    private SysProductExcMapper sysProductExcMapper;
   
}
