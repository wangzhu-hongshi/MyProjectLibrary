package com.imooc.converter;

import com.imooc.dataobjct.OrderMaster;
import com.imooc.dto.OrderDto;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 转换器
 */
public class OrderMaster2OrderDtoConverter {
    /**
     * 对象转换
     * @param orderMaster
     * @return
     */
    public static OrderDto convert(OrderMaster orderMaster){
        OrderDto orderDto=new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        return orderDto;
    }

    /**
     * 集合之间的转换
     * @param orderMasterList
     * @return
     */
    public static List<OrderDto> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e ->
                        convert(e)
                ).collect(Collectors.toList());
    }
}
