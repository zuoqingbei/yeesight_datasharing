package com.haier.datamart.jenkinssupport;

import com.alibaba.fastjson.JSON;
import com.haier.datamart.exception.BusinessException;
import com.haier.datamart.service.impl.AirflowKettleSupportServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 刘志龙
 * airflow的操作
 */
@Component
public class JenkinsHandler {
    @Value("${jenkinssupport.JenkinsHandler.jinkinsIp}")
    private String jinkinsIp;
    @Value("${jenkinssupport.JenkinsHandler.jinkinsUser}")
    private String jinkinsUser;
    @Value("${jenkinssupport.JenkinsHandler.jinkinsPwd}")
    private String jinkinsPwd;
    private static String JINKINS_ADDRESS = "10.138.22.188:15000";
    private static String JINKINS_USER="shuju";
    private static String JINKINS_PWD="shuju";
    private static  Logger logger = LoggerFactory.getLogger(AirflowKettleSupportServiceImpl.class);

    @PostConstruct
    public void init(){
    	JINKINS_ADDRESS = jinkinsIp;
    	JINKINS_USER=jinkinsUser;
    	JINKINS_PWD=jinkinsPwd;
    }
    /**
     * 启动一个调度
     * @param dagName
     */
    public static int build(String dagName){
        String url = "http://"+JINKINS_ADDRESS+"/job/"+dagName+"/build";
        logger.info("do run:"+url);
        Map<String,String> params = new HashMap<>();
        int result =  httpClientWithBasicAuth(JINKINS_USER,JINKINS_PWD,url,params);
        System.out.println(result);
        return result;
    }

    /**
     * 构造Basic Auth认证头信息
     * 
     * @return
     */
    public static int httpClientWithBasicAuth(String username, String password,
			String uri, Map<String, String> paramMap) {
		try {
			// 创建HttpClientBuilder
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
			HttpPost httpPost = new HttpPost(uri);
			// 添加http头信息
			httpPost.addHeader(
					"Authorization",
					"Basic "
							+ Base64.getUrlEncoder().encodeToString(
									(username + ":" + password).getBytes()));

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();

			paramMap.forEach((k, v) -> {
				builder.addPart(k, new StringBody(v,
						ContentType.MULTIPART_FORM_DATA));
			});
			HttpEntity postEntity = builder.build();
			httpPost.setEntity(postEntity);
			String result = "";
			HttpResponse httpResponse = null;
			HttpEntity entity = null;
			try {
				httpResponse = closeableHttpClient.execute(httpPost);
				entity = httpResponse.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity);
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				throw new BusinessException("调用接口失败");
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException("调用接口失败");
			}

			// 关闭连接
			closeableHttpClient.close();

			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			throw new BusinessException("调用接口失败");
		}
		return 0;
	}
   

}
