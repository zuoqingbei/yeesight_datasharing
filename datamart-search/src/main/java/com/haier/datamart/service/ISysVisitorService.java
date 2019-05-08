package com.haier.datamart.service;

import javax.servlet.http.HttpServletRequest;

import com.haier.datamart.base.PublicResult;
import com.haier.datamart.entity.SysVisitor;
import com.haier.datamart.entity.User;


/**
 * 服务类
 * @author ZhangJiang
 * @date 2018-12-14
 */
public interface ISysVisitorService{

	String loginSign(HttpServletRequest request,User user)throws Exception;

	SysVisitor validateLoginStatus(String token);

	int logoutSign(String token);

	PublicResult<String> loggedInProcessing(SysVisitor visitor, String token);

	int timingLogoutSign();
}
