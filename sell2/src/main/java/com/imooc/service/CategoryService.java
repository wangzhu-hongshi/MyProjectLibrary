package com.imooc.service;

import com.imooc.dataobjct.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目 业务层
 *
 */

public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    ProductCategory save(ProductCategory productCategory);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}
