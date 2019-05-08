package com.haier.datamart.service.impl;

import com.haier.datamart.entity.MonitorEtlEmailContentQueryInfoChineseMap;
import com.haier.datamart.mapper.MonitorEtlEmailContentQueryInfoChineseMapMapper;
import com.haier.datamart.service.IMonitorEtlEmailContentQueryInfoChineseMapService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 服务实现类
 * @author ZhangJiang123
 * @date 2018-12-19
 */
@Service
@Transactional
public class MonitorEtlEmailContentQueryInfoChineseMapServiceImpl extends ServiceImpl<MonitorEtlEmailContentQueryInfoChineseMapMapper, MonitorEtlEmailContentQueryInfoChineseMap> implements IMonitorEtlEmailContentQueryInfoChineseMapService {

    @Autowired
    private MonitorEtlEmailContentQueryInfoChineseMapMapper monitorEtlEmailContentQueryInfoChineseMapMapper;
   
}
