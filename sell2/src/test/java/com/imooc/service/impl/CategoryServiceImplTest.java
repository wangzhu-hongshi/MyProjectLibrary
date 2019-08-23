package com.imooc.service.impl;

import com.imooc.dataobjct.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {
    @Autowired
    private CategoryServiceImpl service;

    @Test
    public void findOne() {
        ProductCategory one = service.findOne(2);
        Assert.assertEquals(new Integer(2),one.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> all = service.findAll();
        Assert.assertNotEquals(0,all.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory=new ProductCategory("酒类",5);
        ProductCategory save = service.save(productCategory);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> byCategoryTypeIn = service.findByCategoryTypeIn(Arrays.asList(5));
        Assert.assertNotEquals(0,byCategoryTypeIn.size());
    }
}