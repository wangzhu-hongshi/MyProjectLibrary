package com.wang.service.impl;

import com.wang.dao.impl.DaoImpl;
import com.wang.service.service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ServiceImpl implements service {
    @Resource(name = "daoImpl")
    private DaoImpl  daoImpl;


    public void findDao() {
//        context=new ClassPathXmlApplicationContext("bean.xml");
//        DaoImpl daoImpl =(DaoImpl) context.getBean("DaoImpl");
//        daoImpl.find();
        System.out.println("被调用了！！");
    }
}
