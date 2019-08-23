package com.imooc.repositroy;


import com.imooc.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;
    @Test
    public void findOneTest(){
        List<ProductCategory> all = repository.findAll();
        for (ProductCategory productCategory : all) {
            System.out.println(productCategory);
        }
    }

    @Test
    public void saveTest(){
        ProductCategory category=new ProductCategory();
        category.setCategoryName("女生最爱");
        category.setCategoryType(3);
        ProductCategory save = repository.save(category);
        Assert.assertNotNull(save);

    }
    @Test
    public void findByCategoryTypeIn(){
        List<Integer> list = Arrays.asList(2, 3);
        List<ProductCategory> in = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,in.size());
    }
    @Test
    public void findById(){

    }

}