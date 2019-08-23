package com.imooc.Vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfoVO {
    @JsonProperty("id")
    private String productId;
    @JsonProperty("name")
    private String productName;
    @JsonProperty("price")
    private BigDecimal productPrice;//价钱
    @JsonProperty("description")
    private String productDescription;//商品描述
    @JsonProperty("icon")
    private String productIcon;//小图

}
