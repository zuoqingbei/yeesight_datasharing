package com.haier.datamart;

import java.util.Properties;

import javax.servlet.MultipartConfigElement;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageHelper;

/**
 * @Description: 指定项目为springboot，由此类当作程序入口，自动装配 web 依赖的环境
 * @author zuoqb
 * @since 2018-05-03
 */
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan({"com.haier.datamart.mapper"})
@EnableCaching
public class SpringbootApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(SpringbootApplication.class);
	}

	@Bean
	PageHelper pageHelper() {
		// 分页插件
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("returnPageInfo", "check");
		properties.setProperty("params", "count=countSql");
		pageHelper.setProperties(properties);

		// 添加插件
		new SqlSessionFactoryBean().setPlugins(new Interceptor[] { pageHelper });
		return pageHelper;
	}
	// 文件上传限制
		@Bean
		public MultipartConfigElement multipartConfigElement() {
			MultipartConfigFactory factory = new MultipartConfigFactory();
			// 设置文件大小限制 ,超出设置页面会抛出异常信息，
			// 这样在文件上传的地方就需要进行异常信息的处理了;
			factory.setMaxFileSize("100MB"); // KB,MB
			/// 设置总上传数据总大小
			factory.setMaxRequestSize("100MB");
			// Sets the directory location where files will be stored.
			// factory.setLocation("路径地址");
			return factory.createMultipartConfig();
		}

		//文件下载
		@Bean
		public HttpMessageConverters restFileDownloadSupport() {
			ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
			return new HttpMessageConverters(arrayHttpMessageConverter);
		}

	public static void main(String[] args) {
//		new SpringApplicationBuilder(SpringbootApplication.class).web(true).run(args);
		SpringApplication.run(SpringbootApplication.class, args);
	}
}