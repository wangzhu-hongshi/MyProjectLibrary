package com.wang.lukymoney.controller;

import com.wang.lukymoney.config.LimitConifg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/hello")
public class helloController {

    @Autowired
    private LimitConifg conifg;

    @GetMapping("/say")
    public String say(@RequestParam(value = "id",required = false,defaultValue = "0") Integer id){
        return "id:"+id;
    }
}
