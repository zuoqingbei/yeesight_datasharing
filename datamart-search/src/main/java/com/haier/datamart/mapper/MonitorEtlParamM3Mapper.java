package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.MonitorEtlParamM3;
import com.haier.datamart.entity.MonitorEtlParamM3;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lixiaoyi323
 * @since 2038-06-26
 */
public interface MonitorEtlParamM3Mapper extends BaseMapper<MonitorEtlParamM3> {
	/**
	 * 根据用户id查找对应的数据
	 * @param uid
	 * @return
	 */
	List<MonitorEtlParamM3> getListByUserId(String uid);
	/**
	 * 增加操作
	 * @param paramM3
	 * @return
	 */
	Integer addParam3(MonitorEtlParamM3 paramM3);
    /**
     * @param moudelId
     * @return com.haier.datamart.entity.MonitorEtlParamM3
     */
	
	MonitorEtlParamM3 getModel3ById(String moduleId);
	/**
	 * 根据传入参数更新数据库
	 * @param com.haier.datamart.entity.MonitorEtlParamM3 paramM3
	 */
	Integer updateParamM3(MonitorEtlParamM3 paramM3);
	/**
	 * 删除操作
	 * @param moduleId
	 */
	Integer deleteModel3(String moduleId);
}
