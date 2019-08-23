package com.imooc.repository;

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
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;
    @Test
    public void findOneTest(){
        ProductCategory one = repository.findOne(1);
        System.out.println(one);
        Assert.assertNotNull(one);
    }
    @Test
    public void saveTest(){
//        ProductCategory category=new ProductCategory();
////        category.setCategoryName("热菜");
////        category.setCategoryType(20);
////        ProductCategory save = repository.save(category);
////        Assert.assertNotNull(save);
        ProductCategory one = repository.findOne(2);
        one.setCategoryType(21);
        ProductCategory save = repository.save(one);
        Assert.assertNotNull(save);
    }
    @Test
    public void findByCategoryTypeIn(){
        List<ProductCategory> byCategoryTypeIn = repository.findByCategoryTypeIn(Arrays.asList(10, 21));
        Assert.assertNotEquals(0,byCategoryTypeIn.size());
    }
}