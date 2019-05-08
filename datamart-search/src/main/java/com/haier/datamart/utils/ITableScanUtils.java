package com.haier.datamart.utils;


import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.haier.datamart.entity.User;
import com.haier.datamart.service.IAdminDatasourceConfigService;
import com.haier.datamart.service.impl.AdminDatasourceConfigServiceImpl;

//@Component
public class ITableScanUtils  implements Runnable {
	/**
	 * 表扫描
	* @author doushuihai  
	 * @throws SQLException 
	* @date 2018年6月6日下午3:17:09  
	* @TODO
	 */
	String id;
	User user;
	private IAdminDatasourceConfigService  datasourceConfigService;
	public ITableScanUtils(String id, User user,IAdminDatasourceConfigService  datasourceConfigService) {
		this.id = id;
		this.user = user;
		this.datasourceConfigService=datasourceConfigService;
	}
	public static ITableScanUtils itablescan;
	//@PostConstruct
	public void init() {
		itablescan = this;
	}
	@Override
	public void run() {		
		datasourceConfigService.toTableScan(id, user);
		//itablescan.DatasourceConfigService.toTableScan(id, user);
	}
}
