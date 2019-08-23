package com.itheima.test;

import com.itheima.domain.Account;
import com.itheima.service.IAccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class AccountTest {
    @Test
    public void Test(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("bean.xml");
        IAccountService service = ac.getBean("accountService", IAccountService.class);
        List<Account> list = service.findAllAccount();
        for (Account account : list) {
            System.out.println(account);
        }
    }
    @Test
    public void Test2(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("bean.xml");
        IAccountService service = ac.getBean("accountService", IAccountService.class);
        service.transfer("aaa","bbb",100f);
    }
}
