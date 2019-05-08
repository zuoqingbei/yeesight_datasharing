package com.haier.datamart.service.impl;

import com.haier.datamart.entity.SysUser;
import com.haier.datamart.mapper.SearchReportsMapper;
import com.haier.datamart.mapper.SysUserMapper;
import com.haier.datamart.service.ISysUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-25
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
	@Autowired
	private SysUserMapper sysUserMapper;
	@Override
	public SysUser getById(SysUser user) {
		// TODO Auto-generated method stub
		return sysUserMapper.getById(user);
	}

}
