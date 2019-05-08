package com.haier.datamart.service.impl;

import com.haier.datamart.entity.SysProduct;
import com.haier.datamart.mapper.SysProductMapper;
import com.haier.datamart.service.ISysProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 项目服务实现类
 * @author zuoqb123
 * @date 2018-09-29
 */
@Service
@Transactional
public class SysProductServiceImpl extends ServiceImpl<SysProductMapper, SysProduct> implements ISysProductService {

    @Autowired
    private SysProductMapper sysProductMapper;
   
}
