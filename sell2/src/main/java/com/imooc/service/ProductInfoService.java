package com.imooc.service;

import com.imooc.dataobjct.ProductInfo;
import com.imooc.dto.CartDto;

import java.util.List;

public interface ProductInfoService {
    /**
     * 查询所有在架的商品
     * @param
     * @return
     */
    List<ProductInfo> findUpA();

    ProductInfo findOne(String productId);

    List<ProductInfo> findAll();

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDto> cartDtoList);
    //减库存
    void decreaseStock(List<CartDto> cartDtoList);



}
