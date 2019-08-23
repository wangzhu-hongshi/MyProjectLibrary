package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class InfoServiceImplTest {
    @Autowired
    private InfoServiceImpl service;

    @Test
    public void findAll() {
        PageRequest request=new PageRequest(0,2);
        Page<ProductInfo> all = service.findAll(request);
        long totalElements = all.getTotalElements();
        Assert.assertNotEquals(0,totalElements);

    }

    @Test
    public void findByOne() {
        ProductInfo byOne = service.findByOne("123");
        Assert.assertEquals("123",byOne.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = service.findUpAll();
        for (ProductInfo info : upAll) {
            System.out.println(info);
        }
        Assert.assertNotEquals(0,upAll.size());
    }

    @Test
    public void save() {
        ProductInfo info=new ProductInfo();
        info.setProductId("125");
        info.setProductName("麻辣烫");
        info.setProductPrice(new BigDecimal(25));
        info.setProductStock(18);
        info.setProductDescription("真的很爽！");
        info.setProductIcon("http://#.jsp");
        info.setProductStatus(0);
        info.setCategoryType(3);
        ProductInfo save = service.save(info);
        Assert.assertNotNull(save);
    }
}