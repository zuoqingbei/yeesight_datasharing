package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.MonitorEtlMailReceiver;
import com.haier.datamart.entity.MonitorEtlMailReceiver;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author doushuihai123
 * @since 2018-07-24
 */
public interface IMonitorEtlMailReceiverService extends IService<MonitorEtlMailReceiver> {
	/**
	 * 收件人组管理 查询
	* @author doushuihai  
	* @date 2018年7月23日上午11:30:43  
	* @TODO
	 */
	public List<MonitorEtlMailReceiver> getEtlMailReceiver(MonitorEtlMailReceiver receiver);
	/**
	 * 获取要修改的收件组信息
	* @author doushuihai  
	* @date 2018年7月27日上午9:56:35  
	* @TODO
	 */
	public List<MonitorEtlMailReceiver> getEtlMailReceiver2Update(String receiveId);
	/**
	 * 根据receiveName查询数据
	* @author doushuihai  
	* @date 2018年7月27日下午2:00:49  
	* @TODO
	 */
	public List<MonitorEtlMailReceiver> getByReceiveName(String receiveName);
	/**
	 * 收件人组管理新增
	* @author doushuihai  
	* @date 2018年7月23日下午2:06:53  
	* @TODO
	 */
	public int insertEtlMailReceiver(MonitorEtlMailReceiver receiver);
	/**
	 * 收件人组管理修改
	* @author doushuihai  
	* @date 2018年7月23日下午4:01:50  
	* @TODO
	 */
	public int update(MonitorEtlMailReceiver receiver);
	/**
	 * 收件人组管理删除
	* @author doushuihai  
	* @date 2018年7月23日下午4:02:16  
	* @TODO
	 */
	public int deleteByReceiveId(String receiveId);
	public String alterMailReceiver(String mailReceiverManagerIds, String receiveId, String userId, String receiveType, String mailId);
}
