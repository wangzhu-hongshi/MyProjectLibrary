package com.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

public class OrderProductVo {
    private BigDecimal productTotalPrice;
    private String imageHost;
    private List<OrderItemVo> OrderItemVoList;

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public List<OrderItemVo> getOrderItemVoList() {
        return OrderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
        OrderItemVoList = orderItemVoList;
    }
}
