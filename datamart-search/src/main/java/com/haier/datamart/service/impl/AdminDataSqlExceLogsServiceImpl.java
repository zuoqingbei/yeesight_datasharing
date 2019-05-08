package com.haier.datamart.service.impl;

import com.haier.datamart.entity.AdminDataSqlExceLogs;
import com.haier.datamart.mapper.AdminDataSqlExceLogsMapper;
import com.haier.datamart.service.IAdminDataSqlExceLogsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * sql执行日志服务实现类
 * @author zuoqb123
 * @date 2019-01-02
 */
@Service
@Transactional
public class AdminDataSqlExceLogsServiceImpl extends ServiceImpl<AdminDataSqlExceLogsMapper, AdminDataSqlExceLogs> implements IAdminDataSqlExceLogsService {

    @Autowired
    private AdminDataSqlExceLogsMapper adminDataSqlExceLogsMapper;
   
}
