package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 类目表
 */
@Entity//映射
@DynamicUpdate//自动更新
@Data
public class ProductCategory {
    @Id//表示主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增长
    private Integer categoryId;

    private String categoryName;//类目名字

    private Integer categoryType;//类目编号

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
