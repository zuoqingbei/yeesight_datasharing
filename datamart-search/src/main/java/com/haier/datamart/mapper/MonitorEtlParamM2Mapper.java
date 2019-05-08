package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.MonitorEtlParamM2;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-26
 */
public interface MonitorEtlParamM2Mapper extends BaseMapper<MonitorEtlParamM2> {
	/**
	 * 根据用户id查找对应的数据
	 * @param uid
	 * @return
	 */
	List<MonitorEtlParamM2> getListByUserId(String uid);
	/**
	 * 增加操作
	 * @param paramM2
	 * @return
	 */
	Integer addParam2(MonitorEtlParamM2 paramM2);
    /**
     * @param moudelId
     * @return com.haier.datamart.entity.MonitorEtlParamM2
     */
	MonitorEtlParamM2 getModel2ById(String moduleId);
	/**
	 * 根据传入参数更新数据库
	 * @param com.haier.datamart.entity.MonitorEtlParamM2 paramM2
	 */
	Integer updateParamM2(MonitorEtlParamM2 paramM2);
	/**
	 * 删除操作
	 * @param moduleId
	 */
	Integer deleteModel2(String moduleId);
}
