package com.wang;

import com.wang.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext ac=new ClassPathXmlApplicationContext("bean.xml");
        AccountService service = ac.getBean("accountService", AccountService.class);
        service.delete(1);
        service.insert();
        service.update();
    }
}
