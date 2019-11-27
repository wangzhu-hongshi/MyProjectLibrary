package com.mmall.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 读取配置类的工具类
 * Created by geely
 */
public class PropertiesUtil {
    //定义一个日志对象 是的
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    //定义静态 配置类的变量
    private static Properties props;

    static {
        String fileName = "mmall.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常",e);
        }
    }

    /**
     * 获取在配置类中配置的键的值
     * @param key
     * @return
     */
    public static String getProperty(String key){
        String value = props.getProperty(key.trim());//.trim()方法 用于删除字符串的头尾空白符
        //判单 value 是否是null 或者空字符串
        if(StringUtils.isBlank(value)){
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key,String defaultValue){

        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            //如果 value 是空 则返回传进来的默认值
            value = defaultValue;
        }
        return value.trim();
    }



}
