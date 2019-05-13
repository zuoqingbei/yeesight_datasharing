package com.haier.datamart.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Cookie 工具类
 *
 */
public final class CookieUtil {

  /**
   * 得到Cookie的值, 不编码
   * 
   * @param request
   * @param cookieName
   * @return
   */
  public static String getCookieValue(HttpServletRequest request, String cookieName) {
    return getCookieValue(request, cookieName, false);
  }

  /**
   * 得到Cookie的值,
   * 
   * @param request
   * @param cookieName
   * @return
   */
  public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
    Cookie[] cookieList = request.getCookies();
    if (cookieList == null || cookieName == null) {
      return null;
    }
    String retValue = null;
    try {
      for (int i = 0; i < cookieList.length; i++) {
        if (cookieList[i].getName().equals(cookieName)) {
          if (isDecoder) {
            retValue = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
          } else {
            retValue = cookieList[i].getValue();
          }
          break;
        }
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return retValue;
  }


  /**
   * 删除Cookie带cookie域名
   */
  public static void deleteCookie(HttpServletResponse response, String cookieName, HttpServletRequest request) {
    setCookie(response, cookieName, "", -1, false, request);
  }
  
  public static void deleteCookie(HttpServletResponse response, String cookieName, String domainName) {
    setCookie(response, cookieName, "", -1, false, domainName);
  }

  /**
   * 设置Cookie的值，并使其在指定时间内生效
   * 
   * @param cookieMaxage cookie生效的最大秒数
   */
  public static  void setCookie(HttpServletResponse response, String cookieName, String cookieValue,
      int cookieMaxage, boolean isEncode, HttpServletRequest request) {
    if (null != request) {// 设置域名的cookie
      String domainName = getDomainName(request);
      setCookie(response, cookieName, cookieValue, cookieMaxage, isEncode, domainName);
    }else {
      setCookie(response, cookieName, cookieValue, cookieMaxage, isEncode, "");
    }
  }
  public static void setCookie(HttpServletResponse response, String cookieName,String value){
      Cookie cookie = new Cookie(cookieName,value);
      cookie.setPath("/");
      cookie.setMaxAge(3600);
      response.addCookie(cookie);
  }
  public static void deleteCookie(HttpServletResponse response, String cookieName){
	  Cookie cookie = new Cookie(cookieName, "");
      cookie.setPath("/");
      cookie.setMaxAge(-1);
      response.addCookie(cookie);
  }
  public static  void setCookie(HttpServletResponse response, String cookieName, String cookieValue,
      int cookieMaxage, boolean isEncode, String domainName) {
    try {
      if (cookieValue == null) {
        cookieValue = "";
      } else if (isEncode) {
        cookieValue = URLEncoder.encode(cookieValue, "UTF-8");
      }
      Cookie cookie = new Cookie(cookieName, cookieValue);
      if(StringUtils.isNotBlank(domainName)) {
        cookie.setDomain(domainName);
      }
      cookie.setPath("/");
      response.addCookie(cookie);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 得到cookie的域名
   */
  private static  String getDomainName(HttpServletRequest request) {
    String domainName = null;

    String serverName = request.getRequestURL().toString();
    if (serverName == null || serverName.equals("")) {
      domainName = "";
    } else {
      final int end = serverName.lastIndexOf("/");
      serverName = serverName.substring(0, end);
      final String[] domains = serverName.split("\\.");
      int len = domains.length;
      if (len > 3) {
        // www.xxx.com.cn
        domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
      } else if (len <= 3 && len > 1) {
        // xxx.com or xxx.cn
        domainName = "." + domains[len - 2] + "." + domains[len - 1];
      } else {
        domainName = serverName;
      }
    }

    if (domainName != null && domainName.indexOf(":") > 0) {
      String[] ary = domainName.split("\\:");
      domainName = ary[0];
    }
    return domainName;
  }

}
