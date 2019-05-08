package com.haier.datamart.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.entity.MonitorEtlMailRelation;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author doushuihai123
 * @since 2018-07-24
 */
public interface IMonitorEtlMailRelationService extends IService<MonitorEtlMailRelation> {
	/**
	 * 邮箱管理 查询
	* @author doushuihai  
	* @date 2018年7月23日上午11:30:43  
	* @TODO
	 */
	public List<MonitorEtlMailRelation> getEtlMailRelation(MonitorEtlMailRelation etlMailRelation);
	/**
	 * 根据id查询
	* @author doushuihai  
	* @date 2018年7月27日上午10:47:34  
	* @TODO
	 */
	public MonitorEtlMailRelation getEtlMailRelationById(String id);
	/**
	 * 邮箱管理 新增
	* @author doushuihai  
	* @date 2018年7月23日下午2:06:53  
	* @TODO
	 */
	public int insertEtlMailRelation(MonitorEtlMailRelation etlMailRelation);
	/**
	 * 邮箱管理 修改
	* @author doushuihai  
	* @date 2018年7月23日下午4:01:50  
	* @TODO
	 */
	public int update(MonitorEtlMailRelation etlMailRelation);
	/**
	 * 邮箱管理 删除
	* @author doushuihai  
	* @date 2018年7月23日下午4:02:16  
	* @TODO
	 */
	public int deleteById(String id);
}
