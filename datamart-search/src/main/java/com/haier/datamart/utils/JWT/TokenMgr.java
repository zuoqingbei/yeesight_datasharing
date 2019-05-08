package com.haier.datamart.utils.JWT;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.haier.datamart.entity.SysVisitor;
import com.haier.datamart.utils.JWT.config.Constant;
import com.haier.datamart.utils.JWT.model.CheckResult;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * 管理所有的Token
 * @author XY
 *
 */
@SuppressWarnings("restriction")
public class TokenMgr {
	
	public static SecretKey generalKey() {
		byte[] encodedKey = Base64.decode(Constant.JWT_SECERT);
	    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	    return key;
	}

	/**
	 * 签发JWT
	 * @param subject
	 * @return
	 */
	public static SysVisitor createJWT(String subject) {
		long ttlMillis = Constant.REFRESH_TIME ;
		SysVisitor visitor = new SysVisitor();
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		
		SecretKey secretKey = generalKey();
		
		JwtBuilder builder = Jwts.builder()
				.setId(Constant.JWT_ID)
				.setSubject(subject)
				.setIssuedAt(now)
				.signWith(signatureAlgorithm, secretKey);
		
		visitor.setIssuedAt(now);
		visitor.setLoginTime(now);
		visitor.setLoginStatus("0");
		
		if (ttlMillis >= 0) {
			visitor.setExpiration(new Date(nowMillis + Constant.JWT_TTL));
			builder.setExpiration(new Date(nowMillis + ttlMillis));
		}
		visitor.setToken(builder.compact());
		return visitor;
	}
	
	/**
	 * 验证JWT
	 * @param jwtStr
	 * @return
	 */
	public static CheckResult validateJWT(String jwtStr) {
		CheckResult checkResult = new CheckResult();
		Claims claims = null;
		try {
			claims = parseJWT(jwtStr);
			checkResult.setSuccess(true);
			checkResult.setClaims(claims);
		} catch (ExpiredJwtException e) {
			checkResult.setErrCode(Constant.JWT_ERRCODE_EXPIRE);
			checkResult.setSuccess(false);
		} catch (SignatureException e) {
			checkResult.setErrCode(Constant.JWT_ERRCODE_FAIL);
			checkResult.setSuccess(false);
		} catch (Exception e) {
			checkResult.setErrCode(Constant.JWT_ERRCODE_FAIL);
			checkResult.setSuccess(false);
		}
		return checkResult;
	}
	
	/**
	 * 
	 * 解析JWT字符串
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception {
		SecretKey secretKey = generalKey();
		return Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(jwt)
			.getBody();
	}
	
	/**
	 * 生成subject信息
	 * @param user
	 * @return
	 */
//	public static String generalSubject(SubjectModel sub){
//		return GsonUtil.objectToJsonStr(sub);
//	}
}
