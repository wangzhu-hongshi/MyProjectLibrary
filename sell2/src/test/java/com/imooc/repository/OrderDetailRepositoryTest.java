package com.imooc.repository;

import com.imooc.dataobjct.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){
        OrderDetail detail=new OrderDetail();
        detail.setDetailId("12345");
        detail.setOrderId("12222");
        detail.setProductId("11111");
        detail.setProductIcon("adaf.jsp");
        detail.setProductName("adfadf");
        detail.setProductPrice(new BigDecimal(123));
        detail.setProductQuantity(123);
        OrderDetail save = repository.save(detail);
        Assert.assertNotNull(save);
    }
    @Test
    public void findByOrderIdTest(){
        List<OrderDetail> byOrderId = repository.findByOrderId("12222");
        System.out.println(byOrderId);
        Assert.assertNotEquals(0,byOrderId);
    }
}