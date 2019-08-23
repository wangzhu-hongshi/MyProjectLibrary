package com.wang.controller;

import com.wang.controller.exception.SysException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义异常处理器 需要继承 HandlerExceptionResolver
 */
public class SysExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception xe) {
        xe.printStackTrace();
        SysException se=null;
        if(xe instanceof SysException){
            se=(SysException)xe;
        }else {
            se=new SysException("系统正在维护，请联系管理员！");
        }
        ModelAndView mv=new ModelAndView();
        mv.addObject("message",se.getMessage());
        mv.setViewName("error");
        return mv;
    }
}
