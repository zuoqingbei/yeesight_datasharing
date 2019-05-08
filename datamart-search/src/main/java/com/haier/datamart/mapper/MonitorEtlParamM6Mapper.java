package com.haier.datamart.mapper;

import com.haier.datamart.entity.MonitorEtlParamM6;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-07-19
 */
public interface MonitorEtlParamM6Mapper extends BaseMapper<MonitorEtlParamM6> {
	/**
	 * 根据用户id查找对应的数据
	 * @param uid
	 * @return
	 */
	List<MonitorEtlParamM6> getListByUserId(String uid);
	/**
	 * 增加操作
	 * @param paramM6
	 * @return
	 */
	Integer addParam6(MonitorEtlParamM6 paramM6);
    /**
     * @param moudelId
     * @return com.haier.datamart.entity.MonitorEtlParamM6
     */
	MonitorEtlParamM6 getModel6ById(String moduleId);
	/**
	 * 根据传入参数更新数据库
	 * @param com.haier.datamart.entity.MonitorEtlParamM6 paramM6
	 */
	Integer updateParamM6(MonitorEtlParamM6 paramM6);
	/**
	 * 删除操作
	 * @param moduleId
	 */
	Integer deleteModel6(String moduleId);
}
