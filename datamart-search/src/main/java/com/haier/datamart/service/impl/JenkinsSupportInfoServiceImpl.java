package com.haier.datamart.service.impl;

import com.haier.datamart.entity.JenkinsSupportInfo;
import com.haier.datamart.mapper.JenkinsSupportInfoMapper;
import com.haier.datamart.service.IJenkinsSupportInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * jenkins信息服务实现类
 * @author zuoqb123
 * @date 2018-11-30
 */
@Service
@Transactional
public class JenkinsSupportInfoServiceImpl extends ServiceImpl<JenkinsSupportInfoMapper, JenkinsSupportInfo> implements IJenkinsSupportInfoService {

    @Autowired
    private JenkinsSupportInfoMapper jenkinsSupportInfoMapper;
   
}
