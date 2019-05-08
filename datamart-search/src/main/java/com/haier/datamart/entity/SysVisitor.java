package com.haier.datamart.entity;

import java.util.Date;

public class SysVisitor {
    private Integer id;

    private String token;

    private String userId;

    private String ip;

    private String macAddress;

    private Date loginTime;

    private Date quitTime;

    private Date issuedAt;

    private Date expiration;

    private String loginStatus;

    private String userAgent;
    
    public SysVisitor() {
	}

	public SysVisitor(String token, String userId, String ip, String macAddress, String userAgent, Date issuedAt,
			Date expiration, String loginStatus) {
		this.token = token;
		this.userId = userId;
		this.ip = ip;
		this.macAddress = macAddress;
		this.userAgent = userAgent;
		this.issuedAt = issuedAt;
		this.expiration = expiration;
		this.loginStatus = loginStatus;
	}

	public void setParameter(String userId, String ip, String macAddress, String userAgent, String loginStatus) {
		this.userId = userId;
		this.ip = ip;
		this.macAddress = macAddress;
		this.userAgent = userAgent;
		this.loginStatus = loginStatus;
	}
	
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress == null ? null : macAddress.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(Date quitTime) {
        this.quitTime = quitTime;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus == null ? null : loginStatus.trim();
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent == null ? null : userAgent.trim();
    }
}