package com.haier.datamart.utils;

import java.util.List;

import com.haier.datamart.entity.AdminDataContentDetail;
import com.haier.datamart.entity.TableScanError;
import com.haier.datamart.service.IAdminDataContentDetailService;
import com.haier.datamart.service.ITableScanErrorService;



/**
 * 對表掃描中出現的異常信息進行多線程插入
* @author doushuihai  
* @date 2018年7月5日上午11:24:48  
* @TODO
 */
public class ScanToInsertError  implements Runnable {
	List<TableScanError> currentData;// 获取新增的表信息
	private ITableScanErrorService EerrorService ;

	

	public ScanToInsertError(List<TableScanError> currentData,
			ITableScanErrorService eerrorService) {
		super();
		this.currentData = currentData;
		EerrorService = eerrorService;
	}



	public void run() {		
		EerrorService.insertByBatch(currentData);
		//itablescan.DatasourceConfigService.toTableScan(id, user);
	}

	
}
