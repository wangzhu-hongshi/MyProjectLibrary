package com.imooc.repository;

import com.imooc.dataobjct.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository repository;
    @Test
    public void findByUpA(){
        List<ProductInfo> byProductStatus = repository.findByProductStatus(0);
        for (ProductInfo productStatus : byProductStatus) {
            System.out.println(productStatus);
        }
    }

}