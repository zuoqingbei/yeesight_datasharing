package com.haier.datamart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.redis.RedisUtils;

/**
 * 
 * 补录配置HTTP接口
 * zuoqb
 */
@RestController
public class RedisController {
	@PostMapping(value = "/redis/insert", produces = { "application/json;charset=UTF-8" })
	public Object insert(@RequestParam(value="key",required = true) String key,@RequestParam(value="value",required = true) String value) {
		boolean b=RedisUtils.set(key, value);
		if(b){
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		}else{
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
	}
	
	/**
	 * 
	 * @time   2018年11月19日 下午1:55:39
	 * @author zuoqb
	 * @todo   获取redis数据
	 * @param  @param key
	 * @param  @return
	 * @return_type   Object
	 */
	@GetMapping(value = "/redis/getDataByKey", produces = { "application/json;charset=UTF-8" })
	public Object getDataByKey(@RequestParam(value="key",required = true) String key) {
		Object value=RedisUtils.get(key);
		return new PublicResult<>(PublicResultConstant.SUCCESS, value);
	}
	
}
