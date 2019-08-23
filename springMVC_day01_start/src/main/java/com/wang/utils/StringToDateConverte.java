package com.wang.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义类型转换器  时间转换
 */
public class StringToDateConverte implements Converter<String, Date>{

    @Override
    public Date convert(String source) {
        if(source==null){
            throw new RuntimeException("请传入数据");
        }
        DateFormat format=new SimpleDateFormat("yyy-MM-dd");
        try {
              return format.parse(source);
        } catch (ParseException e) {
            throw new RuntimeException("传入的数据格式错误");
        }

    }
}
