package com.haier.datamart.mapper;

import com.haier.datamart.entity.SysProductGroup;
import com.haier.datamart.entity.SysUserGroup;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 项目用户 Mapper 接口
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-08-03
 */
public interface SysProductGroupMapper extends BaseMapper<SysProductGroup> {
	/**
	 * 根据用户组id集合查询对应的信息
	 * @param sysUserGroups
	 * @return
	 */
	List<SysProductGroup> getSysProductIdList(List<SysUserGroup> sysUserGroups);

}
