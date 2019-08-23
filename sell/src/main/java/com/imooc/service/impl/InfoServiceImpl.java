package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import com.imooc.enums.ProductStatusEnum;
import com.imooc.repositroy.ProductInfoRepository;
import com.imooc.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InfoServiceImpl implements InfoService {
    @Autowired
    private ProductInfoRepository repository;
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {

        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo findByOne(String productId) {
//        Optional<ProductInfo> byId = repository.findById(productId);
//        return  byId.get();
        return null;

    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }
}
