package com.haier.datamart.service;

import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.entity.RoleToMenu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zuoqb123
 * @since 2018-05-03
 */
public interface IRoleToMenuService extends IService<RoleToMenu> {

	/**
	 * 根据角色ID查询
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 结果集
	 */
	List<RoleToMenu> selectByRoleId(Integer roleId);

}
