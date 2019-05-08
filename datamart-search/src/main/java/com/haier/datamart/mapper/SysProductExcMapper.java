package com.haier.datamart.mapper;

import com.haier.datamart.entity.SysProductExc;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 项目接口 Mapper 接口
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-08-03
 */
public interface SysProductExcMapper extends BaseMapper<SysProductExc> {
	/**
	 * 通过关系表中的项目id获取对应的接口id集合
	 * @param productId
	 * @return
	 */
	List<Long> getDataInterfaceList(@Param("productId")String productId);
	/**
	 * 将项目和接口对应关系存入关系表中
	 */
	Integer addForCorrelation(@Param("productId")String productId, @Param("excId")Long excId);

}
