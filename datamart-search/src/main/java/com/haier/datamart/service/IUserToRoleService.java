package com.haier.datamart.service;

import com.haier.datamart.entity.UserToRole;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zuoqb123
 * @since 2018-05-03
 */
public interface IUserToRoleService extends IService<UserToRole> {

	/**
	 * 根据用户ID查询人员角色
	 * 
	 * @param userId
	 *            用户ID
	 * @return 结果
	 */
	UserToRole selectByUserId(Integer userId);

}
