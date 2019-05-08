package com.haier.datamart.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.entity.RoleToMenu;
import com.haier.datamart.mapper.RoleToMenuMapper;
import com.haier.datamart.service.IRoleToMenuService;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zuoqb123
 * @since 2018-05-03
 */
@Service
public class RoleToMenuServiceImpl extends
		ServiceImpl<RoleToMenuMapper, RoleToMenu> implements IRoleToMenuService {

	@Override
	@Cacheable(value = "RoleToMenuServiceImpl:selectByRoleId", key = "'role_'.concat(#root.args[0])")
	public List<RoleToMenu> selectByRoleId(Integer roleId) {
		EntityWrapper<RoleToMenu> ew = new EntityWrapper<>();
		ew.where("role_id={0}", roleId);
		return this.selectList(ew);
	}
}
