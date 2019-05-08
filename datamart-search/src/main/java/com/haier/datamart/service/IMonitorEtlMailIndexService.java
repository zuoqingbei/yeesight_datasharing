package com.haier.datamart.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.entity.MonitorEtlMailIndex;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author doushuihai123
 * @since 2018-07-23
 */
public interface IMonitorEtlMailIndexService extends IService<MonitorEtlMailIndex> {
	/**
	 * 指标组管理 查询 获取指标基本信息
	* @author doushuihai  
	* @date 2018年7月23日上午11:30:43  
	* @TODO
	 */
	public List<MonitorEtlMailIndex> getEtlMailIndex(MonitorEtlMailIndex etlMailIndex);
	/**
	 * 根据groupId获取数据
	* @author doushuihai  
	* @date 2018年7月27日下午1:30:27  
	* @TODO
	 */
	public List<MonitorEtlMailIndex> getEtlMailIndex2Update(String groupId);
	/**
	 * 根据groupName获取数据
	* @author doushuihai  
	* @date 2018年7月27日下午1:34:17  
	* @TODO
	 */
	public List<MonitorEtlMailIndex> getEtlMailIndexByGroupName(String groupName);
	/**
	 * 指标组管理 新增
	* @author doushuihai  
	* @date 2018年7月23日下午2:06:53  
	* @TODO
	 */
	public int insertEtlMailIndex(MonitorEtlMailIndex etlMailIndex);
	/**
	 * 指标组管理 修改
	* @author doushuihai  
	* @date 2018年7月23日下午4:01:50  
	* @TODO
	 */
	public int update(MonitorEtlMailIndex etlMailIndex);
	/**
	 * 指标组管理 删除
	* @author doushuihai  
	* @date 2018年7月23日下午4:02:16  
	* @TODO
	 */
	public int deleteByGroupId(String groupId);
}
