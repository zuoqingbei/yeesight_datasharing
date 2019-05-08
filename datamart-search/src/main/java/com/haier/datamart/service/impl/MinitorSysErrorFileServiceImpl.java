package com.haier.datamart.service.impl;

import com.haier.datamart.entity.MinitorSysErrorFile;
import com.haier.datamart.mapper.MinitorSysErrorFileMapper;
import com.haier.datamart.service.IMinitorSysErrorFileService;
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
public class MinitorSysErrorFileServiceImpl extends ServiceImpl<MinitorSysErrorFileMapper, MinitorSysErrorFile> implements IMinitorSysErrorFileService {

    @Autowired
    private MinitorSysErrorFileMapper minitorSysErrorFileMapper;
   
}
