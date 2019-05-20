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
			"/user/login","/user/logout","/swagger-resources","/user/getUserByToken","/v2/api-docs","/main/count"
    );
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String uri = request.getRequestURI();
    	String clientToken = CookieUtil.getCookieValue(request, LoginInterceptors.DEFAULT_TOKEN_NAME);
    	boolean isPass = false;
    	//判断是否需要拦截
	   	if(!isPass(uri)){
	    	if(StringUtils.isEmpty(clientToken)) {
	    		baseController.clearSessionUser(request, response,  Constant.USER_INFO);
	    		baseController.clearSessionUser(request, response,  Constant.TOKEN_INFO);
	    	}else {
	    		User flagUser = (User) baseController.getSession(request, response,  Constant.USER_INFO);
	    		if(flagUser==null||StringUtils.isEmpty(flagUser.getId())) {
	    		    User user = UserController.paserToken(clientToken);
	    		    if(user!=null&&StringUtils.isNotEmpty(user.getId())&&StringUtils.isNotEmpty(user.getToken())) {
	    		    	baseController.setSession(request, response, Constant.USER_INFO, user);
	    		    	baseController.setSession(request, response, Constant.TOKEN_INFO, clientToken);
	    		    	isPass = true;
	    		    }
	    		}else {	  
	    			String localToken = baseController.getSession(request, response,  Constant.TOKEN_INFO)+"";
	    			if(clientToken.equals(localToken)) {
	    				isPass = true;
	    			}
	    		}
	    	}
	   	}else {
	   		isPass = true;
	   	}
   	 	if(!isPass){
			 response.setContentType("application/json;charset=utf-8");
	   		 //response.sendRedirect("/user/logout");
	   		 //baseController.logout(request, response);
			 //response.reset(); 
			 PrintWriter pw = response.getWriter();
	   		 Map<String,Object> map = new HashMap<>();
	   		 map.put("data",  PublicResultConstant.UNAUTHORIZED);
	   		 pw.write(map.entrySet().toString().replace("=", ":").replace("[", "{").replace("]", "}"));
	   		 return isPass;
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
