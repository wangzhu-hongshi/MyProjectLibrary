package com.imooc.controller;

import com.imooc.Vo.ResultVO;
import com.imooc.converter.OrderForm2OrderDtoConverter;
import com.imooc.dto.OrderDto;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.form.OrderForm;
import com.imooc.service.OrderService;

import com.imooc.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 买家端的web层
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    //创建订单
    /**
     * @Valid 指定需要验证的参数
     */
    @PostMapping("/create")
    public ResultVO<Map<String,String>> ate(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("【创建订单】 参数不正确, orderForm",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDto orderDto = OrderForm2OrderDtoConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.info("【创建订单】 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDto createResult = orderService.create(orderDto);
        Map<String,String> map=new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVOUtil.success(map);
    }
    //订单列表
    @GetMapping("list")
    public ResultVO<OrderDto> list(@RequestParam("openid") String openid,
                                   @RequestParam(value = "page" ,defaultValue = "0") Integer page,
                                   @RequestParam(value = "size",defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.info("【查询列表】 微信号不能为空,openid={}",openid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageReques=new PageRequest(page,size);
        Page<OrderDto> orderDtoPage = orderService.findList(openid, pageReques);
        return ResultVOUtil.success(orderDtoPage.getContent());
    }
    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDto> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        if(StringUtils.isEmpty(orderId)){
            log.info("【订单详情】 订单号不能为空, orderId={}",orderId);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        //TODO 不安全做法
        OrderDto orderDto = orderService.findOne(orderId);
        return ResultVOUtil.success(orderDto);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        if(StringUtils.isEmpty(orderId)){
            log.info("【取消订单】 订单号不能为空, orderId={}",orderId);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        //TODO 不安全做法
        OrderDto orderDto = orderService.findOne(orderId);
        OrderDto cancelResult = orderService.cancel(orderDto);

        return ResultVOUtil.success();
    }

}
