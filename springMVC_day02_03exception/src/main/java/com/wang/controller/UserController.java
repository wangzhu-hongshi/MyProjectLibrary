package com.wang.controller;

import com.wang.exception.SysException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * MVC的异常处理
 *
 */
@Controller
@RequestMapping("/User")
public class UserController {
    @RequestMapping("/exception")
    public String exception() throws SysException {
        System.out.println("异常处理执行了");
        try{
            //模拟异常
            int i=10/0;
        }catch (Exception e){
           e.printStackTrace();
           throw new SysException("查询出现异常了。。。");

        }
        return "success";
    }

}
