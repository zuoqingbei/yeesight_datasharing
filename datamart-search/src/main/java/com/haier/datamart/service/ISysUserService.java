package com.haier.datamart.service;

import com.haier.datamart.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-25
 */
public interface ISysUserService extends IService<SysUser> {
	SysUser getById(SysUser user);
}
