package com.wang.test;

import com.wang.domain.Account;
import com.wang.service.AccountService;
import com.wang.service.impl.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 账户的测试类
 */

/**
 * 测试类的整合
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")//使用的xml注解的方式
public class AccountTest {
    @Autowired
    private AccountServiceImpl service;
    @Test
    public void FindAll(){
        List<Account> all = service.findAll();
        for (Account vlaue : all) {
            System.out.println(vlaue);
        }
    }
}
