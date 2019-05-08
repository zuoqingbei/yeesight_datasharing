package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.AdminDataContent;
import com.haier.datamart.entity.TableScanError;
import com.baomidou.mybatisplus.service.IService;


public interface ITableScanErrorService extends IService<TableScanError> {
	public void insertByBatch(List<TableScanError> contents);
	public List<TableScanError> selectForValidate(TableScanError scanError);
}
