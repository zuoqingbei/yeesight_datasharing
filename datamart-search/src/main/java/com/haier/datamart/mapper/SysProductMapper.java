package com.haier.datamart.mapper;

import com.haier.datamart.entity.SysProduct;
import com.haier.datamart.entity.SysProductGroup;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 项目 Mapper 接口
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-08-03
 */
public interface SysProductMapper extends BaseMapper<SysProduct> {
	/**
	 * 根据项目id集合查找对应的项目detail
	 * @param sysProductIdList
	 * @return
	 */
	List<SysProduct> getSysProductDetailList(List<SysProductGroup> sysProductIdList);
	/**
	 * 获取所有项目列表
	 * @return
	 */
	List<SysProduct> getAllProductList();

}
