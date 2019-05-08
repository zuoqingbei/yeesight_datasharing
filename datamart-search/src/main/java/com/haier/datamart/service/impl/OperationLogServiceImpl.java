package com.haier.datamart.service.impl;

import com.haier.datamart.entity.OperationLog;
import com.haier.datamart.mapper.OperationLogMapper;
import com.haier.datamart.service.IOperationLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author zuoqb123
 * @since 2018-05-08
 */
@Service
public class OperationLogServiceImpl extends
		ServiceImpl<OperationLogMapper, OperationLog> implements
		IOperationLogService {

}
