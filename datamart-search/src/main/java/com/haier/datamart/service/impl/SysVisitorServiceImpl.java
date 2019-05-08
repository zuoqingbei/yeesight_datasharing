package com.haier.datamart.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.SysVisitor;
import com.haier.datamart.entity.User;
import com.haier.datamart.mapper.SysVisitorMapper;
import com.haier.datamart.service.ISysVisitorService;
import com.haier.datamart.utils.JWT.TokenMgr;
import com.haier.datamart.utils.JWT.config.Constant;
import com.haier.datamart.utils.JWT.model.CheckResult;
import com.haier.datamart.utils.JWT.utils.ClientInfoUtil;
import com.haier.datamart.utils.JWT.utils.GsonUtil;

import nl.bitwalker.useragentutils.UserAgent;
/**
 * 服务实现类
 * @author ZhangJiang
 * @date 2018-12-14
 */
@Service
@Transactional
public class SysVisitorServiceImpl implements ISysVisitorService {

    @Autowired
    private SysVisitorMapper sysVisitorMapper;
    
    /**
     * 登录签证
     */
    @Override
	public String loginSign(HttpServletRequest request,User user) throws Exception {
    	
    	String userId = user.getId();
    	  
//    	String subject = GsonUtil.objectToJsonStr(new SubjectModel(UUIDUtils.getUuid(), user.getLoginName()));
    	
    	SysVisitor visitor = TokenMgr.createJWT(userId);
    	
    	String ip = ClientInfoUtil.getIp(request);
		String macAddress = ClientInfoUtil.getMacAddress(ip);
		
		SysVisitor sysVisitor = new SysVisitor();
		sysVisitor.setUserId(userId);
		sysVisitor.setMacAddress(macAddress);
    	sysVisitorMapper.updateLoggedInStatus(sysVisitor);
    	
		//解析agent字符串
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		
		visitor.setParameter( userId, ip, macAddress, GsonUtil.objectToJsonStr(userAgent),"0");
    	
		int i = sysVisitorMapper.insertSelective(visitor);
		if (1 == i) {
//			RedisUtils.set("com:haier:sysVisitor:" + visitor.getToken(), userId, Constant.JWT_TTL / 1000);
			return visitor.getToken();
		}
		
//		获取浏览器对象
//		Browser browser = userAgent.getBrowser();
//		获取操作系统对象
//		OperatingSystem operatingSystem = userAgent.getOperatingSystem();
//		System.out.println("userId:"+user.getId());
//		System.out.println("ip:"+ip);
//		System.out.println("macAddress:"+macAddress);
//		
//		System.out.println("浏览器名:"+browser.getName());
//		System.out.println("浏览器类型:"+browser.getBrowserType());
//		System.out.println("浏览器家族:"+browser.getGroup());
//		System.out.println("浏览器生产厂商:"+browser.getManufacturer());
//		System.out.println("浏览器使用的渲染引擎:"+browser.getRenderingEngine());
//		System.out.println("浏览器版本:"+userAgent.getBrowserVersion());
//		System.out.println("操作系统名:"+operatingSystem.getName());
//		System.out.println("访问设备类型:"+operatingSystem.getDeviceType());
//		System.out.println("操作系统家族:"+operatingSystem.getGroup());
//		System.out.println("操作系统生产厂商:"+operatingSystem.getManufacturer());
		
		return null;
	}
    
    /**
     * 根据token获取登录状态信息
     */
	@Override
	public SysVisitor validateLoginStatus(String token) {
		SysVisitor sysVisitor = new SysVisitor();
		sysVisitor.setToken(token);
		return sysVisitorMapper.select(sysVisitor);
	}
	
	/**
	 * 注销签证
	 */
	@Override
	public int logoutSign(String token) {
		SysVisitor sysVisitor = new SysVisitor();
		sysVisitor.setToken(token);
		Date now = new Date();
		sysVisitor.setQuitTime(now);
		return sysVisitorMapper.logoutSign(sysVisitor);
	}
	
	/**
	 * 已登录用户处理
	 */
	@Override
	public PublicResult<String> loggedInProcessing(SysVisitor visitor, String token) {
		Integer id = visitor.getId();
		Date expiration = visitor.getExpiration();
		visitor =new SysVisitor();
		visitor.setId(id);
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		if(now.after(expiration)) {
			visitor.setLoginTime(expiration);
			visitor.setLoginStatus("1");
			sysVisitorMapper.updateByPrimaryKeySelective(visitor);
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, "token过期");
		}
		// 验证JWT的签名，返回CheckResult对象
		CheckResult checkResult = TokenMgr.validateJWT(token);
		if (checkResult.isSuccess()) {
//			Claims claims = checkResult.getClaims();
			return new PublicResult<>(PublicResultConstant.SUCCESS, token);
		} else {
			switch (checkResult.getErrCode()) {
				// 签名过期，返回过期提示码
				case Constant.JWT_ERRCODE_EXPIRE:
					int i = sysVisitorMapper.updateByPrimaryKeySelective(visitor);
					if(1 == i) {
	//					response.addHeader("token", token);
						token = TokenMgr.createJWT(visitor.getUserId()).getToken();
						visitor.setToken(token);
						visitor.setExpiration(new Date(nowMillis + Constant.JWT_TTL));
						return new PublicResult<>(PublicResultConstant.SUCCESS, token);
					}else {
						return new PublicResult<>(PublicResultConstant.ERROR,"更新token过期时间失败");
					}
				// 签名验证不通过
				case Constant.JWT_ERRCODE_FAIL:
					return new PublicResult<>(PublicResultConstant.ERROR,"签名验证不通过");
				default:
					break;
			}
		}
		return new PublicResult<>(PublicResultConstant.ERROR,"请重新登录");
	}
	
	/**
	 * 定时注销过期签证
	 */
	@Override
	public int timingLogoutSign() {
		return sysVisitorMapper.timingLogoutSign();
	}
    
    
//	/**
//	 * 动态获取枚举属性测试
//	 */
//	@Test
//    public void getHello() throws Exception {
//    	try {
//        // 1.得到枚举类对象
//        Class<?> class1 = Class.forName("nl.bitwalker.useragentutils.OperatingSystem");
//        OperatingSystem operatingSystem = (OperatingSystem) Enum.valueOf((Class<OperatingSystem>) class1, "WINDOWS_MOBILE7");
//        
//        System.out.println("操作系统名:"+operatingSystem.getName());
//		System.out.println("访问设备类型:"+operatingSystem.getDeviceType());
//		System.out.println("操作系统家族:"+operatingSystem.getGroup());
//		System.out.println("操作系统生产厂商:"+operatingSystem.getManufacturer());
//        
//    	} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
   
}
