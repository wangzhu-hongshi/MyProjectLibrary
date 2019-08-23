package com.imooc.repository;

import com.imooc.dataobjct.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.tools.JavaCompiler;

/**
 * 订单表
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    //根据微信id查询用户
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenId, Pageable pageable);

}
