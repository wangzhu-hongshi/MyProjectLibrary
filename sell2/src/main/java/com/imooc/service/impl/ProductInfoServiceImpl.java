package com.imooc.service.impl;

import com.imooc.dataobjct.ProductInfo;
import com.imooc.dto.CartDto;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.repository.ProductInfoRepository;
import com.imooc.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService{
    @Autowired
    public ProductInfoRepository repository;

    /**
     * 查询所有在架的商品
     * @return
     */
    @Override
    public List<ProductInfo> findUpA() {
        return repository.findByProductStatus(0);
    }

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findAll() {
        return repository.findAll();
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            ProductInfo productInfo = repository.findOne(cartDto.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.ORDER_NOT_EXIST);
            }
            Integer result=productInfo.getProductStock()+ cartDto.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }

    }

    /**
     * 减库存
     * 保持事务的一致性
     * @param cartDtoList
     */
    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            ProductInfo one = repository.findOne(cartDto.getProductId());
            if(one==null){
                throw new SellException(ResultEnum.ORDER_NOT_EXIST);
            }
            Integer result=one.getProductStock()-cartDto.getProductQuantity();
            if(result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            one.setProductStock(result);
            repository.save(one);

        }
    }
}
