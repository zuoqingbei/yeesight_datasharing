package com.haier.datamart.interceptor;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.config.Constant;
import com.haier.datamart.controller.BaseController;
import com.haier.datamart.controller.UserController;
import com.haier.datamart.entity.User;
import com.haier.datamart.utils.CookieUtil;

public class  LoginInterceptors extends HandlerInterceptorAdapter {
	@Autowired
	UserController baseController;
    /**
     * 默认请求request header 头部存放 token 名称
     */
    public static String DEFAULT_TOKEN_NAME = "token";
	private static List<String> notNeedLogin=Arrays.asList(
			"/user/login","/user/logout","/swagger-resources","/user/getUserByToken"
    );
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	 
		 User flagUser = (User) baseController.getSession(request, response,  Constant.USER_INFO);
		if(flagUser==null||StringUtils.isEmpty(flagUser.getId())) {
			//setSession(request, response, , user2);
		    String uri = request.getRequestURI();
		    //String to = request.getParameter("to");
		    String token = CookieUtil.getCookieValue(request, LoginInterceptors.DEFAULT_TOKEN_NAME);
		    boolean isPassToken = false;
		    if(StringUtils.isNotBlank(token)){
		    	User user = UserController.paserToken(token);
		    	if(user!=null&&StringUtils.isNotEmpty(user.getId())) {
		    		baseController.setSession(request, response, Constant.USER_INFO, user);
		    		isPassToken = true;
		    	}
		    }
		    if(!isPassToken) {
		    		//当前用户没有登录  判断是否需要拦截
		        	 if(!isPass(uri)){
		    			
		    			 response.setContentType("application/json;charset=utf-8");
		        		//response.sendRedirect("/user/logout");
		        		//baseController.logout(request, response);
		    			 //response.reset(); 
		    			 PrintWriter pw = response.getWriter();
		        		 Map<String,Object> map = new HashMap<>();
		        		 map.put("data",  PublicResultConstant.UNAUTHORIZED);
		        		 pw.write(map.entrySet().toString().replace("=", ":").replace("[", "{").replace("]", "}"));
		        		 return false;
		        	 }
		    }
		}
		 
    	
    return super.preHandle(request, response, handler);
}
    
    public static boolean isPass(String url){
    	for(String unPass:notNeedLogin){
    		if(url.indexOf(unPass)!=-1){
    			return true;
    		}
    	}
    	return false;
    }
}
