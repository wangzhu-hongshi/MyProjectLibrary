package com.wang.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
public class MyInterceptor implements HandlerInterceptor {
    /**
     * 预处理的方法 在controller方法前执行
     * 返回true 表示放行 反之不放行
     * 可以做业务操作
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器把你拦截下来了 ");
        return true;
    }

    /**
     * 后处理的方法 在控制方法回来后执行
     * 可以用它做跳转页面  不执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("你回来我就执行");

    }

    /**
     * 在返回成功页面后执行
     * 可以做关闭资源等操作
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("全部结束后我在执行");
    }
}
