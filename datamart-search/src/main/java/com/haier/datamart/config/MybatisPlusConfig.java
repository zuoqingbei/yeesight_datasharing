package com.haier.datamart.config;

import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageHelper;

/**
 *
 * @author zuoqb
 * @since 2018-03-21
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.haier.datamart.mapper.**.mapper*")
public class MybatisPlusConfig {
    /**
     *   mybatis-plus分页插件
     */
   /* @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }*/
    /**
     * PageHelper分页插件
     * @return
     */
    @Bean  
    public PageHelper pageHelper() {  
       System.out.println("MyBatisConfiguration.pageHelper()");  
        PageHelper pageHelper = new PageHelper();  
        Properties p = new Properties();  
        p.setProperty("offsetAsPageNum", "true");  
        p.setProperty("rowBoundsWithCount", "true");  
        p.setProperty("reasonable", "true");  
        pageHelper.setProperties(p);  
        return pageHelper;  
    }  
}
