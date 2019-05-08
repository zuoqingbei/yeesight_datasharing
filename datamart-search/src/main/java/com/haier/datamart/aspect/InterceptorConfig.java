package com.haier.datamart.aspect;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.haier.datamart.config.Constant;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.IUserService;



public class InterceptorConfig  implements HandlerInterceptor{  
	@Autowired
	 private IUserService userService;
  
    private static final Logger log = LoggerFactory.getLogger(InterceptorConfig.class);  
  
    /** 
     * 进入controller层之前拦截请求 
     * @param httpServletRequest 
     * @param httpServletResponse 
     * @param o 
     * @return 
     * @throws Exception 
     */  
    @Override  
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {  
  
        //log.info("---------------------开始进入请求地址拦截----------------------------");  
       /*HttpSession session = httpServletRequest.getSession();  
        User user=(User) session.getAttribute(Constant.USER_INFO);
		if(user==null){
			Object userId=session.getAttribute("userId");
			if(userId!=null){
				user=userService.selectOne(userId+"");
			}
			if(user==null)
			user=new User();
		}
        if(StringUtils.isNotBlank(user.getId())){  
        	session.setAttribute(Constant.USER_INFO, user);
            return true;  
        }else{  
            PrintWriter printWriter = httpServletResponse.getWriter();  
            printWriter.write("{code:0,message:\"session is invalid,please login again!\"}");  
            return false;  
        }  */ 
    	 return true;  
    }  
  
    @Override  
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {  
       // log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");  
    }  
  
    @Override  
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {  
       // log.info("---------------视图渲染之后的操作-------------------------0");  
    }  
}  
