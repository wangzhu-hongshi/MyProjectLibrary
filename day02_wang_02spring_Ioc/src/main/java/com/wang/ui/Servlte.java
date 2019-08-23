package com.wang.ui;

import com.wang.service.impl.ServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Servlte {
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("bean.xml");
        ServiceImpl serviceImpl = context.getBean("serviceImpl", ServiceImpl.class);
        serviceImpl.findDao();
    }
}
