package com.imooc.repository;

import com.imooc.dataobjct.OrderMaster;
import org.aspectj.weaver.ast.Or;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository repository;
    @Test
    public void  saveTest(){
        OrderMaster master=new OrderMaster();
        master.setOrderId("1233");
        master.setBuyerName("李志");
        master.setBuyerPhone("1736124421");
        master.setBuyerAddress("南京欧拉艺术空间");
        master.setBuyerOpenid("wx1234");
        master.setOrderAmount(new BigDecimal(100));
        OrderMaster save = repository.save(master);
        Assert.assertNotNull(master);
    }

    @Test
    public void findByBuyerOpenidTest(){
        PageRequest page=new PageRequest(1,1);
        Page<OrderMaster> master = repository.findByBuyerOpenid("wx1234", page);
        System.out.println(master.getTotalElements());

    }
}