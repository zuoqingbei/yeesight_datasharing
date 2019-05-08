package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.TableScanError;
import com.haier.datamart.mapper.AdminDataContentMapper;
import com.haier.datamart.mapper.TableScanErrorMapper;
import com.haier.datamart.service.ITableScanErrorService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-14
 */
@Service
public class TableScanErrorServiceImpl extends ServiceImpl<TableScanErrorMapper, TableScanError> implements ITableScanErrorService {
	@Autowired
	private TableScanErrorMapper errorMapper;
	@Override
	public void insertByBatch(List<TableScanError> contents) {
		if (CollectionUtils.isEmpty(contents)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
		errorMapper.insertBatchby(contents);
		
	}
	@Override
	public List<TableScanError> selectForValidate(TableScanError scanError) {
		// TODO Auto-generated method stub
		return errorMapper.selectForValidate(scanError);
	}

}
