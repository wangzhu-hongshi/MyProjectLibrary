package com.wang.dao;

import com.wang.domain.Customer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.statements.SpringRepeat;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)//声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的配置信息
public class CustomerDaoTest {
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testFindOne(){
       Customer customer=customerDao.findOne(2l);
        System.out.println(customer);

    }
    @Test
    public void testCount(){
        long count = customerDao.count();

    }

    /**
     * getOne是懒加载
     * 需要事务支持
     */
    @Test
    @Transactional

    public void testGetOne(){
        Customer one = customerDao.getOne(2l);

    }
    @Test
    @Transactional
    @Rollback(value = false)
    public void testUpdateCustomer(){
        customerDao.updateCustomer(2l,"宏诗");
    }

    /*
    方法命名规则查询
     */
    @Test
    public void testCustName(){
        Customer custName = customerDao.findByCustName("宏诗");
        System.out.println(custName);
    }
    @Test
    public void testCustNameLike(){
        List<Customer> byCustNameLike = customerDao.findByCustNameLike("%诗");
        for (Customer customer : byCustNameLike) {
            System.out.println(customer);
        }
    }



}