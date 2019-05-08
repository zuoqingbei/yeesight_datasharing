package com.haier.datamart.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.entity.Role;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-28
 */
public interface IRoleService extends IService<Role> {

	List<Role> getRoleByGroup(String id);
	List<Role> getRoleByUserId(String uid);
}
