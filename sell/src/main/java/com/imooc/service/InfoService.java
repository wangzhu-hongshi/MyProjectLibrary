package com.imooc.service;

import com.imooc.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InfoService {
    /**
     * 查询所有 并分页
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo findByOne(String ProductId);

    /**
     * 查询所有在架商品表
     * @return
     */
    List<ProductInfo> findUpAll();

    ProductInfo save(ProductInfo productInfo);
}
