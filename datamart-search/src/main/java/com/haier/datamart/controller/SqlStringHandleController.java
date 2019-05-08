package com.haier.datamart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.service.SqlStringHandleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: sql字符串处理 Controller层
 * @author: ZhangJiang
 * @date: 2018-12-09 下午6:08:02
 */
@Api(value = "sql字符串处理 ", description = "sql字符串处理 @author: ZhangJiang")
@Configuration
@Component
@EnableScheduling
@RestController
public class SqlStringHandleController extends BaseController {
	@Autowired
	private SqlStringHandleService sqlStringHandleService;

	/**
	 * 测试url:
	 * 	http://localhost:8080/fieldNames/list 
	 * 		id:1
	 *		sql:SELECT * FROM import_data_bhrate
	 * 根据sql字符串获取查询字段名
	 * 
	 * @param request
	 * @param id      表记录id
	 * @param sql     sql字符串
	 */
	@ApiOperation(value = "根据sql字符串获取查询字段名", notes = "根据sql字符串获取查询字段名")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "表记录id", required = true, paramType = "query"),
		@ApiImplicitParam(name = "sql", value = "sql字符串", required = true, paramType = "query") 
	})
	@PostMapping(value = "/fieldNames/list", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/fieldNames/list")
	public Object getFieldNamesBySql(String id, String sql) {

		List<String> fieldNames = sqlStringHandleService.getFieldNamesBySql(id, sql);

		return new PublicResult<>(PublicResultConstant.SUCCESS, fieldNames);
	}
	
	/**
	 * 根据sql字符串和连接数据源id,连接数据库,获取结果集
	 * @param id
	 * @param sql
	 * @return
	 */
	@ApiOperation(value = "根据sql字符串和连接数据源id,连接数据库,获取结果集", notes = "根据sql字符串和连接数据源id,连接数据库,获取结果集")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "表记录id", required = true, paramType = "query"),
		@ApiImplicitParam(name = "sql", value = "sql字符串", required = true, paramType = "query") 
	})
	@PostMapping(value = "/fieldNames/sqlQueryReturnList", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/fieldNames/sqlQueryReturnList")
	public Object getSqlQueryReturnList(String id, String sql) {

		List<List<String>> list = sqlStringHandleService.getSqlQueryReturnList(id, sql);

		return new PublicResult<>(PublicResultConstant.SUCCESS, list);
	}
	
}
