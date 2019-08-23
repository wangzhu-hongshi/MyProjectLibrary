package com.imooc.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.dataobjct.OrderDetail;
import com.imooc.dto.OrderDto;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderForm转换为 OrderDto
 */
@Slf4j
public class OrderForm2OrderDtoConverter {
    public static OrderDto convert(OrderForm orderForm){
        Gson gson=new Gson();//json转换对象
        OrderDto orderDto=new OrderDto();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOpenid(orderForm.getOpenid());

        //把参数中的 购物车字符串转化为json格式
        List<OrderDetail>orderDetailList=new ArrayList<>();
        try{
            orderDetailList=gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){
                    }.getType());
        }catch (Exception e){
            log.info("【对象转换】 错误, string={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

}
