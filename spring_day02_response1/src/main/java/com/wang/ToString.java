package com.wang;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/User")
public class ToString {
    @RequestMapping("/toString")
    public String toString(){
        System.out.println("被执行了");
        return "success";
    }
}
