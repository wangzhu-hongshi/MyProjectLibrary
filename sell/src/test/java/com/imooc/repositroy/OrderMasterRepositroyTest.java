package com.imooc.repositroy;

import com.imooc.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositroyTest {
    @Autowired
    private OrderMasterRepository repository;
    @Test
    public void saveTest(){
        OrderMaster master=new OrderMaster();
        master.setOrderId("123");
        master.setBuyerName("李志");
        master.setBuyerPhone("1731212314");
        master.setBuyerAddress("南京欧拉艺术空间");
        master.setBuyerOpenid("123");
        master.setOrderAmount(new BigDecimal(100));
        OrderMaster save = repository.save(master);
        System.out.println(save);
//        Assert.assertNotNull(save);

    }

}