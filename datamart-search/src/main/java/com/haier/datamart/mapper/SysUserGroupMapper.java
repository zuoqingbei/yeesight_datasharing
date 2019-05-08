package com.haier.datamart.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haier.datamart.entity.SysUserGroup;

/**
 * <p>
 * 用户-组 Mapper 接口
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-08-03
 */
public interface SysUserGroupMapper extends BaseMapper<SysUserGroup> {
	
	/**
	 * 根据用户id获取对应的用户组对象
	 * @param userId
	 * @return
	 */
	List<SysUserGroup> getGroupId(String userId);
	int  addSysUserGroup(SysUserGroup sysUserGroup);
	

}
