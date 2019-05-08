package com.haier.datamart.utils;

import java.util.List;

import com.haier.datamart.entity.AdminDataContentDetail;
import com.haier.datamart.service.IAdminDataContentDetailService;



/**
 * 對多量的新增表信息進行多線程處理
* @author doushuihai  
* @date 2018年7月5日上午11:22:30  
* @TODO
 */
public class ScanToinsertDetail  implements Runnable {
	List<AdminDataContentDetail> dataContentDetailList ;// 获取新增的表信息
	private IAdminDataContentDetailService contentDetailService;

	public ScanToinsertDetail(List<AdminDataContentDetail> dataContentDetailList,
			IAdminDataContentDetailService contentDetailService) {
		super();
		this.dataContentDetailList = dataContentDetailList;
		this.contentDetailService = contentDetailService;
	}

	public void run() {		
		contentDetailService.insertByBatch(dataContentDetailList);
		//itablescan.DatasourceConfigService.toTableScan(id, user);
	}

	
}
