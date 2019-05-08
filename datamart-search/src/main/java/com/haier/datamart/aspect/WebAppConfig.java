package com.haier.datamart.aspect;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/** 
 * 
 * 注册拦截器 
 * Created by SYSTEM on 2017/8/16. 
 */  
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {  
  
    @Override  
    public void addInterceptors(InterceptorRegistry registry) {  
        //注册自定义拦截器，添加拦截路径和排除拦截路径  
        registry.addInterceptor(new InterceptorConfig()).addPathPatterns("/**")
        .excludePathPatterns("/api/doc*/**")
        .excludePathPatterns("/api/enteringTableSettingDetailData/**")
        .excludePathPatterns("/user/**")
        .excludePathPatterns("/searchIndex/**")
        .excludePathPatterns("/data/**")
        .excludePathPatterns("/entering/**")
        .excludePathPatterns("/hot/search/**")
        .excludePathPatterns("/comment/**")
        .excludePathPatterns("/api/searchIndexMagicRevise/**")
        .excludePathPatterns("/report/**")
        .excludePathPatterns("/api/SearchReports2/**")
        .excludePathPatterns("/api/searchReportsFile/**")
        .excludePathPatterns("/search/**")
        .excludePathPatterns("/searchCategory/**")
        .excludePathPatterns("/main/add/**")
        .excludePathPatterns("/swagger*/**")
        .excludePathPatterns("/api-docs/**")
        .excludePathPatterns("/v2/api-docs/**")
        .excludePathPatterns("/api/mail**/**")
        
        ;
        
        
    }  
} 
