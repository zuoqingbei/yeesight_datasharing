package com.haier.datamart.service.impl;

import com.haier.datamart.entity.MinitorSysError;
import com.haier.datamart.mapper.MinitorSysErrorMapper;
import com.haier.datamart.service.IMinitorSysErrorService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 服务实现类
 * @author zuoqb123
 * @date 2018-11-21
 */
@Service
@Transactional
public class MinitorSysErrorServiceImpl extends ServiceImpl<MinitorSysErrorMapper, MinitorSysError> implements IMinitorSysErrorService {

    @Autowired
    private MinitorSysErrorMapper minitorSysErrorMapper;
   
}
