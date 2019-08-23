package com.imooc.dto;

import lombok.Data;

/**
 * 购物车对象
 *
 */
@Data
public class CartDto {
    //商品id
    private String productId;
    //购买的数量
    private Integer productQuantity;

    public CartDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
