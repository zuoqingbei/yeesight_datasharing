package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.entity.AltasNameHelp;
import com.haier.datamart.entity.User;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 数据源配置 服务类
 * </p>
 *
 * @author dsh123
 * @since 2018-05-28
 */
public interface IAdminDatasourceConfigService extends IService<AdminDatasourceConfig> {
	AdminDatasourceConfig get(String id);
	AdminDatasourceConfig getConfig(AdminDatasourceConfig config);
	AdminDatasourceConfig getConfigByDb(AdminDatasourceConfig config);
	List<AdminDatasourceConfig> getAll();
	List<AdminDatasourceConfig> getAllbyuid(AdminDatasourceConfig config);
	int add(AdminDatasourceConfig config);
	
	int update(AdminDatasourceConfig config);
	
	int delete(String id);
	
	AltasNameHelp getbyid(String id,String uid);
	public boolean toTableScan(String id, User user) ;
	List<AdminDatasourceConfig> getAllByOfficeId(String officeId);
	
}
