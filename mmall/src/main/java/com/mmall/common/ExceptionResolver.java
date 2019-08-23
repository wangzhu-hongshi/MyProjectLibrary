package com.mmall.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *处理全局异常  防止信息泄露 异常处理器
 */
@Slf4j
@Component//把异常处理器 注入到 Spirng MVC
public class ExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.error("{} Exception",httpServletRequest.getRequestURI(),e);
        //将其转化为json
        ModelAndView modelAndView=new ModelAndView(new MappingJacksonJsonView());

        modelAndView.addObject("status",ResponseCode.ERROR.getCode());
        modelAndView.addObject("mag","接口异常，详情请查看服务端！");
        modelAndView.addObject("data",e.toString());
        return modelAndView;
    }
}
