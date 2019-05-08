package com.haier.datamart.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haier.datamart.entity.MonitorEtlMailReceiver;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author doushuihai123
 * @since 2018-07-24
 */
public interface MonitorEtlMailReceiverMapper extends BaseMapper<MonitorEtlMailReceiver> {
	List<MonitorEtlMailReceiver> getEtlMailReceiver(MonitorEtlMailReceiver etlMailReceiver);//收件人组管理  查询
	List<MonitorEtlMailReceiver> getEtlMailReceiver2Update(String receiveId);//收件人组管理  根据receiveId查询获取要修改的收件人组
	List<MonitorEtlMailReceiver> getByReceiveName(String receiveName);//收件人组管理 根据receiveName查询
	int insertEtlMailReceiver(MonitorEtlMailReceiver etlMailReceiver);//收件人组管理  新增
	Integer updateByReceiveId(MonitorEtlMailReceiver etlMailReceiver);//收件人组管理  修改
	Integer deleteByReceiveId(String groupId);//收件人组管理  删除
}
