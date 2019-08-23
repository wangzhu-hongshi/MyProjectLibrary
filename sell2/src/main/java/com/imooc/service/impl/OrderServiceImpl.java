package com.imooc.service.impl;

import com.imooc.converter.OrderMaster2OrderDtoConverter;
import com.imooc.dataobjct.OrderDetail;
import com.imooc.dataobjct.OrderMaster;
import com.imooc.dataobjct.ProductInfo;
import com.imooc.dto.CartDto;
import com.imooc.dto.OrderDto;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.repository.OrderDetailRepository;
import com.imooc.repository.OrderMasterRepository;
import com.imooc.repository.ProductInfoRepository;
import com.imooc.service.OrderService;
import com.imooc.service.ProductInfoService;
import com.imooc.utils.KyeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductInfoService service;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private ProductInfoService productInfoService;
    /**
     * 创建订单
     * @param orderDto
     * @return
     */
    @Override
    @Transactional//控制事务的注解
    public OrderDto create(OrderDto orderDto) {
        String orderId= KyeUtil.genUniquekey();
        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);
        //1.查询商品 （数量，价格）
        List<OrderDetail> orderDetailList = orderDto.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList) {
            ProductInfo productInfo = service.findOne(orderDetail.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算订单总价
            orderAmount=productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //订单详情入库
            orderDetail.setDetailId(KyeUtil.genUniquekey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        //3.写入订单数据库
        OrderMaster orderMaster=new OrderMaster();
        orderDto.setOrderId(orderId);
        orderDto.setOrderAmount(orderAmount);
        orderDto.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDto.setPayStatus(PayStatusEnum.WAIT.getCode());
        BeanUtils.copyProperties(orderDto,orderMaster);

        orderMasterRepository.save(orderMaster);


        //4.扣库存
            //lambda表达式 将传入进来的orderDto对象中的商品id，和数量 存入 购物车的集合中
        List<CartDto> cartDtoList=orderDto.getOrderDetailList().stream().map(e ->
                new CartDto(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDtoList);


        return orderDto;
    }

    /**
     * 查询单个订单
     * @param orderId 订单id
     * @return
     */
    @Override
    public OrderDto findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDto orderDto=new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

    /**
     * 查询订单列表
     * @param buyer 用户微信号
     * @param pageable 分页参数
     * @return
     */
    @Override
    public Page<OrderDto> findList(String buyer, Pageable pageable) {
        Page<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid(buyer, pageable);
        List<OrderDto> orderDtoList = OrderMaster2OrderDtoConverter.convert(orderMasters.getContent());
        PageImpl<OrderDto> orderDtos = new PageImpl<>(orderDtoList, pageable, orderMasters.getTotalElements());
        return orderDtos;
    }

    /**
     * 取消订单
     * @param orderDto 传递对象
     * @return
     */
    @Override
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        OrderMaster orderMaster=new OrderMaster();

        //1.判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
                log.info("【取消订单】 订单状态不正确, orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
                throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        //2.修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.info("【取消订单】 更新失败,orderMaster={}",updateResult);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //3.返回库存
        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.info("【取消订单】 订单中无商品详情,orderDto={}",orderDto );
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDto> cartDtoList =orderDto.getOrderDetailList().stream().map(e ->
                new CartDto(e.getProductId(),e.getProductQuantity())
                ).collect(Collectors.toList());
        productInfoService.increaseStock(cartDtoList);
        //4.如果已支付，需要退款
        if(orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS)){
            //TODO
        }

        return orderDto;
    }

    /**
     * 完结订单
     * @param orderDto
     * @return
     */
    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {
        //1.判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("【完结订单】 订单状态不正确, orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        //2.修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.info("【取消订单】 更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }

    /**
     * 支付订单
     * @param orderDto
     * @return
     */
    @Override
    @Transactional
    public OrderDto paid(OrderDto orderDto) {
        //1.判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("【支付订单】 订单状态不正确, orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        //2.判断支付状态
        if(!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.info("【支付订单】 支付状态不正确, orderDto={}",orderDto);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.info("【支付订单】 更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }
}
