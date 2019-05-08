package com.haier.datamart.service.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.entity.UserToRole;
import com.haier.datamart.mapper.UserToRoleMapper;
import com.haier.datamart.service.IUserToRoleService;
import com.haier.datamart.utils.ComUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zuoqb123
 * @since 2018-05-03
 */
@Service
public class UserToRoleServiceImpl extends
		ServiceImpl<UserToRoleMapper, UserToRole> implements IUserToRoleService {

	@Override
	@Cacheable(value = "UserToRoleServiceImpl:selectByUserId", key = "'user_'.concat(#root.args[0])")
	public UserToRole selectByUserId(Integer userId) {
		EntityWrapper<UserToRole> ew = new EntityWrapper<>();
		ew.where("user_id={0}", userId);
		List<UserToRole> userToRoleList = this.selectList(ew);
		return ComUtil.isEmpty(userToRoleList) ? null : userToRoleList.get(0);
	}
}
