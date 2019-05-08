package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.Role;
import com.haier.datamart.mapper.RoleMapper;
import com.haier.datamart.service.IRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-28
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
  @Autowired
  private RoleMapper roleMapper;
	
	@Override
	public List<Role> getRoleByGroup(String id) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleByGroup(id);
	}
	/**
	 * 获取用户角色
	 */
	@Override
	public List<Role> getRoleByUserId(String uid) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleByUserId(uid);
	}

}
