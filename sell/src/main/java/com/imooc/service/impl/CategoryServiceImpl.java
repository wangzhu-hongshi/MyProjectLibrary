package com.imooc.service.impl;

import com.imooc.dataobject.ProductCategory;
import com.imooc.repositroy.ProductCategoryRepository;
import com.imooc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 类目
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Override
    public ProductCategory findByid(Integer id) {
        return null;
    }

    @Override
    public List<ProductCategory> findAll() {
        return null;
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return null;
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return null;
    }
}
