package com.haier.datamart.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.SysVisitor;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.ISysVisitorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author ZhangJiang
 * @date 2018-12-14
 * @todo 用户签证
 */
@RestController
@RequestMapping("/api/sysVisitor")
@Api(value = "用户签证", description = " @author ZhangJiang")
public class SysVisitorController extends BaseController {

	@Autowired
	UserController userController;
	@Autowired
	public ISysVisitorService iSysVisitorService;

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "登录签证", notes = "登录签证")
	@RequestMapping(value = "/loginSign", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/api/sysVisitor/loginSign")
	public Object loginSign(HttpServletRequest request, HttpServletResponse response) {
		try {
			PublicResult<User> publicResult = (PublicResult<User>) userController.loginRoot(request, response);
			User user = publicResult.getData();
			if ("success".equals(publicResult.getMsg())) {
				String token = iSysVisitorService.loginSign(request, user);
				if (null != token) {
//					response.addHeader("token", token);
					return new PublicResult<>(PublicResultConstant.SUCCESS, token);
				} else {
					return new PublicResult<>(PublicResultConstant.ERROR,"记录保存失败");
				}
			} else {
				return publicResult;
			}
		} catch (Exception e) {
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}

	}
	
	
	
	@ApiOperation(value = "验证签证", notes = "验证签证")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "query") 
	})
	@RequestMapping(value = "/verifySign", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/api/sysVisitor/verifySign")
	public Object verifySign(HttpServletRequest request, HttpServletResponse response,String token) {
//		String token = request.getHeader("token")/*.split(" ")[1]*/;
//		if (token == null || token.equals("")) {
//			return new PublicResult<>(PublicResultConstant.INVALID_PARAM_EMPTY, null);
//		}
		try {
			SysVisitor visitor = iSysVisitorService.validateLoginStatus(token);
			String status = visitor.getLoginStatus();
			if("0".equals(status) ) {
				return iSysVisitorService.loggedInProcessing(visitor,token);
			}else if("1".equals(status) ) {
				return new PublicResult<>(PublicResultConstant.ERROR,"token过期");
			}else if ("2".equals(status) ) {
				return new PublicResult<>(PublicResultConstant.ERROR,"已在其他地方登录");
			}else {
				return new PublicResult<>(PublicResultConstant.ERROR,"未知结果");
			}
		} catch (Exception e) {
			return new PublicResult<>(PublicResultConstant.FAILED, "请联系管理员");
		}
	}
	
	@ApiOperation(value = "注销签证", notes = "注销签证")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "query") 
	})
	@RequestMapping(value = "/logoutSign", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/api/sysVisitor/logoutSign")
	public Object  logoutSign(HttpServletRequest request, HttpServletResponse response,String token) {
		int i = iSysVisitorService.logoutSign(token);
		if(i == 1) {
			return new PublicResult<>(PublicResultConstant.SUCCESS, "注销签证成功");
		}else {
			return new PublicResult<>(PublicResultConstant.ERROR, "注销签证失败");
		}
	}

}
