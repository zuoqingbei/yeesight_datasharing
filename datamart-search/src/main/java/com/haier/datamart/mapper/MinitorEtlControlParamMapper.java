package com.haier.datamart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.MonitorEtlControlParam;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-26
 */				  
public interface MinitorEtlControlParamMapper extends BaseMapper<MonitorEtlControlParam> {
     List<MonitorEtlControlParam> getList(MonitorEtlControlParam param);
     Integer delete(String mid);
     MonitorEtlControlParam get(String id);
     /**
      * @param com.haier.datamart.entity.MonitorEtlControlParam mecp
      * @return 执行成功的条数
      */
     Integer addOne(MonitorEtlControlParam mecp);
     /**
      * 通过id获取一条entry
      * @param configId
      * @return
      */
	MonitorEtlControlParam getConfigById(String configId);
	/**
	 * 通过entry参数跟新数据库
	 * @param com.haier.datamart.entity.MonitorEtlControlParam mecp
	 */
	Integer updateMECP(MonitorEtlControlParam mecp);
	/**
	 * 通过moduleId获取一条entry
	 * @param moduleId
	 * @return
	 */
	MonitorEtlControlParam getConfigBymoduleId(String moduleId);
	/**
	 * 检查在当前指标下是否已经存在模板配置
	 * @param indexId
	 * @return
	 */
	Integer checkIsExist(String indexId);
	/**
	 * 获取所有控制参数的集合
	 * @return
	 */
	List<MonitorEtlControlParam> getControllerParam();
	/**
	 * 模糊搜索
	 * @param moduleName
	 * @param remarks
	 * @param userId
	 * @return
	 */
	List<MonitorEtlControlParam> fuzzSearch(
			@Param("moduleName")String moduleName, 
			@Param("remarks")String remarks, 
			@Param("userId")String userId);
	/**
	 * 通过指标id获取模板id
	 * @param index
	 * @return
	 */
	List<MonitorEtlControlParam> getModuleIdByIndexId(String index);
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
