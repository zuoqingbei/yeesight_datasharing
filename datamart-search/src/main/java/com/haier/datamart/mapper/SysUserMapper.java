package com.haier.datamart.mapper;

import com.haier.datamart.entity.SysUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-25
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
	SysUser getById(SysUser user);
}
