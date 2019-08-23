package com.wang.service.impl;

import com.wang.dao.impl.DaoImpl;
import com.wang.service.service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceImpl implements service {
    private DaoImpl  daoImpl;

    public void setDaoImpl(DaoImpl  daoImpl) {
        this. daoImpl =  daoImpl;
    }

    public void findDao() {
//        context=new ClassPathXmlApplicationContext("bean.xml");
//        DaoImpl daoImpl =(DaoImpl) context.getBean("DaoImpl");
        daoImpl.find();
    }
}
