package com.wang;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloControlle {
    @RequestMapping(path = "/hello")
    public String sayHello(){
        System.out.println("hello spring MVC");
        return "success";
    }
}
