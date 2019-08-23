package com.imooc.repositroy;

import com.imooc.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository repository;


    @Test
    public void saveTest(){
        ProductInfo info=new ProductInfo();
        info.setProductId("123");
        info.setProductName("麻辣香锅");
        info.setProductPrice(new BigDecimal(22));
        info.setProductStock(100);
        info.setProductDescription("让人回味的麻辣！");
        info.setProductIcon("http://#.jsp");
        info.setProductStatus(0);
        info.setCategoryType(3);
        ProductInfo save = repository.save(info);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByProductStatus() throws Exception{
        List<ProductInfo> byProductStatus = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,byProductStatus.size());
    }
}