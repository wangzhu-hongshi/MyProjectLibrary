package com.wang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 拦截器的类
 */
@Controller
@RequestMapping("/User")
public class UserInterceptor {
    @RequestMapping("/interceptor")
    public String interceptor(){
        System.out.println("interceptor执行了");
        return "success";
    }
}
