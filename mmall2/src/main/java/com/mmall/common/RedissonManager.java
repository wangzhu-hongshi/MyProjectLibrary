package com.mmall.common;

import com.mmall.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * redisson 初始化类
 *
 */
@Component
@Slf4j
public class RedissonManager {
    private Config config=new Config();


    private Redisson redisson=null;

    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redis1Port=Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));
    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));

    public Redisson getRedisson() {
        return redisson;
    }

    @PostConstruct
    private void init(){
        try{
            //配置 redis信息
            config.useSingleServer().setAddress(new StringBuffer().append(redis1Ip).append(":").append(redis1Port).toString());
            redisson=(Redisson) redisson.create(config);//返回redisson对象

            log.info("初始化Redisson结束");
        }catch (Exception e){
             log.info("redisson init error",e);
        }
    }
}
