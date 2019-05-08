package com.haier.datamart.mapper;

import com.haier.datamart.entity.MonitorEtlParamM5;
import com.haier.datamart.entity.MonitorEtlParamM6;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author leizhiguo523
 * @since 2058-07-19
 */
public interface MonitorEtlParamM5Mapper extends BaseMapper<MonitorEtlParamM5> {
	/**
	 * 根据用户id查找对应的数据
	 * @param uid
	 * @return
	 */
	List<MonitorEtlParamM5> getListByUserId(String uid);
	/**
	 * 增加操作
	 * @param paramM5
	 * @return
	 */
	Integer addParam5(MonitorEtlParamM5 paramM5);
    /**
     * @param moudelId
     * @return com.haier.datamart.entity.MonitorEtlParamM5
     */
	MonitorEtlParamM5 getModel5ById(String moduleId);
	/**
	 * 根据传入参数更新数据库
	 * @param com.haier.datamart.entity.MonitorEtlParamM5 paramM5
	 */
	Integer updateParamM5(MonitorEtlParamM5 paramM5);
	/**
	 * 删除操作
	 * @param moduleId
	 */
	Integer deleteModel5(String moduleId);
}
