package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.MonitorEtlParamM1;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-26
 */
public interface MonitorEtlParamM1Mapper extends BaseMapper<MonitorEtlParamM1> {
	/**
	 * 根据用户id查找对应的数据
	 * @param uid
	 * @return
	 */
	List<MonitorEtlParamM1> getListByUserId(String uid);
	/**
	 * 增加操作
	 * @param paramM1
	 * @return
	 */
    Integer addParam1(MonitorEtlParamM1 paramM1);
    /**
     * @param moudelId
     * @return com.haier.datamart.entity.MonitorEtlParamM1
     */
	MonitorEtlParamM1 getModel1ById(String moduleId);
	/**
	 * 根据传入参数更新数据库
	 * @param com.haier.datamart.entity.MonitorEtlParamM1 paramM1
	 */
	Integer updateParamM1(MonitorEtlParamM1 paramM1);
	/**
	 * 删除操作
	 * @param moduleId
	 */
	Integer deleteModel1(String moduleId);
}
