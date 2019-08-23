package com.imooc.service.impl;

import com.imooc.dataobjct.OrderDetail;
import com.imooc.dto.OrderDto;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;
    private final String BOID="1123123";
    private final String ORDER_ID="1562854671163329359";
    @Test
    public void create() {
        OrderDto orderDto=new OrderDto();
        orderDto.setBuyerName("李添兴");
        orderDto.setBuyerAddress("南京李志团队");
        orderDto.setBuyerPhone("12314123");
        orderDto.setBuyerOpenid(BOID);
        //添加购物车
        List<OrderDetail> orderDetails=new ArrayList<>();

        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setProductId("124");
        orderDetail.setProductQuantity(1);

//        OrderDetail orderDetail2=new OrderDetail();
//        orderDetail.setProductId("123");
//        orderDetail.setProductQuantity(3);

        orderDetails.add(orderDetail);
//        orderDetails.add(orderDetail2);

        orderDto.setOrderDetailList(orderDetails);

        OrderDto dto = orderService.create(orderDto);
        log.info("【创建订单】 dto={}",dto);
        Assert.assertNotNull(dto);


    }

    @Test
    public void findOne() {
        OrderDto one = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】 one={}",one);
        Assert.assertEquals(ORDER_ID, one.getOrderId());

    }

    /**
     * 查询订单列表
     */
    @Test
    public void findList() {
        PageRequest pageRequest=new PageRequest(0,2);
        Page<OrderDto> list = orderService.findList(BOID, pageRequest);
        Assert.assertNotEquals(0,list.getTotalElements());
    }

    /**
     * 取消订单
     */
    @Test
    public void cancel() {
        OrderDto one = orderService.findOne(ORDER_ID);
        OrderDto cancel = orderService.cancel(one);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),cancel.getOrderStatus());

    }

    /**
     * 完结订单
     */
    @Test
    public void finish() {
        OrderDto one = orderService.findOne(ORDER_ID);
        OrderDto finish = orderService.finish(one);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),finish.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDto one = orderService.findOne(ORDER_ID);
        OrderDto finish = orderService.paid(one);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),finish.getPayStatus());
    }
}