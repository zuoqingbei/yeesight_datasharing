package com.haier.datamart.service;

import com.haier.datamart.entity.MonitorEtlControlParam;
import com.haier.datamart.entity.MonitorEtlParamM1;
import com.haier.datamart.entity.MonitorEtlParamM2;
import com.haier.datamart.entity.MonitorEtlParamM3;
import com.haier.datamart.entity.MonitorEtlParamM4;
import com.haier.datamart.entity.MonitorEtlParamM5;
import com.haier.datamart.entity.MonitorEtlParamM6;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-07-19
 */
public interface IMonitorService {
	/*公用*/
	void deleteModel(String moduleId, String etlTypeId);

	MonitorEtlControlParam getConfigBymoduleId(String moduleId);
	
	MonitorEtlControlParam getConfigById(String configId);
	
	/*模板1*/

	MonitorEtlParamM1 getModel1ById(String entryId);

	boolean addModel1One(MonitorEtlParamM1 paramM1, MonitorEtlControlParam mecp);

	boolean updateParamM1(MonitorEtlParamM1 paramM1, MonitorEtlControlParam mecp);
	
	
	/*模板2*/
	
	boolean updateParamM2(MonitorEtlParamM2 paramM2, MonitorEtlControlParam mecp);

	boolean addModel2One(MonitorEtlParamM2 paramM2, MonitorEtlControlParam mecp);

	MonitorEtlParamM2 getModel2ById(String moduleId);

	/*模板3*/
	
	boolean updateParamM3(MonitorEtlParamM3 paramM3, MonitorEtlControlParam mecp);

	boolean addModel3One(MonitorEtlParamM3 paramM3, MonitorEtlControlParam mecp);

	MonitorEtlParamM3 getModel3ById(String moduleId);
	
	/*模板4*/
	
	boolean updateParamM4(MonitorEtlParamM4 paramM4, MonitorEtlControlParam mecp);

	boolean addModel4One(MonitorEtlParamM4 paramM4, MonitorEtlControlParam mecp);

	MonitorEtlParamM4 getModel4ById(String moduleId);
	
	/*模板5*/
	
	boolean updateParamM5(MonitorEtlParamM5 paramM5, MonitorEtlControlParam mecp);

	boolean addModel5One(MonitorEtlParamM5 paramM5, MonitorEtlControlParam mecp);

	MonitorEtlParamM5 getModel5ById(String moduleId);
	
	/*模板6*/
	
	boolean updateParamM6(MonitorEtlParamM6 paramM6, MonitorEtlControlParam mecp);

	boolean addModel6One(MonitorEtlParamM6 paramM6, MonitorEtlControlParam mecp);

	MonitorEtlParamM6 getModel6ById(String moduleId);
	
	boolean checkIsExist(String indexId);

	List<MonitorEtlControlParam> getControllerParam();
	/**
	 * 
	 * @param moduleName
	 * @param remarks
	 * @param userIds
	 * @return
	 */
	List<MonitorEtlControlParam> fuzzSearch(String moduleName, String remarks, String userId);

	List<MonitorEtlControlParam> getModuleIdByIndexId(String indexId);
	
	/**
	 * 
	 * @time   2018年12月6日 上午11:56:31
	 * @author zuoqb
	 * @todo   根据表编码查询配置的模板信息
	 */
	List<MonitorEtlControlParam> getMonitorListByContentId(String contentId);
	
	/**
	 * 
	 * @time   2018年12月6日 上午11:56:31
	 * @author zuoqb
	 * @todo   根据报表查询配置的模板信息
	 */
	List<MonitorEtlControlParam> getMonitorListByReportId(String reportId);
	/**
	 * 
	 * @time   2018年12月6日 上午11:56:31
	 * @author zuoqb
	 * @todo   根据指标id查询配置的模板信息
	 */
	List<MonitorEtlControlParam> getMonitorListByIndexId(String indexId);
}
