package com.haier.datamart.utils;

import java.util.List;

import com.haier.datamart.entity.AdminDataContentDetail;
import com.haier.datamart.service.IAdminDataContentDetailService;



/**
 * 對表掃描中需要重新維護的表信息進行多線程的更新維護
* @author doushuihai  
* @date 2018年7月5日上午11:25:37  
* @TODO
 */
public class ScanToUpdateDetail  implements Runnable {
	List<AdminDataContentDetail> dataContentDetailList ;// 获取新增的表信息
	private IAdminDataContentDetailService contentDetailService;

	public ScanToUpdateDetail(List<AdminDataContentDetail> dataContentDetailList,
			IAdminDataContentDetailService contentDetailService) {
		super();
		this.dataContentDetailList = dataContentDetailList;
		this.contentDetailService = contentDetailService;
	}

	public void run() {		
		contentDetailService.updateByBatch(dataContentDetailList);
		//itablescan.DatasourceConfigService.toTableScan(id, user);
	}

	
}
