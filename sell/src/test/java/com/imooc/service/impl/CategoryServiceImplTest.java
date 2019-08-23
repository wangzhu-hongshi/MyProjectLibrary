package com.imooc.service.impl;

import com.imooc.dataobject.ProductCategory;
import com.imooc.repositroy.ProductCategoryRepository;
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
    public void findByid() {
//        ProductCategory byid = service.findByid(1);
//        Assert.assertNotNull(byid);
    }

//    @Test
//    public void findAll() {
//        List<ProductCategory> categoryList = service.findAll();
//        Assert.assertNotEquals(0,categoryList.size());
//    }
//
//    @Test
//    public void findByCategoryTypeIn() {
//        List<ProductCategory> byCategoryTypeIn = service.findByCategoryTypeIn(Arrays.asList(3, 3));
//        Assert.assertNotEquals(0,byCategoryTypeIn.size());
//    }
//
//    @Test
//    public void save() {
//        ProductCategory productCategory=new ProductCategory("香辣食品",5);
//        ProductCategory save = service.save(productCategory);
//        Assert.assertNotNull(save);
//    }
}