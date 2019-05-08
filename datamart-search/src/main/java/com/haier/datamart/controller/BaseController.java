package com.haier.datamart.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.haier.datamart.config.Constant;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.IUserService;

public class BaseController implements Constant{
	 @Autowired
	 private IUserService userService;
	
	public void clearSessionUser(HttpServletRequest request,HttpServletResponse response,String key){
		request.getSession().setAttribute(key, null);
	}
	
	/**
	 * 设置session
	 * */
	public void setSession(HttpServletRequest request,HttpServletResponse response,String key,Object value) {
		request.getSession().setAttribute(key, value);
		if("datamart_user".equals(key)){
			request.getSession().setMaxInactiveInterval(3600);
		}
	}
	/**
	 * 获取session
	 * @return 
	 * */
	public Object getSession(HttpServletRequest request,HttpServletResponse response,String key) {
		return request.getSession().getAttribute(key);
	}
	/**
	 * 获取当前登录人
	 * @param request
	 * @param response
	 * @return
	 */
	public User getLoginUser(HttpServletRequest request){
		User user=(User) request.getSession().getAttribute(USER_INFO);
		if(user==null)
			user=new User();
		return user;
	}
	
	public User getUserByUid(String uid){
		if(StringUtils.isNotBlank(uid)){
			User u=userService.selectOne(uid);
			if(u!=null&&"1".equals(u.getUserType())){
				u.setHasAdmin(true);
			}
			return u;
		}
		return new User();
	}
	/**
	 * @time   2018年9月26日 上午11:24:33
	 * @author zuoqb
	 * @todo   判断是否为超级管理员
	 * @return_type   boolean
	 */
	public boolean isAdmin(HttpServletRequest request){
		User user=(User) request.getSession().getAttribute(USER_INFO);
		if(user==null)
			return false;
		if(!"1".equals(user.getUserType()))
			return false;
		return true;
	}
}
