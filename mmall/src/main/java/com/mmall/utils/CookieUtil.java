package com.mmall.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie 的工具类
 *  读 从request 里读
 *  写 从response 里写
 *
 */
@Slf4j//记录日志的注解
public class CookieUtil {

    //默认的 二级域名
    private final static String COOKIE_DOMAIN="mmall.com";
    //默认的cookie的名字
    private final static String COOKIE_NAME="mmall_login_token";
    //读取cookie
    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie ck : cookies) {
                //log.info("write cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    log.info("write cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    //存入cookie 并发送
    public static void writeloginToken(HttpServletResponse response,String token){
        //创建一个cookie 传入默认的name 和 token
        Cookie ck=new Cookie(COOKIE_NAME,token);
        //设置二级域名
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");//
        ck.setHttpOnly(true);// 不能通过脚本访问Cookie、HttpOnly属性在一定程度上可以防止XSS攻击
        //如果这个maxage不设置的话 cookie就不会写入硬盘 而是写在内存
        ck.setMaxAge(60*60*24*365);//-1代表 永久
        log.info("write cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
        response.addCookie(ck);//发送cookie
    }

    /**
     * 删除cookie
     * @param response
     * @param request
     */
    public static void delloginToken(HttpServletResponse response,HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if(cks!=null){
            for (Cookie ck : cks) {
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);//设置 0 代表是 删除 cookie
                    log.info("write cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    response.addCookie(ck);//发送cookie
                    return;
                }
            }
        }
    }
}
