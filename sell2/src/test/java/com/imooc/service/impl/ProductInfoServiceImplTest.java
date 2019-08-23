package com.imooc.service.impl;

import com.imooc.dataobjct.ProductInfo;
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
public class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoServiceImpl service;

    @Test
    public void findUpA() {
        List<ProductInfo> upA = service.findUpA();
        System.out.println(upA);
        Assert.assertNotEquals(0, upA.size());
    }

    @Test
    public void findOne() {
        ProductInfo one = service.findOne("123");
        Assert.assertNotNull(one);
    }

    @Test
    public void findAll() {
        List<ProductInfo> all = service.findAll();
        Assert.assertNotEquals(0,all.size());
    }

    @Test
    public void save() {
        ProductInfo productInfo=new ProductInfo("123","凉拌黄瓜",new BigDecimal(10),35,"来瓶啤酒会更爽哦！","123.jsp",10);
        ProductInfo save = service.save(productInfo);
        Assert.assertNotNull(save);
    }
}