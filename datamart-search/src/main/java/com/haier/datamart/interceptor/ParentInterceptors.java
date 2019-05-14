package com.haier.datamart.interceptor;

 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
 

/**
 * Package: com.hailian.interceptor.AppInterceptors
 * Description: 描述
 * Copyright: Copyright (c) 2017
 *
 * @author lv bin
 * Date: 2018/1/19 14:11
 * Version: V1.0.0
 */

@Configuration
public class ParentInterceptors extends WebMvcConfigurerAdapter {
	 
	@Bean
	public  com.haier.datamart.interceptor.LoginInterceptors  loginCeptor(){
	return new  com.haier.datamart.interceptor.LoginInterceptors();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(
				loginCeptor()
				).excludePathPatterns("/user/login","/user/logout","/user/getUserByToken");
	}
	
 }
 