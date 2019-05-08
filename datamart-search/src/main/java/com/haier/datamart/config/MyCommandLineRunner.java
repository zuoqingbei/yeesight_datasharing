package com.haier.datamart.config;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haier.datamart.annotation.Pass;
import com.haier.datamart.base.Constant;
import com.haier.datamart.utils.ComUtil;

/**
 * @author zuoqb
 * @Since 2018-05-10
 */
/*@Component*/
@Deprecated
public class MyCommandLineRunner implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(MyCommandLineRunner.class);

	@Value("${controller.scanPackage}")
	private String scanPackage;

	@Override
	public void run(String... args) throws Exception {
		doScanner(scanPackage);
		Set<String> urlAndMethodSet  =new HashSet<>();
		for (String aClassName:Constant.METHOD_URL_SET) {
			Class<?> clazz = Class.forName(aClassName);
			String baseUrl = "";
			String[] classUrl ={};
			if(!ComUtil.isEmpty(clazz.getAnnotation(RequestMapping.class))){
				classUrl=clazz.getAnnotation(RequestMapping.class).value();
			}
			Method[] methods = clazz.getMethods();
			for (Method method:methods) {
				if(method.isAnnotationPresent(Pass.class)){
					String [] methodUrl = null;
					StringBuilder sb  =new StringBuilder();
					if(!ComUtil.isEmpty(method.getAnnotation(PostMapping.class))){
						methodUrl=method.getAnnotation(PostMapping.class).value();
						if(ComUtil.isEmpty(methodUrl)){
							methodUrl=method.getAnnotation(PostMapping.class).path();
						}
						baseUrl=getRequestUrl(classUrl, methodUrl, sb,"POST");
					}else if(!ComUtil.isEmpty(method.getAnnotation(GetMapping.class))){
						methodUrl=method.getAnnotation(GetMapping.class).value();
						if(ComUtil.isEmpty(methodUrl)){
							methodUrl=method.getAnnotation(GetMapping.class).path();
						}
						baseUrl=getRequestUrl(classUrl, methodUrl, sb,"GET");
					}else if(!ComUtil.isEmpty(method.getAnnotation(DeleteMapping.class))){
						methodUrl=method.getAnnotation(DeleteMapping.class).value();
						if(ComUtil.isEmpty(methodUrl)){
							methodUrl=method.getAnnotation(DeleteMapping.class).path();
						}
						baseUrl=getRequestUrl(classUrl, methodUrl, sb,"DELETE");
					}else if(!ComUtil.isEmpty(method.getAnnotation(PutMapping.class))){
						methodUrl=method.getAnnotation(PutMapping.class).value();
						if(ComUtil.isEmpty(methodUrl)){
							methodUrl=method.getAnnotation(PutMapping.class).path();
						}
						baseUrl=getRequestUrl(classUrl, methodUrl, sb,"PUT");
					}else {
						methodUrl=method.getAnnotation(RequestMapping.class).value();
						baseUrl=getRequestUrl(classUrl, methodUrl, sb,RequestMapping.class.getSimpleName());
					}
					if(!ComUtil.isEmpty(baseUrl)){
						urlAndMethodSet.add(baseUrl);
					}
				}
			}
		}
		Constant.METHOD_URL_SET=urlAndMethodSet;
		logger.info("@Pass:"+urlAndMethodSet);
	}

	private String  getRequestUrl(String[] classUrl, String[] methodUrl, StringBuilder sb,String requestName) {
		if(!ComUtil.isEmpty(classUrl)){
            for (String url:classUrl) {
                sb.append(url+"/");
            }
        }
		for (String url:methodUrl) {
            sb.append(url);
        }
		return sb.toString().replaceAll("/+", "/")+":"+requestName;
	}

	private void doScanner(String packageName) {
		//把所有的.替换成/
		URL url  =this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
		File dir = new File(url.getFile());
		for (File file : dir.listFiles()) {
			if(file.isDirectory()){
				//递归读取包
				doScanner(packageName+"."+file.getName());
			}else{
				String className =packageName +"." +file.getName().replace(".class", "");
				Constant.METHOD_URL_SET.add(className);
			}
		}
	}

}
