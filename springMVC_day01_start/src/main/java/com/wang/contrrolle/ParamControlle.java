package com.wang.contrrolle;

import com.wang.domain.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/param")
public class ParamControlle {
    @RequestMapping("/setParam")
    public String setParam(Param param){
        System.out.println(param);
        return "success";
    }
    @RequestMapping("/stringTo")
    public String stringTo(Param param){
        System.out.println(param);
        return "success";
    }
}
