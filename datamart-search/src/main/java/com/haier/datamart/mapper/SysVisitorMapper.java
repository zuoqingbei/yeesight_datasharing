package com.haier.datamart.mapper;

import com.haier.datamart.entity.SysVisitor;

public interface SysVisitorMapper {

	SysVisitor select(SysVisitor record);

	int deleteByPrimaryKey(Integer id);
	
	int insertSelective(SysVisitor record);

	int updateByPrimaryKeySelective(SysVisitor record);

	int updateLoggedInStatus(SysVisitor sysVisitor);

	int logoutSign(SysVisitor sysVisitor);

	int timingLogoutSign();
    
}