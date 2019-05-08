package com.haier.datamart.mapper;

import com.haier.datamart.entity.MonitorEtlParamM4;
import com.haier.datamart.entity.MonitorEtlParamM4;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author leizhiguo423
 * @since 2048-07-19
 */
public interface MonitorEtlParamM4Mapper extends BaseMapper<MonitorEtlParamM4> {
	/**
	 * 根据用户id查找对应的数据
	 * @param uid
	 * @return
	 */
	List<MonitorEtlParamM4> getListByUserId(String uid);
	/**
	 * 增加操作
	 * @param paramM4
	 * @return
	 */
	Integer addParam4(MonitorEtlParamM4 paramM4);
    /**
     * @param moudelId
     * @return com.haier.datamart.entity.MonitorEtlParamM4
     */
	MonitorEtlParamM4 getModel4ById(String moduleId);
	/**
	 * 根据传入参数更新数据库
	 * @param com.haier.datamart.entity.MonitorEtlParamM4 paramM4
	 */
	Integer updateParamM4(MonitorEtlParamM4 paramM4);
	/**
	 * 删除操作
	 * @param moduleId
	 */
	Integer deleteModel4(String moduleId);
}
